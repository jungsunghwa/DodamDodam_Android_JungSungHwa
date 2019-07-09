package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsels;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.CounselService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class CounselClient {
    private CounselService counsel;

    public CounselClient() { counsel = Utils.RETROFIT.create(CounselService.class); }

    public Single<List<Counsel>> getAllCounsel(Token token) {
        return Single.create(observer -> counsel.getAllCounsel(token.getToken()).enqueue(new Callback<Response<List<Counsel>>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<List<Counsel>>> call, retrofit2.Response<Response<List<Counsel>>> response) {
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
            public void onFailure(Call<Response<List<Counsel>>> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }

    public Single<String> postCounsel(Token token, CounselRequest request) {
        return Single.create(observer -> counsel.postCounsel(token.getToken(), request).enqueue(new Callback<Response<Counsels>>() {
            @Override
            @EverythingIsNonNull
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
            @EverythingIsNonNull
            public void onFailure(Call<Response<Counsels>> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }

    public Single<List<Counsel>> getCertainCounsel(Token token, int counselIdx) {
        return Single.create(observer -> counsel.getCertainCounsel(token.getToken(), counselIdx).enqueue(new Callback<Response<List<Counsel>>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<List<Counsel>>> call, retrofit2.Response<Response<List<Counsel>>> response) {
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
            public void onFailure(Call<Response<List<Counsel>>> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }

    public Single<String> deleteCounsel(Token token, int counselIdx) {
        return Single.create(observer -> counsel.deleteCounsel(token.getToken(), counselIdx).enqueue(new Callback<Response>() {
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
                observer.onError(new Throwable("네트워크상태를 확인히세요"));
            }
        }));
    }

    public Single<String> postCounselAllow(Token token, CounselRequest request) {
        return Single.create(observer -> counsel.postCounselAllow(token.getToken(), request).enqueue(new Callback<Response<Counsels>>() {
            @Override
            @EverythingIsNonNull
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
            @EverythingIsNonNull
            public void onFailure(Call<Response<Counsels>> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }

    public Single<String> postCounselCancel(Token token, CounselRequest request) {
        return Single.create(observer -> counsel.postCounselCancel(token.getToken(), request).enqueue(new Callback<Response<Counsels>>() {
            @Override
            @EverythingIsNonNull
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
            @EverythingIsNonNull
            public void onFailure(Call<Response<Counsels>> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }
}
