package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import retrofit2.Response;

abstract class BaseViewModel<T> extends ViewModel {
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;
    private SingleObserver subscription;

    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    public final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<T> data = new MutableLiveData<>();

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

    BaseViewModel(Context context) {
        disposable = new CompositeDisposable();
        subscription = new SingleObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }
        };
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    void addDisposable(Single single, DisposableSingleObserver observer) {
        disposable.add((Disposable) single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));

//        subscription = single
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(observer);
    }

    DisposableSingleObserver<String> baseObserver = new DisposableSingleObserver<String>() {
        @Override
        public void onSuccess(String s) {
            successMessage.setValue(s);
            loading.setValue(false);
        }

        @Override
        public void onError(Throwable e) {
            errorMessage.setValue(e.getMessage());
            loading.setValue(false);
        }
    };

    DisposableSingleObserver<T> dataObserver = new DisposableSingleObserver<T>() {
        @Override
        public void onSuccess(T t) {
            data.setValue(t);
            loading.setValue(false);
        }

        @Override
        public void onError(Throwable e) {
            errorMessage.setValue(e.getMessage());
            error.setValue(e);
            loading.setValue(false);
        }
    };
}
