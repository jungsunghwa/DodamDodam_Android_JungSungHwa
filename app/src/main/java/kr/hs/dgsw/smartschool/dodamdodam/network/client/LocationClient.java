package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Location;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.post.LocationService;
import retrofit2.Call;
import retrofit2.Callback;

public class LocationClient {
    private LocationService location;

    public LocationClient(){
        location = Utils.RETROFIT.create(LocationService.class);
    }

    public Single<String> postLocation(LocationRequest request, String token) {
        return Single.create(observer -> {
            location.postLocation(token, request).enqueue(new Callback<Response>() {
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

    public Single<Location> getStudentLocation(String token) {
        return Single.create(observer -> {
            location.getStudentLocation(token,1).enqueue(new Callback<Response<Location>>() {
                @Override
                public void onResponse(Call<Response<Location>> call, retrofit2.Response<Response<Location>> response) {
                    if (response.isSuccessful()){
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
                public void onFailure(Call<Response<Location>> call, Throwable t) {
                    observer.onError( new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }
}
