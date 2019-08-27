package kr.hs.dgsw.smartschool.dodamdodam.network.client;


import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Teachers;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.CounselService;
import retrofit2.Call;
import retrofit2.Callback;

public class TeacherClient extends NetworkClient {

    private CounselService counsel;

    public TeacherClient(Context context) {
        super(context);
        counsel = Utils.RETROFIT.create(CounselService.class);
    }

    public Single<Teachers> getTeacher(Token token) {
        return counsel.getTeacher(token.getToken()).map(getResponseObjectsFunction());
    }
}
