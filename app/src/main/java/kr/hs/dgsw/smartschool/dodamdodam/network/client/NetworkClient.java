package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class NetworkClient<T> {

    private CompositeDisposable disposable;

    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    public final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<T> data = new MutableLiveData<>();


    public LiveData<T> getData() {
        return data;
    }

    public LiveData<Throwable> getError() {
        return error;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    NetworkClient() {
        disposable = new CompositeDisposable();
    }

    void addDisposable(Single single, DisposableSingleObserver observer) {
        disposable.add((Disposable) single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));
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

    public Single<Response> actionService(Call service) {
        return Single.create(observer ->
                service.enqueue(new Callback<Response>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            observer.onSuccess(response.body());
                        } else {
                            JSONObject errorBody;
                            try {
                                errorBody = new JSONObject(Objects
                                        .requireNonNull(
                                                response.errorBody()).string());

                                if (errorBody.getInt("Status") == 405){
                                    Response response1 = new Response();
                                    response1.setStatus(errorBody.getInt("status"));
                                    response1.setMessage(errorBody.getString("message"));
                                    observer.onSuccess(response1);
                                }else
                                    observer.onError(new Throwable(errorBody.getString("message")));

                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    @EverythingIsNonNull
                    public void onFailure(Call<Response> call, Throwable t) {
                        observer.onError(new Throwable("네트워크 상태를 확인하세요"));

                    }
                }));
    }
}
