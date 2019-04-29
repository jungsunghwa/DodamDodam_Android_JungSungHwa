package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Model.TimeTables;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.get.TimeTableService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class TimeTableClient {
    private TimeTableService timeTableService;

    public TimeTableClient(){
        timeTableService = Utils.RETROFIT.create(TimeTableService.class);
    }

    public Single<List<Time>> getTimeTable(Token token) {
        return Single.create(observer -> timeTableService.getTimeTable(token.getToken()).enqueue(new Callback<Response<TimeTables>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<TimeTables>> call, retrofit2.Response<Response<TimeTables>> response) {
                if (response.isSuccessful()){
                    observer.onSuccess(response.body().getData().getTimeTables());
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
            @EverythingIsNonNull
            public void onFailure(Call<Response<TimeTables>> call, Throwable t) {
                observer.onError( new Throwable("네트워크 상태를 확인하세요"));
            }
        }));
    }
}
