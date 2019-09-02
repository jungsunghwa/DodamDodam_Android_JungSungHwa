package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFounds;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.LostFoundService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class LostFoundClient extends NetworkClient {
    private LostFoundService lostFound;

    public LostFoundClient() {
        lostFound = Utils.RETROFIT.create(LostFoundService.class);
    }

    public Single<List<LostFound>> getLostFound(Token token, Integer page, Integer type) {
        return lostFound.getLostFound(token.getToken(), page, type).map(response -> {
            if (!response.isSuccessful()) {
                JSONObject errorBody = new JSONObject(Objects
                        .requireNonNull(
                    response.errorBody()).string());
                    Log.e("aaa", errorBody.getString("message"));
                    throw new Exception(errorBody.getString("message"));
                }
            Log.e("aaa", response.body().getStatus() + "");
            return response.body().getData().getLostFounds();
        });
    }

    public Single<String> postCreateLostFound(Token token, LostFoundRequest request) {
        return lostFound.postCreateLostFound(token.getToken(), request).map(getResponseMessageFunction());
    }

    public Single<String> putLostFound(Token token, LostFoundRequest request) {
        return lostFound.putLostFound(token.getToken(), request).map(getResponseMessageFunction());
    }

    public Single<String> deleteLostFound(Token token, Integer idx) {
        return lostFound.deleteLostFound(token.getToken(), idx).map(getResponseMessageFunction());
    }


    public Single<List<LostFound>> getLostFoundSearch(Token token, String search) {
        return lostFound.getLostFoundSearch(token.getToken(), search).map(response -> {
            if (!response.isSuccessful()) {
                JSONObject errorBody = new JSONObject(Objects
                        .requireNonNull(
                                response.errorBody()).string());
                Log.e("aaa", errorBody.getString("message"));
                throw new Exception(errorBody.getString("message"));
            }
            Log.e("aaa", response.body().getStatus() + "");
            return response.body().getData().getLostFounds();
        });
    }
}
