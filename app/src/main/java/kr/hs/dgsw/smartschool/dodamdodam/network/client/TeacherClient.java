package kr.hs.dgsw.smartschool.dodamdodam.network.client;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Teachers;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.CounselService;
import retrofit2.Call;
import retrofit2.Callback;

public class TeacherClient {
    CounselService counsel;

    public TeacherClient() {
        counsel = Utils.RETROFIT.create(CounselService.class);
    }

    public Single<Teachers> getTeacher(Token token) {
        return Single.create(observer -> counsel.getTeacher(token.getToken()).enqueue(new Callback<Response<Teachers>>() {
            @Override
            public void onResponse(Call<Response<Teachers>> call, retrofit2.Response<Response<Teachers>> response) {
                if (response.isSuccessful()) {
                    observer.onSuccess(response.body().getData());
                } else {
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<Teachers>> call, Throwable t) {
                observer.onError(new Throwable("네트워크상태를 확인하세요"));
            }
        }));
    }
}
