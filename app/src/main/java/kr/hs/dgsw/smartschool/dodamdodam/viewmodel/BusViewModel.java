package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.BusClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;

public class BusViewModel extends ViewModel {
    BusClient busClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<Bus> response = new MutableLiveData<>();
    private final MutableLiveData<String> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public BusViewModel(Context context) {
        busClient = new BusClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<Bus> getResponse() {
        return response;
    }

    public LiveData<String> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getLoginErrorMessage() {
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @SuppressLint("CheckResult")
    public void postBusApply(BusRequest request) {
        loading.setValue(true);
        disposable.add(busClient.postBusApply(
                databaseHelper.getToken().getToken(), request)
                .subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>(){
                    @Override
                    public void onSuccess(String successMessage) {
                        isSuccess.setValue(successMessage);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginErrorMessage.setValue(e.getMessage());
                        loading.setValue(false);
                    }
                }));
    }

    @SuppressLint("CheckResult")
    public void deleteBusApply(int idx) {
        loading.setValue(true);
        disposable.add(busClient.deleteBusApply(
                databaseHelper.getToken().getToken(), idx)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(String successMessage) {
                        isSuccess.setValue(successMessage);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginErrorMessage.setValue(e.getMessage());
                        loading.setValue(false);
                    }
                }));
    }

    @SuppressLint("CheckResult")
    public void getMyBusApply() {
        loading.setValue(true);
        disposable.add(busClient.getMyBusApply(
                databaseHelper.getToken().getToken())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Bus>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onSuccess(Bus bus) {
                                response.setValue(bus);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                loginErrorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));
    }

    @SuppressLint("CheckResult")
    public void putModifyBusApply(int idx, BusRequest request) {
        loading.setValue(true);
        disposable.add(busClient.putModifyBusApply(
                databaseHelper.getToken().getToken(), idx, request)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>(){
                    @Override
                    public void onSuccess(String s) {
                        isSuccess.setValue(s);
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
