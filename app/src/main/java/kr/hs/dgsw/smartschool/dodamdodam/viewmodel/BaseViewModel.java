package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.activity.LoginActivity;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.TokenClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.TokenRequest;
import okhttp3.ResponseBody;

import static android.content.Context.CONNECTIVITY_SERVICE;

abstract class BaseViewModel<T> extends AndroidViewModel {
    public final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    final MutableLiveData<String> successMessage = new MutableLiveData<>();
    final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    final MutableLiveData<Throwable> error = new MutableLiveData<>();
    final MutableLiveData<T> data = new MutableLiveData<>();

    DatabaseHelper helper;
    TokenManager manager;

    private CompositeDisposable disposable;
    private TokenClient tokenClient;

    BaseViewModel(Application application) {
        super(application);
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getInstance(application);
        manager = TokenManager.getInstance(application);
        tokenClient = new TokenClient();
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<T> getData() {
        return data;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    void addDisposable(Single single, DisposableSingleObserver observer) {
        disposable.add((Disposable) single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));
    }

    DisposableSingleObserver<String> getBaseObserver() {
        return new BaseDisposableSingleObserver();
    }

    DisposableSingleObserver<T> getDataObserver() {
        return new DataDisposableSingleObserver();
    }

    DisposableSingleObserver<String> getTokenObserver() {
        return new TokenDisposableSingleObserver();
    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private class BaseDisposableSingleObserver extends DisposableSingleObserver<String> {
        @Override
        public void onSuccess(String s) {
            successMessage.setValue(s);
            loading.setValue(false);
            success.setValue(true);
            dispose();
        }

        @Override
        public void onError(Throwable e) {
            if (errorEvent(e))
                dispose();
        }
    }

    private class DataDisposableSingleObserver extends DisposableSingleObserver<T> {
        @Override
        public void onSuccess(T t) {
            data.setValue(t);
            loading.setValue(false);
            success.setValue(true);
            dispose();
        }

        @Override
        public void onError(Throwable e) {
            if (errorEvent(e))
                dispose();
        }
    }

    private class TokenDisposableSingleObserver extends DisposableSingleObserver<String> {
        @Override
        public void onSuccess(String s) {
            String refresh = manager.getToken().getRefreshToken();
            manager.setToken(null, null);
            manager.setToken(s, refresh);
            loading.setValue(false);
            dispose();
        }

        @Override
        public void onError(Throwable e) {
            Log.e("tokenError",  e.getMessage());
            getApplication().startActivity(new Intent(getApplication(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            loading.setValue(false);
            dispose();
        }
    }

    protected final boolean errorEvent(Throwable e) {
        loading.setValue(false);
        success.setValue(false);
        error.setValue(e);
        if (isNetworkConnected()) {
            if (checkToken(e.getMessage())) {
                return false;
            }
        }
        return true;
    }

    protected final boolean checkToken(String message) {
        errorMessage.setValue(message);
        if (message.equals("토큰이 만료되었습니다")) {
            addDisposable(tokenClient.getNewToken(new TokenRequest(manager.getToken().getRefreshToken())),getTokenObserver());
            return true;
        }
        return false;
    }

    protected final boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) getApplication().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return true;
        }
        else {
            errorMessage.setValue("네트워크를 연결해 주세요");
            return false;
        }
    }
}
