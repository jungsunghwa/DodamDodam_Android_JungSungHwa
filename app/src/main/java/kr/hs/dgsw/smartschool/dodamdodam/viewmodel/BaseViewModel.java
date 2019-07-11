package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

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

abstract class BaseViewModel<T> extends ViewModel {
    public final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    final MutableLiveData<String> successMessage = new MutableLiveData<>();
    final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    final MutableLiveData<Throwable> error = new MutableLiveData<>();
    final MutableLiveData<T> data = new MutableLiveData<>();
    DatabaseHelper helper;
    DisposableSingleObserver<String> baseObserver = new BaseDisposableSingleObserver();
    DisposableSingleObserver<T> dataObserver = new DataDisposableSingleObserver();
    private CompositeDisposable disposable;
    private SingleObserver subscription;

    BaseViewModel(Context context) {
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getInstance(context);
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

//        subscription = single
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(observer);
    }

    DisposableSingleObserver<String> getBaseObserver() {
        if (baseObserver.isDisposed())
            synchronized (BaseViewModel.class) {
                if (baseObserver.isDisposed())
                    baseObserver = new BaseDisposableSingleObserver();
            }
        return baseObserver;
    }

    DisposableSingleObserver<T> getDataObserver() {
        if (dataObserver.isDisposed())
            synchronized (BaseViewModel.class) {
                if (dataObserver.isDisposed())
                    dataObserver = new DataDisposableSingleObserver();
            }
        return dataObserver;
    }

    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    class BaseDisposableSingleObserver extends DisposableSingleObserver<String> {
        @Override
        public void onSuccess(String s) {
            successMessage.setValue(s);
            loading.setValue(false);
            baseObserver.dispose();
            success.setValue(true);
        }

        @Override
        public void onError(Throwable e) {
            errorMessage.setValue(e.getMessage());
            loading.setValue(false);
            baseObserver.dispose();
            success.setValue(false);
        }
    }

    class DataDisposableSingleObserver extends DisposableSingleObserver<T> {
        @Override
        public void onSuccess(T t) {
            data.setValue(t);
            loading.setValue(false);
            dataObserver.dispose();
            success.setValue(true);
        }

        @Override
        public void onError(Throwable e) {
            errorMessage.setValue(e.getMessage());
            error.setValue(e);
            loading.setValue(false);
            dataObserver.dispose();
            success.setValue(false);
        }
    }
}
