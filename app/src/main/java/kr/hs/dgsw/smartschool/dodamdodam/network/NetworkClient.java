package kr.hs.dgsw.smartschool.dodamdodam.network;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import androidx.annotation.Nullable;
import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.service.B1ndLogin;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Login.LoginData;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Login.LoginRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class NetworkClient {
    Login login;

    public NetworkClient(){
        login = Utils.RETROFIT.create(Login.class);
    }

    public Single<LoginData> login(LoginRequest request) {
        return Single.create(observer -> {
            login.login(request).enqueue(new Callback<Response<LoginData>>() {
                @Override
                public void onResponse(Call<Response<LoginData>> call, retrofit2.Response<Response<LoginData>> response) {
                    if (response.isSuccessful()){
                        observer.onSuccess(response.body().getData());
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
                public void onFailure(Call<Response<LoginData>> call, Throwable t) {
                    observer.onError( new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }
}
