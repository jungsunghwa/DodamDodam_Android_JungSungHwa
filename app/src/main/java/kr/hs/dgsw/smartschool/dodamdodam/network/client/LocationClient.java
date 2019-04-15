package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LoginRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.PostLocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.Login;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.PostLocation;
import retrofit2.Call;
import retrofit2.Callback;

public class LocationClient {
    private PostLocation postLocation;

    public LocationClient(){
        postLocation = Utils.RETROFIT.create(PostLocation.class);
    }

    public Single<String> postLocation(PostLocationRequest request, String token) {
        return Single.create(observer -> {
            postLocation.postLocation(token, request).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()){
                        observer.onSuccess(response.body().getMessage());
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
                    observer.onError( new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }
}
