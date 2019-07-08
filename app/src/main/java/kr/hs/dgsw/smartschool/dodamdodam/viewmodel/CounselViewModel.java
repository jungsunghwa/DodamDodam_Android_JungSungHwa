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
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.CounselClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;

public class CounselViewModel extends ViewModel {
    private CounselClient counselClient;
    private CompositeDisposable disposable;
    private TokenManager manager;

    private final MutableLiveData<Counsel> response = new MutableLiveData<>();
    private final MutableLiveData<String> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public CounselViewModel(Context context) {
        counselClient = new CounselClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(context);
    }

    public LiveData<Counsel> getResponse() {
        return response;
    }

    public LiveData<String> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getError() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @SuppressLint("CheckResult")
    public void getAllCounsel() {
        loading.setValue(true);
        disposable.add(counselClient.getAllCounsel(
                manager.getToken())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Counsel>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onSuccess(Counsel counsel) {
                                response.setValue(counsel);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }
                ));
    }

    @SuppressLint("CheckResult")
    public void postCounsel(CounselRequest request) {
        loading.setValue(true);
        disposable.add(counselClient.postCounsel(
                manager.getToken(), request)
                .subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        isSuccess.setValue(s);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorMessage.setValue(e.getMessage());
                        loading.setValue(false);
                    }
                }));
    }

    @SuppressLint("CheckResult")
    public void getCertainCounsel(int counselIdx) {
        loading.setValue(true);
        disposable.add(counselClient.getCertainCounsel(
                manager.getToken(), counselIdx)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Counsel>() {
                            @Override
                            public void onSuccess(Counsel counsel) {
                                response.setValue(counsel);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }
                ));
    }

    @SuppressLint("CheckResult")
    public void deleteCounsel(int counselIdx) {
        loading.setValue(true);
        disposable.add(counselClient.deleteCounsel(
                manager.getToken(), counselIdx)
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
                        errorMessage.setValue(e.getMessage());
                        loading.setValue(false);
                    }
                }));
    }

    @SuppressLint("CheckResult")
    public void postCounselAllow(CounselRequest request) {
        loading.setValue(true);
        disposable.add(counselClient.postCounselAllow(
                manager.getToken(), request)
                .subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        isSuccess.setValue(s);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorMessage.setValue(e.getMessage());
                        loading.setValue(false);
                    }
                }));
    }

    @SuppressLint("CheckResult")
    public void postCounselCancel(CounselRequest request) {
        loading.setValue(true);
        disposable.add(counselClient.postCounselCancel(
                manager.getToken(), request)
                .subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        isSuccess.setValue(s);
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
