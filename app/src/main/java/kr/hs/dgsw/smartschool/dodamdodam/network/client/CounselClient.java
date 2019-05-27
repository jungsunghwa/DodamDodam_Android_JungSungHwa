package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsels;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.CounselService;
import retrofit2.Call;
import retrofit2.Callback;

public class CounselClient {
    private CounselService counsel;

    public CounselClient() { counsel = Utils.RETROFIT.create(CounselService.class); }

    public Single<Counsel> getAllCounsel(String token) {
        return Single.create(observer -> {
            counsel.getAllCounsel(token).enqueue(new Callback<Response<Counsel>>() {
                @Override
                public void onResponse(Call<Response<Counsel>> call, retrofit2.Response<Response<Counsel>> response) {
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
                public void onFailure(Call<Response<Counsel>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }

    public Single<String> postCounsel(String token, CounselRequest request) {
        return Single.create(observer -> {
            counsel.postCounsel(token, request).enqueue(new Callback<Response<Counsels>>() {
                @Override
                public void onResponse(Call<Response<Counsels>> call, retrofit2.Response<Response<Counsels>> response) {
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
                public void onFailure(Call<Response<Counsels>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }

    public Single<Counsel> getCertainCounsel(String token, int counselIdx) {
        return Single.create(observer -> {
            counsel.getCertainCounsel(token, counselIdx).enqueue(new Callback<Response<Counsel>>() {
                @Override
                public void onResponse(Call<Response<Counsel>> call, retrofit2.Response<Response<Counsel>> response) {
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
                public void onFailure(Call<Response<Counsel>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }

    public Single<String> deleteCounsel(String token, int counselIdx) {
        return Single.create(observer -> {
            counsel.deleteCounsel(token, counselIdx).enqueue(new Callback<Response>() {
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
                    observer.onError(new Throwable("네트워크상태를 확인히세요"));
                }
            });
        });
    }

    public Single<String> postCounselAllow(String token, CounselRequest request) {
        return Single.create(observer -> {
            counsel.postCounselAllow(token, request).enqueue(new Callback<Response<Counsels>>() {
                @Override
                public void onResponse(Call<Response<Counsels>> call, retrofit2.Response<Response<Counsels>> response) {
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
                public void onFailure(Call<Response<Counsels>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }

    public Single<String> postCounselCancel(String token, CounselRequest request) {
        return Single.create(observer -> {
            counsel.postCounselCancel(token, request).enqueue(new Callback<Response<Counsels>>() {
                @Override
                public void onResponse(Call<Response<Counsels>> call, retrofit2.Response<Response<Counsels>> response) {
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
                public void onFailure(Call<Response<Counsels>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }
}
