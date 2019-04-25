package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.post;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Locations;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LocationService {
    @POST("location")
    public Call<Response> postLocation(
            @Header("x-access-token") String token,
            @Body LocationRequest request
    );

    @GET("location/search/my")
    public Call<Response<LocationRequest>> getMyLocation(
            @Header("x-access-token") String token
    );
}
