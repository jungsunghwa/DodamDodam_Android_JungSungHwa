package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFounds;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LostFoundService {
    @GET("/lostfound")
    Call<Response<LostFounds>> getLostFound(
            @Header("x-access-token") String token,
            @Query("page") Integer page,
            @Query("type") Integer type
    );

    @POST("/lostfound")
    Call<Response> postCreateLostFound(
            @Header("x-access-token") String token,
            @Body LostFoundRequest request
    );

    @PUT("/lostfound")
    Call<Response> putLostFound(
            @Header("x-access-token") String token,
            @Body LostFoundRequest request
    );

    @DELETE("/lostfound")
    Call<Response> deleteLostFound(
            @Header("x-access-token") String token,
            @Body Integer idx
    );

    @GET("/lostfound/find")
    Call<Response<LostFounds>> getLostFoundSearch(
            @Header("x-access-token") String token,
            @Query("search") String search
    );
}
