package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.B1ndService;
import kr.hs.dgsw.b1nd.service.interfaces.OnGetClassesListener;
import kr.hs.dgsw.b1nd.service.interfaces.OnLoginSuccessListener;
import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginData;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginRequest;
import kr.hs.dgsw.b1nd.service.service.B1ndGetClasses;
import kr.hs.dgsw.b1nd.service.service.B1ndLogin;
import kr.hs.dgsw.smartschool.dodamdodam.Constants;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;

public class ClassClient {
    public ClassClient() {
        B1ndService.setHostURL(Constants.DEFAULT_HOST);
    }

    public Single<ArrayList<ClassInfo>> getClasses(Token token) {
        return Single.create(observer -> new B1ndGetClasses().getClasses(token.getToken(), new OnGetClassesListener() {

            @Override
            public void onGetClassesSuccess(int status, kr.hs.dgsw.b1nd.service.model.ClassInfo[] classes) {
                if (status == 200){
                    observer.onSuccess((ArrayList<ClassInfo>) Arrays.asList(classes));
                    return;
                }
                observer.onError(new Throwable(status+""));
            }

            @Override
            public void onGetClassesFailed(int status, Throwable throwable, String message) {
                observer.onError(new Throwable(message));
            }
        }));
    }
}
