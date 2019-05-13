package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class NetworkClient {
    public NetworkClient() {
    }

    public Single<Response> actionService(Call service){
        return Single.create(observer ->
                service.enqueue(new Callback<Response>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.isSuccessful()) {
                            observer.onSuccess(response.body());
                        } else {
                            try {
                                JSONObject errorBody = new JSONObject(Objects
                                        .requireNonNull(
                                                response.errorBody()).string());
                                observer.onError(new Throwable(errorBody.getString("message")));
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        observer.onError(new Throwable("네트워크 상태를 확인하세요"));

                    }
                }));
    }
}
