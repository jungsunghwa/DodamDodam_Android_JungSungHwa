package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Type;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Types;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.BusService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class BusClient {
    private BusService bus;

    public BusClient() {
        bus = Utils.RETROFIT.create(BusService.class);
    }

    public Single<String> postBusApply(Token token, BusRequest request) {
        return Single.create(observer -> bus.postBusApply(token.getToken(), request).enqueue(new Callback<Response>() {
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
            @EverythingIsNonNull
            public void onFailure(Call<Response> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }

    public Single<String> deleteBusApply(Token token, int idx) {
        return Single.create(observer -> bus.deleteBusApply(token.getToken(), idx).enqueue(new Callback<Response>() {
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
            @EverythingIsNonNull
            public void onFailure(Call<Response> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }

    public Single<Bus> getMyBusApply(Token token) {
        return Single.create(observer -> bus.getMyBusApply(token.getToken()).enqueue(new Callback<Response<Bus>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Bus>> call, retrofit2.Response<Response<Bus>> response) {
                if (response.isSuccessful()) {
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
            @EverythingIsNonNull
            public void onFailure(Call<Response<Bus>> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }

    public Single<String> putModifyBusApply(Token token, int idx, BusRequest request) {
        return Single.create(observer -> bus.putModifyBusApply(token.getToken(), idx, request).enqueue(new Callback<Response>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    observer.onSuccess(response.body().getMessage());
                } else {
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody().string()));
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }

    public Single<Types> getBusType(Token token) {
        return Single.create(observer -> bus.getBusType(token.getToken()).enqueue(new Callback<Response<Types>>() {
            @Override
            public void onResponse(Call<Response<Types>> call, retrofit2.Response<Response<Types>> response) {
                if (response.isSuccessful()) {
                    observer.onSuccess(response.body().getData());
                } else {
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody().string()));
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<Response<Types>> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }


}