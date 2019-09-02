package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.PlaceList;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.activity.LoginActivity;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.NetworkClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.TokenRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.PlaceService;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.TokenService;

public class TokenClient extends NetworkClient {

    private TokenService tokenService;

    public TokenClient() {
        tokenService = Utils.RETROFIT.create(TokenService.class);
    }

    public Single<String> getNewToken(TokenRequest tokenRequest) {
        return tokenService.getNewToken(tokenRequest).map(getResponseObjectsFunction());
    }
}
