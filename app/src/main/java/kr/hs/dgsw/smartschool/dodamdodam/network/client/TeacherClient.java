package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.CounselService;
import retrofit2.Call;
import retrofit2.Callback;

public class TeacherClient {
    private CounselService counsel;

    public TeacherClient() {
        counsel = Utils.RETROFIT.create(CounselService.class);
    }

    public Single<Teacher> getTeacher(String token) {
        return Single.create(observer -> {
            counsel.getTeacher(token).enqueue(new Callback<Response<Teacher>>() {
                @Override
                public void onResponse(Call<Response<Teacher>> call, retrofit2.Response<Response<Teacher>> response) {
                    if (response.isSuccessful()) {
                        observer.onSuccess(response.body().getData());
                    } else {
                        try {
                            JSONObject errorBody = new JSONObject(Objects
                                    .requireNonNull(response.errorBody()).string());
                            observer.onError(new Throwable(errorBody.getString("message")));
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response<Teacher>> call, Throwable t) {
                    observer.onError(new Throwable("네트워크상태를 확인하세요"));
                }
            });
        });
    }
}
