package kr.hs.dgsw.smartschool.dodamdodam.network;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.B1ndService;
import kr.hs.dgsw.b1nd.service.interfaces.OnLoginSuccessListener;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginData;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginRequest;
import kr.hs.dgsw.b1nd.service.service.B1ndLogin;
import kr.hs.dgsw.smartschool.dodamdodam.Constants;

public class NetworkClient {

    public NetworkClient() {
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
                    observer.onError(throwable);
                }
            });
        });
    }
}
