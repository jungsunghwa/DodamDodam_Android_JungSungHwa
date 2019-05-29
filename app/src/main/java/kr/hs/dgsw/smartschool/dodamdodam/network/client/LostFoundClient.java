package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFounds;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.LostFoundService;
import retrofit2.Call;
import retrofit2.Callback;

public class LostFoundClient {
    private LostFoundService lostFound;

    public LostFoundClient() {
        lostFound = Utils.RETROFIT.create(LostFoundService.class);
    }

    public Single<List<LostFound>> getLostFound(String token, Integer page, Integer type) {
        return Single.create(observer -> {
            lostFound.getLostFound(token, page, type).enqueue(new Callback<Response<LostFounds>>() {
                @Override
                public void onResponse(Call<Response<LostFounds>> call, retrofit2.Response<Response<LostFounds>> response) {
                    if (response.isSuccessful()) {
                        Log.d("ShowData", response.body().getData().getLostFounds()+"");
                        observer.onSuccess(response.body().getData().getLostFounds());
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
                public void onFailure(Call<Response<LostFounds>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }

    public Single<String> postCreateLostFound(String token, LostFoundRequest request) {
        return Single.create(observer -> {
            lostFound.postCreateLostFound(token, request).enqueue(new Callback<Response>() {
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

    public Single<String> putLostFound(String token, LostFoundRequest request) {
        return Single.create(observer -> {

            lostFound.putLostFound(token, request).enqueue(new Callback<Response>() {
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

    public Single<String> deleteLostFound(String token, Integer idx) {
        return Single.create(observer -> {
            lostFound.deleteLostFound(token, idx).enqueue(new Callback<Response>() {
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


    public Single<List<LostFound>> getLostFoundSearch(String token, String search) {
        return Single.create(observer -> {
            lostFound.getLostFoundSearch(token, search).enqueue(new Callback<Response<LostFounds>>() {
                @Override
                public void onResponse(Call<Response<LostFounds>> call, retrofit2.Response<Response<LostFounds>> response) {
                    if (response.isSuccessful()) {
                        observer.onSuccess(response.body().getData().getLostFounds());
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
                public void onFailure(Call<Response<LostFounds>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }
}
