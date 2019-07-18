package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import okhttp3.ResponseBody;

abstract class BaseViewModel<T> extends AndroidViewModel {
    public final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    final MutableLiveData<String> successMessage = new MutableLiveData<>();
    final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    final MutableLiveData<Throwable> error = new MutableLiveData<>();
    final MutableLiveData<T> data = new MutableLiveData<>();
    DatabaseHelper helper;
    private CompositeDisposable disposable;
    private SingleObserver subscription;

    BaseViewModel(Application application) {
        super(application);
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getInstance(application);
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
            errorMessage.setValue(e.getMessage());
            loading.setValue(false);
            success.setValue(false);
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
            errorMessage.setValue(e.getMessage());
            error.setValue(e);
            loading.setValue(false);
            success.setValue(false);
            dispose();
        }
    }
}
