package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Buses;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.BusService;
import retrofit2.Call;
import retrofit2.Callback;

public class BusClient {
    private BusService bus;

    public BusClient() {
        bus = Utils.RETROFIT.create(BusService.class);
    }

    public Single<String> postBusApply(String token, Integer type) {
        return Single.create(observer -> {
            bus.postBusApply(token, new Bus(type)).enqueue(new Callback<Response<Buses>>() {
                @Override
                public void onResponse(Call<Response<Buses>> call, retrofit2.Response<Response<Buses>> response) {
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
                public void onFailure(Call<Response<Buses>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }

    public Single<String> deleteBusApply(String token, int idx) {
        return Single.create(observer -> {
            bus.deleteBusApply(token, idx).enqueue(new Callback<Response>() {
                @Override
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
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }

    public Single<Bus> getMyBusApply(String token) {
        return Single.create(observer -> {
            bus.getMyBusApply(token).enqueue(new Callback<Response<Bus>>() {
                @Override
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
                public void onFailure(Call<Response<Bus>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }

    public Single<String> putModifyBusApply(String token, int idx, Bus buses) {
        return Single.create(observer -> {
            bus.putModifyBusApply(token, idx, buses).enqueue(new Callback<Response<Buses>>() {
                @Override
                public void onResponse(Call<Response<Buses>> call, retrofit2.Response<Response<Buses>> response) {
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
                public void onFailure(Call<Response<Buses>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }


}