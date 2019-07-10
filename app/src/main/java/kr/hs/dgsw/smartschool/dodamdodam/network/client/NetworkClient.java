package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

@SuppressWarnings("unchecked")
public class NetworkClient {

    private CompositeDisposable disposable;

    NetworkClient() {
        disposable = new CompositeDisposable();
    }

    void addDisposable(Single single, DisposableSingleObserver observer) {
        disposable.add((Disposable) single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));
    }

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

                                if (errorBody.getInt("Status") == 405) {
                                    Response response1 = new Response();
                                    response1.setStatus(errorBody.getInt("status"));
                                    response1.setMessage(errorBody.getString("message"));
                                    observer.onSuccess(response1);
                                } else
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

    <T> Function<retrofit2.Response<Response<T>>, T> getResponseObjectsFunction() {
        return response -> {
            if (!response.isSuccessful()) {
                JSONObject errorBody = new JSONObject(Objects
                        .requireNonNull(
                                response.errorBody()).string());
                Log.e("Status", errorBody.getString("message"));
                throw new Exception(errorBody.getString("message"));
            }
            Log.e("Status", response.body().getStatus() + "");
            return (T) response.body().getData();
        };
    }

    <T extends retrofit2.Response, R> Function<retrofit2.Response<Response<T>>, R> ddd() {
        return response -> {
            if (!response.isSuccessful()) {
                JSONObject errorBody = new JSONObject(Objects
                        .requireNonNull(
                                response.errorBody()).string());
                Log.e("Status", errorBody.getString("message"));
                throw new Exception(errorBody.getString("message"));
            }
            Log.e("Status", response.body().getStatus() + "");
            return (R) response.body().getData();
        };
    }


}
