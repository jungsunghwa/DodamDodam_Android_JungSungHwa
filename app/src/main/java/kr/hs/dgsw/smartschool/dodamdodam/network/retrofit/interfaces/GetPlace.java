package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.PlaceList;
import kr.hs.dgsw.smartschool.dodamdodam.Model.TimeTables;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface GetPlace {
    @GET("place")
    public Call<Response<PlaceList>> getAllPlace(
            @Header("x-access-token") String token
    );
}
