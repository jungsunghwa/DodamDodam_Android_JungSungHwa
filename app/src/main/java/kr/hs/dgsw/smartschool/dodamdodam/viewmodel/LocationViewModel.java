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
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LocationClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;

public class LocationViewModel extends ViewModel {
    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<String> isPostSuccess = new MutableLiveData<>();
    private final MutableLiveData<Map<Time, Place>> studentLocationValue = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public LocationViewModel(Context context) {
        locationClient = new LocationClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<String> getIsPostSuccess() {
        return isPostSuccess;
    }

    public LiveData<Map<Time, Place>> getStudentLocationValue() {
        return studentLocationValue;
    }

    public LiveData<String> getError() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void postLocation(Map<Time, Place> timeTable) {
        loading.setValue(true);
        Single<String> client;

        String method = "POST";

        if (locationRequest.getLocations() != null) {
            method = "PUT";
        }

        locationRequest.setLocations(timeTable);

        client = locationClient.postLocation(locationRequest
                , databaseHelper.getToken().getToken()
                , method);

        Log.e("request", locationRequest.toString());
        disposable.add(client
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @Override
                            public void onSuccess(String successMessage) {
                                isPostSuccess.setValue(successMessage);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));

    }

    public void getMyLocation() {
        loading.setValue(true);
        disposable.add((Disposable) locationClient.getMyLocation(
                databaseHelper.getToken().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<LocationRequest>() {
                            @Override
                            public void onSuccess(LocationRequest locations) {
                                locationRequest = locations;

                                Map<Time, Place> result = new HashMap<>();
                                List<Time> times = databaseHelper.getData(DatabaseManager.TABLE_TIME, new DatabaseGetDataType<>(Time.class));
                                if (Utils.isWeekEnd)
                                    times = Stream.of(times).filter(time -> time.getType() == 2).collect(Collectors.toList());
                                else
                                    times = Stream.of(times).filter(time -> time.getType() == 1).collect(Collectors.toList());

                                for (Time time : times) {
                                    result.put(time, null);
                                }

                                for (Location location : locations.getLocations()) {

                                    Time time = Stream.of(times)
                                            .filter(a -> a.getIdx() == location.getTimetableIdx())
                                            .collect(Collectors.toList()).get(0);

                                    if (location.getPlaceIdx() == null) {
                                        result.put(time, null);
                                        continue;
                                    }
                                    Place place = databaseHelper.getData(
                                            DatabaseManager.TABLE_PLACE
                                            , "idx"
                                            , Integer.toString(location.getPlaceIdx())
                                            , new DatabaseGetDataType<>(Place.class)
                                    );

                                    result.put(time, place);
                                }
                                studentLocationValue.setValue(result);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));

    }
}
