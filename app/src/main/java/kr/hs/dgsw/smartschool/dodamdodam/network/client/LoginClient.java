package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.B1ndService;
import kr.hs.dgsw.b1nd.service.interfaces.OnLoginSuccessListener;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginData;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginRequest;
import kr.hs.dgsw.b1nd.service.service.B1ndLogin;
import kr.hs.dgsw.smartschool.dodamdodam.Constants;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.Login;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginClient {

    public LoginClient() {
        B1ndService.setHostURL(Constants.DEFAULT_HOST);
    }

    public Single<LoginData> login(LoginRequest request) {
        return Single.create(observer -> {
            new B1ndLogin().login(request, new OnLoginSuccessListener() {
                @Override
                public void onSuccess(int status, String message, String token, String refreshToken) {
                    LoginData loginData = new LoginData();
                    loginData.setToken(token);
                    loginData.setRefreshToken(refreshToken);

                    observer.onSuccess(loginData);
                }

                @Override
                public void onFail(Throwable throwable, String message) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }

            });
        });
    }
}
