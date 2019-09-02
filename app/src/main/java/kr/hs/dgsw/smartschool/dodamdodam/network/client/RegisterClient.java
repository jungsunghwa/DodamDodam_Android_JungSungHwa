package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.B1ndService;
import kr.hs.dgsw.b1nd.service.interfaces.OnRegisterSuccessListener;
import kr.hs.dgsw.b1nd.service.retrofit2.response.register.StudentRegisterRequest;
import kr.hs.dgsw.b1nd.service.retrofit2.response.register.TeacherRegisterRequest;
import kr.hs.dgsw.b1nd.service.service.B1ndRegister;
import kr.hs.dgsw.smartschool.dodamdodam.Constants;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;


public class RegisterClient extends NetworkClient {

    public RegisterClient() {
        B1ndService.setHostURL(Constants.DEFAULT_HOST);
    }

    public Single<String> studentRegister(StudentRegisterRequest request) {
        return Single.create(observer -> new B1ndRegister().studentRegister(request, new OnRegisterSuccessListener() {
            @Override
            public void onSuccess(int status, String message) {
                if (status == 200) {
                    observer.onSuccess(message);
                }
            }

            @Override
            public void onFail(Throwable throwable, String message) {
                throwable.printStackTrace();
                observer.onError(new Throwable(throwable.getMessage()));
            }
        }));
    }

    public Single<String> teacherRegister(TeacherRegisterRequest request) {
        return Single.create(observer -> new B1ndRegister().teacherRegister(request, new OnRegisterSuccessListener() {
            @Override
            public void onSuccess(int status, String message) {
                if (status == 200) {
                    observer.onSuccess(message);
                }
            }

            @Override
            public void onFail(Throwable throwable, String message) {
                throwable.printStackTrace();
                observer.onError(new Throwable(throwable.getMessage()));
            }
        }));
    }
}
