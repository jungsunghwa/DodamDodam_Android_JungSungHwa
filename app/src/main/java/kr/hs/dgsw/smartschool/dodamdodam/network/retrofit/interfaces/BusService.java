package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;


import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Type;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Types;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BusService {
    @POST("/bus")
    Single<Response> postBusApply(
            @Header("x-access-token") String token,
            @Body BusRequest request
    );

    @DELETE("/bus/{idx}")
    Single<Response> deleteBusApply(
            @Header("x-access-token") String token,
            @Path("idx") int idx
    );

    @GET("/bus/my")
    Single<retrofit2.Response<Response<Bus>>> getMyBusApply(
            @Header("x-access-token") String token
    );

    @PUT("/bus/{idx}")
    Single<Response> putModifyBusApply(
            @Header("x-access-token") String token,
            @Path("idx") int idx,
            @Body BusRequest request
    );

    @GET("/bus/type")
    Single<retrofit2.Response<Response<Types>>> getBusType(
            @Header("x-access-token") String token
    );
}
