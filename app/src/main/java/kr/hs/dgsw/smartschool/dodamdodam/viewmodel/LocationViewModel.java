package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

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
import kr.hs.dgsw.smartschool.dodamdodam.network.request.PostLocationRequest;

public class LocationViewModel extends ViewModel {
    LocationClient locationClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<String> isPostSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public LocationViewModel(Context context) {
        locationClient = new LocationClient();
        disposable = new CompositeDisposable();
        databaseHelper = new DatabaseHelper(context);
    }

    public LiveData<String> getIsPostSuccess() {
        return isPostSuccess;
    }

    public LiveData<String> getError() {
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    public void postLocation(ArrayList<Place> timeTable) {
        loading.setValue(true);
        PostLocationRequest request = new PostLocationRequest(timeTable);
        Log.e("request", request.toString());
        disposable.add(locationClient.postLocation(request
                , databaseHelper.<Token>getData("token",new Token()).getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
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

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @SuppressLint("CheckResult")
//    public void getStudentLoaction() {
//        loading.setValue(true);
//        disposable.add(locationClient.postLocation(new PostLocationRequest(timeTable)
//                ,databaseHelper.<Token>getData("token",new Token()).getToken())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(
//                        new DisposableSingleObserver<String>() {
//                            @RequiresApi(api = Build.VERSION_CODES.N)
//                            @Override
//                            public void onSuccess(String successMessage) {
//                                isPostSuccess.setValue(successMessage);
//                                loading.setValue(false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                loginErrorMessage.setValue(e.getMessage());
//                                loading.setValue(false);
//                            }
//                        }));
//
//    }
}
