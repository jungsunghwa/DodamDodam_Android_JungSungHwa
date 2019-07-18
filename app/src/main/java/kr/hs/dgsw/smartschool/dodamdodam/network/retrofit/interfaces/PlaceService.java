package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.PlaceList;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface PlaceService {
    @GET("place")
    Single<retrofit2.Response<Response<PlaceList>>> getAllPlace(
            @Header("x-access-token") String token
    );
}
