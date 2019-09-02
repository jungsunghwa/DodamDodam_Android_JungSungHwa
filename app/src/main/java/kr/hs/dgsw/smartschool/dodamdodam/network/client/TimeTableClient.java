package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.TimeTables;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.TimeTableService;

public class TimeTableClient extends NetworkClient{
    private TimeTableService timeTableService;

    public TimeTableClient() {
        timeTableService = Utils.RETROFIT.create(TimeTableService.class);
    }

    public Single<TimeTables> getTimeTable(Token token) {
        return timeTableService.getTimeTable(token.getToken()).map(getResponseObjectsFunction());
    }
}
