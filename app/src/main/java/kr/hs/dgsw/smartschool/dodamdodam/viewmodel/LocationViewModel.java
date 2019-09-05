package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Locations;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LocationClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;

public class LocationViewModel extends BaseViewModel<Map<Student, List<LocationInfo>>> {
    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private CompositeDisposable disposable;
    private ArrayList<Locations> locations;
    private TokenManager manager;
    private Boolean isPost = false;

    private MutableLiveData<Boolean> unCheckStatus = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkStatus = new MutableLiveData<>();

    private Map<Student, List<LocationInfo>> result = new HashMap<>();

    public MutableLiveData<Boolean> getUnCheckStatus() {
        return unCheckStatus;
    }

    public MutableLiveData<Boolean> getCheckStatus() {
        return checkStatus;
    }

    public LocationViewModel(Application application) {
        super(application);
        locationClient = new LocationClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(application);
    }

    public void putLocation(LocationInfo locationInfo) {
        loading.setValue(true);

        addDisposable(locationClient.putLocation(locationInfo, manager.getToken()), getBaseObserver());
    }

    public void deleteLocation(int idx) {
        loading.setValue(true);

        addDisposable(locationClient.deleteLocation(manager.getToken(), idx), getBaseObserver());
    }

    public void postLocation() {
        loading.setValue(true);
        List<LocationInfo> timeTable = new ArrayList<>();
        DisposableSingleObserver<String> observer = new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String locationRequest) {
                getMyLocation();
                loading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                getMyLocation();
                loading.setValue(false);
            }
        };
        //-------------------------------------------------------------------------------------------------------------------//
        List<Time> times = helper.getData(DatabaseManager.TABLE_TIME, new DatabaseGetDataType<>(Time.class));

        if (Utils.isWeekEnd)
            times = Stream.of(times).filter(time -> time.getType() == 2).collect(Collectors.toList());
        else
            times = Stream.of(times).filter(time -> time.getType() == 1).collect(Collectors.toList());

        for (Time time : times) {
            timeTable.add(new LocationInfo(time, null));
        }

//        List<LocationInfo> currentTimeTable = new ArrayList<>();
//        currentTimeTable.add(timeTable.get(idx));

        locationRequest = new LocationRequest(timeTable, ((Student) helper.getMyInfo()).getClassInfo());
        //-------------------------------------------------------------------------------------------------------------------//
        addDisposable(locationClient.postLocation(locationRequest, manager.getToken()), observer);
    }

    public void checkLocation(int idx) {
        loading.setValue(true);

        DisposableSingleObserver<String> observer = new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String s) {
                checkStatus.setValue(true);
                loading.setValue(false);
                dispose();
            }
            @Override
            public void onError(Throwable e) {
                if (errorEvent(e)) {
                    checkStatus.setValue(false);
                    dispose();
                }
            }
        };
        addDisposable(locationClient.checkLocation(manager.getToken(), idx), observer);
    }

    public void unCheckLocation(int idx) {
        loading.setValue(true);

        DisposableSingleObserver<String> observer = new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String s) {
                unCheckStatus.setValue(true);
                loading.setValue(false);
                dispose();
            }
            @Override
            public void onError(Throwable e) {
                if (errorEvent(e)) {
                    unCheckStatus.setValue(false);
                    dispose();
                }
            }
        };
        addDisposable(locationClient.unCheckLocation(manager.getToken(), idx), observer);
    }

    public void getLocation() {
        loading.setValue(true);

        DisposableSingleObserver<List<Locations>> observer = new DisposableSingleObserver<List<Locations>>() {
            @Override
            public void onSuccess(List<Locations> locations) {
                result.clear();
                data.setValue(convertLocationsToMap(locations));
                loading.setValue(false);
                dispose();
            }

            @Override
            public void onError(Throwable e) {
                if (errorEvent(e))
                    dispose();
            }
        };
        //client는 존재 해야되는데 onError로 호출 하는 방법을 찾아야 됨
        addDisposable(locationClient.getLocation(manager.getToken()), observer);
    }

    public void getMyLocation() {
        loading.setValue(true);

        DisposableSingleObserver<LocationRequest> observer = new DisposableSingleObserver<LocationRequest>() {
            @Override
            public void onSuccess(LocationRequest locationRequest) {
                result.clear();
                if (locationRequest.getLocationInfos().isEmpty()) {
                    postLocation();
                } else {
                    data.setValue(
                            convertLocationRequestToMap(locationRequest.getLocationInfos(), null)); // 휴대폰에 맨 처음 깔고 실행했을 경우 여기에서 nullpoint 오류남
                    loading.setValue(false);
                }
                dispose();
            }

            @Override
            public void onError(Throwable e) {
                if (errorEvent(e))
                    dispose();
            }
        };

        addDisposable(locationClient.getMyLocation(manager.getToken()), observer);
    }

    private Map<Student, List<LocationInfo>> convertLocationsToMap(List<Locations> locations) {
        result.clear();

        for (Locations location : locations) {
            convertLocationRequestToMap(location.getLocations(), location.getStudentIdx());
        }

        return result;
    }

    private Map<Student, List<LocationInfo>> convertLocationRequestToMap(List<LocationInfo> locations, Integer studentIdx) {
        Map<Student, List<LocationInfo>> locationInfoMap = new HashMap<>();
        Student student;

        if (studentIdx == null)
            student = (Student) helper.getMyInfo();
        else {
            student = (Student) helper.getStudentByIdx(studentIdx);
        }

        locationInfoMap.put(student, new ArrayList<>());

        List<Time> times = helper.getData(DatabaseManager.TABLE_TIME, new DatabaseGetDataType<>(Time.class));

        if (Utils.isWeekEnd)
            times = Stream.of(times).filter(time -> time.getType() == 2).collect(Collectors.toList());
        else
            times = Stream.of(times).filter(time -> time.getType() == 1).collect(Collectors.toList());

        if (locations.isEmpty()) {
            for (Time time : times) {
                locations.add(new LocationInfo(time, null));
            }
        }

        for (LocationInfo location : locations) {
            Time time = Stream.of(times)
                    .filter(a -> a.getIdx() == location.getTimetableIdx())
                    .collect(Collectors.toList()).get(0);

            location.setTime(time);

            if (location.getPlaceIdx() == null) {
                locationInfoMap.get(student).add(location);
                continue;
            }

            Place place = helper.getData(
                    DatabaseManager.TABLE_PLACE,
                    "idx",
                    Integer.toString(location.getPlaceIdx()),
                    new DatabaseGetDataType<>(Place.class)
            );

            location.setPlace(place);

            locationInfoMap.get(student).add(location);
        }

        if (student != null) result.put(student, locations);

        return locationInfoMap;
    }
}
