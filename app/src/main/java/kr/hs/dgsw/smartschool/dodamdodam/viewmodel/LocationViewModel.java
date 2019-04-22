package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LocationClient;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Location;

public class LocationViewModel extends ViewModel {
    LocationClient locationClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<String> isPostSuccess = new MutableLiveData<>();
    private final MutableLiveData<Location> studentLocationValue = new MutableLiveData<>();
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
    public LiveData<Location> getStudentLocationValue() {
        return studentLocationValue;
    }
    public LiveData<String> getError() {
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void postLocation(List<Place> timeTable) {
        loading.setValue(true);
        Location request = new Location(timeTable);
        Log.e("request", request.toString());
        disposable.add(locationClient.postLocation(request,
                databaseHelper.<Token>getData("token",new Token()).getToken())
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

    public void getStudentLocation() {
        loading.setValue(true);
        disposable.add(locationClient.getStudentLocation(
                databaseHelper.<Token>getData("token",new Token()).getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                studentLocationValue.setValue(location);
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
