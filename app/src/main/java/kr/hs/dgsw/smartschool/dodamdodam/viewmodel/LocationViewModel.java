package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
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

public class LocationViewModel extends ViewModel {
    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;
    private ArrayList<Locations> locations;

    private Boolean isPost = false;

    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<Map<Student, List<LocationInfo>>> data = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    Map<Student,List<LocationInfo>> result = new HashMap<>();

    public LocationViewModel(Context context) {
        locationClient = new LocationClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<Map<Student,List<LocationInfo>>> getData() {
        return data;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void postLocation(List<LocationInfo> timeTable) {
        loading.setValue(true);
        Single<Response> single;

        String method = "PUT";

        if (isPost) {
            method = "POST";
            locationRequest = new LocationRequest(timeTable);
        } else {
            locationRequest.setLocations(timeTable);
        }


        single = locationClient.postLocation(locationRequest, databaseHelper.getToken().getToken(), method);

        Log.e("request", locationRequest.toString());

        addDisposable(single);

        /*disposable.add(single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Response>() {
                            @Override
                            public void onSuccess(Response response) {
                                isPostSuccess.setValue(response.getMessage());
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));*/

    }

    public void checkLocation(int idx){
        Single<Response> single;

        single = locationClient.checkLocation(databaseHelper.getToken().getToken(), idx);

        addDisposable(single);
    }

    public void getLocation() {
        loading.setValue(true);
        Single<Response> single = locationClient.getLocation(databaseHelper.getToken().getToken());

        addDisposable(single);

        /*disposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Response>() {
                            @Override
                            public void onSuccess(Response response) {
                                locationRequest = (LocationRequest) response.getData();
                                studentLocationValue.setValue(convertLocationRequestToMap((LocationRequest)response.getData()));
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));*/
    }

    private void addDisposable(Single<Response> single) {
        disposable.add(single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Response>() {
                            @Override
                            public void onSuccess(Response response) {
                                if (response.getData() == null) {
                                    successMessage.setValue(response.getMessage());
                                } else {
                                    if (Utils.identity == Identity.STUDENT) {
                                        locationRequest = ((Response<LocationRequest<LocationInfo>>) response).getData();
                                        result.clear();
                                        data.setValue(
                                                convertLocationRequestToMap(locationRequest.getLocations(), null));
                                    }else {
                                        locations = (ArrayList<Locations>) response.getData();
                                        result.clear();
                                        data.setValue(convertLocationsToMap(locations));
                                    }
                                }
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));
    }

    private Map<Student, List<LocationInfo>> convertLocationsToMap(List<Locations> locations) {
        result.clear();

        for (Locations location : locations) {
            convertLocationRequestToMap(location.getLocations(), location.getStudentIdx());
        }

        return result;
    }

    private Map<Student, List<LocationInfo>> convertLocationRequestToMap(List<LocationInfo> locations, Integer studentIdx){
        Map<Student, List<LocationInfo>> locationInfoMap = new HashMap<>();
        Student student;

        if (studentIdx == null)
            student = (Student) databaseHelper.getMyInfo();
        else{
            student = (Student) databaseHelper.getStudentByIdx(studentIdx);
        }

        locationInfoMap.put(student, new ArrayList<>());

        List<Time> times = databaseHelper.getData(DatabaseManager.TABLE_TIME, new DatabaseGetDataType<>(Time.class));

        if (Utils.isWeekEnd)
            times = Stream.of(times).filter(time -> time.getType() == 2).collect(Collectors.toList());
        else
            times = Stream.of(times).filter(time -> time.getType() == 1).collect(Collectors.toList());

        if (locations.isEmpty()) {
            isPost = true;
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

            Place place = databaseHelper.getData(
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
