package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;


import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.BusResponse;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.PostBusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BusService {
    @GET("/bus")
    Single<retrofit2.Response<Response<List<BusResponse>>>> getCurrentBus(
            @Header("x-access-token") String token
    );

    @GET("/bus/self")
    Single<retrofit2.Response<Response<List<Bus>>>> getMyBusApply(
            @Header("x-access-token") String token
    );

    @POST("/bus/self")
    Single<retrofit2.Response<Response>> postBusApply(
            @Header("x-access-token") String token,
            @Body PostBusRequest request
            );

    @PUT("/bus/self")
    Single<retrofit2.Response<Response>> putBusApply(
            @Header("x-access-token") String token,
            @Body BusRequest request
    );

    @DELETE("/bus/self/{bus_idx}")
    Single<retrofit2.Response<Response>> deleteBusApply(
            @Header("x-access-token") String token,
            @Path("bus_idx") int busIdx
    );
}
