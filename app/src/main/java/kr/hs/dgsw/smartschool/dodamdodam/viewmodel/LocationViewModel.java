package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.b1nd.service.model.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
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

    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<Map<Student,Map<Time, Place>>> data = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public LocationViewModel(Context context) {
        locationClient = new LocationClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<Map<Student,Map<Time, Place>>> getData() {
        return data;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void postLocation(Map<Time, Place> timeTable) {
        loading.setValue(true);
        Single<Response> single;

        String method = "PUT";

        if (locationRequest.getLocations().isEmpty()){
                method = "POST";
            locationRequest = new LocationRequest(timeTable);

        }else {
            locationRequest.setLocations(timeTable);
        }


        single = locationClient.postLocation(locationRequest
                , databaseHelper.getToken().getToken()
                , method);

        Log.e("request", locationRequest.toString());

        addDisposable(single);

//        disposable.add(single
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(
//                        new DisposableSingleObserver<Response>() {
//                            @Override
//                            public void onSuccess(Response response) {
//                                isPostSuccess.setValue(response.getMessage());
//                                loading.setValue(false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                errorMessage.setValue(e.getMessage());
//                                loading.setValue(false);
//                            }
//                        }));

    }

    public void getLocation() {
        loading.setValue(true);
        Single<Response> single = null;
        single =  locationClient.getLocation(databaseHelper.getToken().getToken());

        assert single != null;
        addDisposable(single);

//        disposable.add(single.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(
//                        new DisposableSingleObserver<Response>() {
//                            @Override
//                            public void onSuccess(Response response) {
//                                locationRequest = (LocationRequest) response.getData();
//                                studentLocationValue.setValue(convertLocationRequestToMap((LocationRequest)response.getData()));
//                                loading.setValue(false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                errorMessage.setValue(e.getMessage());
//                                loading.setValue(false);
//                            }
//                        }));
    }

    private void addDisposable(Single single){
        disposable.add((Disposable) single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Response>() {
                            @Override
                            public void onSuccess(Response response) {
                                if (response.getData() == null){
                                    successMessage.setValue(response.getMessage());
                                }else{
                                    locationRequest = (LocationRequest<LocationInfo>) response.getData();
                                    data.setValue(
                                            convertLocationRequestToMap((LocationRequest) response.getData()));
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

    private Map convertLocationRequestToMap(LocationRequest<Location> locations){
        Map<Student,Map<Time,Place>> result = new HashMap<>();

        Map<Time, Place> locationTemp = new HashMap<>();

        List<Time> times = databaseHelper.getData(DatabaseManager.TABLE_TIME, new DatabaseGetDataType<>(Time.class));

        if (Utils.isWeekEnd)
            times = Stream.of(times).filter(time -> time.getType() == 2).collect(Collectors.toList());
        else
            times = Stream.of(times).filter(time -> time.getType() == 1).collect(Collectors.toList());

        for (Time time : times) {
            locationTemp.put(time, null);
        }

        for (Location location : locations.getLocations()) {

            Time time = Stream.of(times)
                    .filter(a -> a.getIdx() == location.getTimetableIdx())
                    .collect(Collectors.toList()).get(0);

            if (location.getPlaceIdx() == null) {
                locationTemp.put(time, null);
                continue;
            }
            Place place = databaseHelper.getData(
                    DatabaseManager.TABLE_PLACE
                    , "idx"
                    , Integer.toString(location.getPlaceIdx())
                    , new DatabaseGetDataType<>(Place.class)
            );

            locationTemp.put(time, place);
        }

        result.put((Student) databaseHelper.getMyInfo(),locationTemp);
        return result;
    }
}
