package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.post;

import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Locations;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LocationService {

    @POST("location")
    Single<Response> postLocation(
            @Header("x-access-token") String token,
            @Body LocationRequest<LocationInfo> locations
    );

    @GET("location/search/my")
    Single<Response<LocationRequest>> getMyLocation(
            @Header("x-access-token") String token,
            @Query("date") String date
    );

    @GET("location/search/students")
    Single<Response<List<Locations>>> getLocation(
            @Header("x-access-token") String token,
            @Query("date") String date
    );

    @PUT("location")
    Call<Response> putAllLocation(
            @Header("x-access-token") String token,
            @Body LocationRequest<LocationInfo> request
    );

    @PUT("location/{idx}")
    Single<Response> putLocation(
            @Header("x-access-token") String token,
            @Path("idx") int idx,
            @Body Location placeIdx
    );

    @PUT("location/check/{idx}")
    public Call<Response> checkLocation(
            @Header("x-access-token") String token,
            @Path("idx") int idx
    );


}
