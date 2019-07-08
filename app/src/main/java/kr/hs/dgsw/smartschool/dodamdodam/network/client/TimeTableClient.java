package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.TimeTables;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.get.TimeTableService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class TimeTableClient extends NetworkClient{
    private TimeTableService timeTableService;

    public TimeTableClient(){
        timeTableService = Utils.RETROFIT.create(TimeTableService.class);
    }

    public Single<TimeTables> getTimeTable(Token token) {
        return timeTableService.getTimeTable(token.getToken()).map(getResponseObjectsFunction());
    }
}
