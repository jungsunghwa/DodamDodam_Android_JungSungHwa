package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leaves;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Offbase;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Passes;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.OffbaseRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.OffbaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class OffbaseClient {

    private OffbaseService offbase;

    public OffbaseClient() {
        offbase = Utils.RETROFIT.create(OffbaseService.class);
    }

    public Single<Offbase> getOffbase(Token token) {
        return Single.create(observer -> offbase.getOffbase(token.getToken()).enqueue(new Callback<Response<Offbase>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Offbase>> call, retrofit2.Response<Response<Offbase>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Offbase>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Offbase> getOffbaseAllow(Token token) {
        return Single.create(observer -> offbase.getOffbaseAllow(token.getToken()).enqueue(new Callback<Response<Offbase>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Offbase>> call, retrofit2.Response<Response<Offbase>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Offbase>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Offbase> getOffbaseCancel(Token token) {
        return Single.create(observer -> offbase.getOffbaseCancel(token.getToken()).enqueue(new Callback<Response<Offbase>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Offbase>> call, retrofit2.Response<Response<Offbase>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Offbase>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Leaves> getLeaves(Token token) {
        return Single.create(observer -> offbase.getLeaves(token.getToken()).enqueue(new Callback<Response<Leaves>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Leaves>> call, retrofit2.Response<Response<Leaves>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Leaves>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Leaves> getLeaveAllows(Token token) {
        return Single.create(observer -> offbase.getLeaveAllows(token.getToken()).enqueue(new Callback<Response<Leaves>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Leaves>> call, retrofit2.Response<Response<Leaves>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Leaves>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Leaves> getLeaveCancels(Token token) {
        return Single.create(observer -> offbase.getLeaveCancels(token.getToken()).enqueue(new Callback<Response<Leaves>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Leaves>> call, retrofit2.Response<Response<Leaves>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Leaves>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Leave> getLeaveById(Token token, int leaveId) {
        return Single.create(observer -> offbase.getLeaveById(token.getToken(), leaveId).enqueue(new Callback<Response<Leave>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Leave>> call, retrofit2.Response<Response<Leave>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Leave>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Passes> getPasses(Token token) {
        return Single.create(observer -> offbase.getPasses(token.getToken()).enqueue(new Callback<Response<Passes>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Passes>> call, retrofit2.Response<Response<Passes>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Passes>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Passes> getPassAllows(Token token) {
        return Single.create(observer -> offbase.getPassAllows(token.getToken()).enqueue(new Callback<Response<Passes>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Passes>> call, retrofit2.Response<Response<Passes>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Passes>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Passes> getPassCancels(Token token) {
        return Single.create(observer -> offbase.getPassCancels(token.getToken()).enqueue(new Callback<Response<Passes>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Passes>> call, retrofit2.Response<Response<Passes>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Passes>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Pass> getPassById(Token token, int passId) {
        return Single.create(observer -> offbase.getPassById(token.getToken(), passId).enqueue(new Callback<Response<Pass>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Pass>> call, retrofit2.Response<Response<Pass>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Pass>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<String> postLeave(Token token, OffbaseRequest request) {
        return Single.create(observer -> offbase.postLeave(token.getToken(), request).enqueue(new Callback<Response>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getMessage());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<String> putLeave(Token token, int leaveId, OffbaseRequest request) {
        return Single.create(observer -> offbase.putLeave(token.getToken(), leaveId, request).enqueue(new Callback<Response>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getMessage());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<String> postPass(Token token, OffbaseRequest request) {
        return Single.create(observer -> offbase.postPass(token.getToken(), request).enqueue(new Callback<Response>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getMessage());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<String> putPass(Token token, int passId, OffbaseRequest request) {
        return Single.create(observer -> offbase.putPass(token.getToken(), passId, request).enqueue(new Callback<Response>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getMessage());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<String> deleteLeave(Token token, int leaveId) {
        return Single.create(observer -> offbase.deleteLeave(token.getToken(), leaveId).enqueue(new Callback<Response>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getMessage());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<String> deletePass(Token token, int passId) {
        return Single.create(observer -> offbase.deletePass(token.getToken(), passId).enqueue(new Callback<Response>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getMessage());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }
}
