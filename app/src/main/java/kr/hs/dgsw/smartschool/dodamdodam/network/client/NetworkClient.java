package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.util.Log;

import org.json.JSONObject;

import java.util.Objects;

import io.reactivex.functions.Function;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;

@SuppressWarnings("unchecked")
public class NetworkClient {

    <T> Function<retrofit2.Response<Response<T>>, T> getResponseObjectsFunction() {
        return response -> {
            checkResponseErrorBody(response);
            Log.e("Status", response.body().getStatus() + "");
            return response.body().getData();
        };
    }

    Function<retrofit2.Response<Response>, String> getResponseMessageFunction() {
        return response -> {
            checkResponseErrorBody(response);
            Log.e("Status", response.body().getStatus() + "");
            return response.body().getMessage();
        };
    }

    private void checkResponseErrorBody(retrofit2.Response response) throws Exception {
        if (!response.isSuccessful()) {
            JSONObject errorBody = new JSONObject(Objects
                    .requireNonNull(
                            response.errorBody()).string());
            Log.e("Status", errorBody.getInt("status") + "");
            throw new Exception(errorBody.getString("message"));
        }
    }

}
