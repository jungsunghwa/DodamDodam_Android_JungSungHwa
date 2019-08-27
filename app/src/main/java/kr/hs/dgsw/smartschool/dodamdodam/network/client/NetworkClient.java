package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.Objects;

import io.reactivex.functions.Function;
import kr.hs.dgsw.smartschool.dodamdodam.network.NetworkTokenCheck;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;

@SuppressWarnings("unchecked")
public class NetworkClient {

    private NetworkTokenCheck tokenClient;

    NetworkClient(Context context) {
        tokenClient = new NetworkTokenCheck(context);
    }

    <T> Function<retrofit2.Response<Response<T>>, T> getResponseObjectsFunction() {
        return response -> {
            if (!response.isSuccessful()) {
                JSONObject errorBody = new JSONObject(Objects
                        .requireNonNull(
                                response.errorBody()).string());
                Log.e("Status", errorBody.getString("message"));
                throw new Exception(errorBody.getString("message"));
            }
            Log.e("Status", response.body().getStatus() + "");
            if (response.body().getStatus() == 410) {
                tokenClient.getNewToken();
                throw new Exception(response.body().getMessage());
            }
            return response.body().getData();
        };
    }

}
