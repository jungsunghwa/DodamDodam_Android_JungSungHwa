package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.B1ndService;
import kr.hs.dgsw.b1nd.service.interfaces.OnLoginSuccessListener;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginData;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginRequest;
import kr.hs.dgsw.b1nd.service.service.B1ndLogin;
import kr.hs.dgsw.smartschool.dodamdodam.Constants;
import retrofit2.internal.EverythingIsNonNull;

public class LoginClient extends NetworkClient {

    public LoginClient() {
        B1ndService.setHostURL(Constants.DEFAULT_HOST);
    }

    public Single<LoginData> login(LoginRequest request) {
        return Single.create(observer -> new B1ndLogin().login(request, new OnLoginSuccessListener() {
            @Override
            @EverythingIsNonNull
            public void onSuccess(int status, String message, String token, String refreshToken) {
                if (status == 200) {
                    LoginData loginData = new LoginData();
                    loginData.setToken(token);
                    loginData.setRefreshToken(refreshToken);

                    observer.onSuccess(loginData);
                    return;
                }
                if (status == 401) {
                    observer.onError(new Throwable("아이디 또는 비밀번호가 일치하지 않습니다"));
                }
                observer.onError(new Throwable(message));
            }

            @Override
            @EverythingIsNonNull
            public void onFail(Throwable throwable, String message) {
                throwable.printStackTrace();
                observer.onError(new Throwable(throwable.getMessage()));
            }
        }));
    }
}
