package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meal;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Locations;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LocationClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;

public class LocationViewModel extends BaseViewModel {
    private LocationClient locationClient;
    private LocationRequest<LocationInfo> locationRequest;
    private CompositeDisposable disposable;
    private DatabaseHelper helper;
    private ArrayList<Locations> locations;


    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<Map<Student, List<LocationInfo>>> data = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private Map<Student, List<LocationInfo>> result = new HashMap<>();

    public LocationViewModel(Context context) {
        super(context);
        locationClient = new LocationClient();
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<Map<Student, List<LocationInfo>>> getData() {
        return data;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void putLocation(LocationInfo locationInfo) {
        loading.setValue(true);

        addDisposable(locationClient.putLocation(locationInfo, helper.getToken()), baseObserver);
    }

    public void postLocation() {
        loading.setValue(true);
        List<LocationInfo> timeTable = new ArrayList<>();
        DisposableSingleObserver<String> observer = new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String locationRequest) {
                getLocation();
                loading.setValue(false);
            }
            @Override
            public void onError(Throwable e) {
                errorMessage.setValue(e.getMessage());
                loading.setValue(false);
            }
        };
        //-------------------------------------------------------------------------------------------------------------------//
        List<Time> times = helper.getData(DatabaseManager.TABLE_TIME, new DatabaseGetDataType<>(Time.class));

        if (Utils.isWeekEnd) times = Stream.of(times).filter(time -> time.getType() == 2).collect(Collectors.toList());
        else times = Stream.of(times).filter(time -> time.getType() == 1).collect(Collectors.toList());

        for (Time time : times) timeTable.add(new LocationInfo(time, null));

        locationRequest = new LocationRequest<>(timeTable, ((Student) helper.getMyInfo()).getClassInfo());
        //-------------------------------------------------------------------------------------------------------------------//
        addDisposable(locationClient.postLocation(locationRequest, helper.getToken()), observer);
    }

    public void checkLocation(int idx) {
        loading.setValue(true);
        addDisposable(locationClient.checkLocation(helper.getToken(), idx), baseObserver);
    }

    public void getLocation() {
        loading.setValue(true);

        DisposableSingleObserver<List<Locations>> observer = new DisposableSingleObserver<List<Locations>>() {
            @Override
            public void onSuccess(List<Locations> locations) {
                result.clear();
                data.setValue(convertLocationsToMap(locations));
                loading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                errorMessage.setValue(e.getMessage());
                loading.setValue(false);
            }
        };

        addDisposable(locationClient.getLocation(helper.getToken()), observer);
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
                            convertLocationRequestToMap(locationRequest.getLocationInfos(), null));
                    loading.setValue(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                errorMessage.setValue(e.getMessage());
                loading.setValue(false);
            }
        };

        addDisposable(locationClient.getMyLocation(helper.getToken()), observer);
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
