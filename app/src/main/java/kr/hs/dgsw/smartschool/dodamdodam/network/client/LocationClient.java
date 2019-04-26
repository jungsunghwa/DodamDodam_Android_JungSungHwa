package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.post.LocationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class LocationClient {
    private LocationService location;

    public LocationClient() {
        location = Utils.RETROFIT.create(LocationService.class);
    }

    public Single<String> postLocation(LocationRequest request, String token, String method) {
        Call<Response> retrofit = location.postLocation(token, request);
        if (method.equals("PUT")) retrofit = location.putLocation(token, request);
        Call<Response> finalRetrofit = retrofit;
        return Single.create(observer -> {
            finalRetrofit.enqueue(new Callback<Response>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    if (response.isSuccessful()) {
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
                    observer.onError(new Throwable("네트워크 상태를 확인하세요"));
                }
            });
        });
    }

    public Single<LocationRequest> getMyLocation(String token) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return Single.create(observer ->
                location.getMyLocation(token,date).enqueue(new Callback<Response<LocationRequest>>() {
                    @Override
                    @EverythingIsNonNull
                    public void onResponse(Call<Response<LocationRequest>> call, retrofit2.Response<Response<LocationRequest>> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
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
                    public void onFailure(Call<Response<LocationRequest>> call, Throwable t) {
                        observer.onError(new Throwable("네트워크 상태를 확인하세요"));

                    }
                }));
    }
}
