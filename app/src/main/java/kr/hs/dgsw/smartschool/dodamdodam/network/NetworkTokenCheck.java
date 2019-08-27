package kr.hs.dgsw.smartschool.dodamdodam.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.PlaceList;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.PlaceService;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.TokenService;

public class NetworkTokenCheck {

    private TokenService tokenService;
    private TokenManager tokenManager;
    private CompositeDisposable disposable;

    public NetworkTokenCheck(Context context) {
        tokenService = Utils.RETROFIT.create(TokenService.class);
        tokenManager = TokenManager.getInstance(context);
        disposable = new CompositeDisposable();
    }

    public void getNewToken() {
        Single<String> getNewToken = tokenService.getNewToken(tokenManager.getToken().getRefreshToken()).map(getResponseObjectsFunction());

        addDisposable(getNewToken, new TokenDisposableSingleObserver());
    }

    void addDisposable(Single single, DisposableSingleObserver observer) {
        disposable.add((Disposable) single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));
    }

    private class TokenDisposableSingleObserver extends DisposableSingleObserver<String> {
        @Override
        public void onSuccess(String s) {
            tokenManager.setToken(s, tokenManager.getToken().getRefreshToken());
            dispose();
        }

        @Override
        public void onError(Throwable e) {
            Log.e("tokenError",  e.getMessage());
            try {
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            dispose();
        }
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
            return response.body().getData();
        };
    }
}
