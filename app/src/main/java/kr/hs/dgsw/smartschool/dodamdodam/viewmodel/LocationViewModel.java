package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.util.Log;

import com.annimon.stream.Collector;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.WindowDecorActionBar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Locations;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LocationClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;

public class LocationViewModel extends ViewModel {
    LocationClient locationClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<String> isPostSuccess = new MutableLiveData<>();
    private final MutableLiveData<Map<Time, Place>> studentLocationValue = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
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
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void postLocation(Map<Time, Place> timeTable) {
        loading.setValue(true);
        LocationRequest request = new LocationRequest(timeTable);
        Log.e("request", request.toString());
        disposable.add(locationClient.postLocation(request
                , databaseHelper.getToken().getToken())
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
                                loginErrorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));

    }

    public void getMyLocation() {
        loading.setValue(true);
        disposable.add(locationClient.getMyLocation(
                databaseHelper.getToken().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<LocationRequest>() {
                            @Override
                            public void onSuccess(LocationRequest locations) {
                                Map<Time, Place> result = new HashMap<>();
                                ArrayList<Time> times = (ArrayList<Time>) databaseHelper.getData("time", new DatabaseGetDataType<>(Time.class));
                                if (Utils.isWeekEnd)
                                    times = (ArrayList<Time>) Stream.of(times).filter(time -> time.getType() == 2).collect(Collectors.toList());
                                else
                                    times = (ArrayList<Time>) Stream.of(times).filter(time -> time.getType() == 1).collect(Collectors.toList());

                                for (Time time : times){
                                    result.put(time, null);
                                }

                                for (Location location : locations.getLocations()) {

                                    Time time = Stream.of(times)
                                            .filter(a -> a.getIdx() == location.getTimetableIdx())
                                            .collect(Collectors.toList()).get(0);


                                    Place place = (Place) databaseHelper.getData(
                                            "place"
                                            , "idx"
                                            , location.getPlaceIdx() + ""
                                            , new DatabaseGetDataType<>(Place.class)
                                    );
                                    result.put(time, place);
                                }
                                studentLocationValue.setValue(result);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                loginErrorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));

    }
}
