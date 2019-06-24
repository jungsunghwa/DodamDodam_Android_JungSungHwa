package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LostFoundClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;

public class LostFoundViewModel {
    private final MutableLiveData<List<LostFound>> response = new MutableLiveData<>();
    private final MutableLiveData<String> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private LostFoundClient lostFoundClient;
    private CompositeDisposable disposable;
    private TokenManager manager;

    public LostFoundViewModel(Context context) {
        lostFoundClient = new LostFoundClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(context);
    }

    public LiveData<List<LostFound>> getResponse() {
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

    public void getLostFound(Integer page, Integer type) {
        loading.setValue(true);
        disposable.add(lostFoundClient.getLostFound(
                manager.getToken(), page, type)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<LostFound>>() {
                    @Override
                    public void onSuccess(List<LostFound> lostFound) {
                        response.setValue(lostFound);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginErrorMessage.setValue(e.getMessage());
                        loading.setValue(false);
                    }
                }));
    }

    public void postCreateLostFound(LostFoundRequest request) {
        loading.setValue(true);
        disposable.add(lostFoundClient.postCreateLostFound(
                manager.getToken(), request)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
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

    public void putLostFound(LostFoundRequest request) {
        loading.setValue(true);
        disposable.add(lostFoundClient.postCreateLostFound(
                manager.getToken(), request)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
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

    public void deleteLostFound(Integer idx) {
        loading.setValue(true);
        disposable.add(lostFoundClient.deleteLostFound(
                manager.getToken(), idx)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
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

    public void getLostFoundSearch(String search) {
        loading.setValue(true);
        disposable.add(lostFoundClient.getLostFoundSearch(
                manager.getToken(), search)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<LostFound>>() {
                    @Override
                    public void onSuccess(List<LostFound> lostFound) {
                        response.setValue(lostFound);
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
