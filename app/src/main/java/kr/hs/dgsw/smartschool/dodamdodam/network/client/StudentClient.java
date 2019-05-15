package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.B1ndService;
import kr.hs.dgsw.b1nd.service.interfaces.OnGetAllProfileListener;
import kr.hs.dgsw.b1nd.service.interfaces.OnGetClassesListener;
import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.b1nd.service.model.Student;
import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.b1nd.service.service.B1ndGetAllProfileService;
import kr.hs.dgsw.b1nd.service.service.B1ndGetClasses;
import kr.hs.dgsw.smartschool.dodamdodam.Constants;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;

public class StudentClient {
    public StudentClient() {
        B1ndService.setHostURL(Constants.DEFAULT_HOST);
    }

    public Single<ArrayList<Member>> getMember(Token token){
        return Single.create(observer -> new B1ndGetAllProfileService().getAllProfile(token.getToken(), new OnGetAllProfileListener() {
            @Override
            public void onGetAllProfileSuccess(int status, ArrayList<Member> members) {

                observer.onSuccess(members);
            }

            @Override
            public void onGetAllProfileFailed(Throwable throwable, String message) {
                observer.onError(new Throwable(message));
            }
        }));
    }

    public Single<ArrayList<ClassInfo>> getClasses(Token token) {
        return Single.create(observer -> new B1ndGetClasses().getClasses(token.getToken(), new OnGetClassesListener() {

            @Override
            public void onGetClassesSuccess(int status, kr.hs.dgsw.b1nd.service.model.ClassInfo[] classes) {
                if (status == 200){
                    ArrayList<ClassInfo> list = new ArrayList<>(Arrays.asList(classes));
                    observer.onSuccess(list);
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
