package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.get;

import kr.hs.dgsw.smartschool.dodamdodam.model.TimeTables;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TimeTableService {
    @GET("time-table")
    Call<Response<TimeTables>> getTimeTable(
            @Header("x-access-token") String token
    );
}
