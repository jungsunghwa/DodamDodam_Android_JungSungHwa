package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFounds;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LostFoundService {
    @GET("/lostfound")
    Single<retrofit2.Response<Response<LostFounds>>> getLostFound(
            @Header("x-access-token") String token,
            @Query("page") Integer page,
            @Query("type") Integer type
    );

    @POST("/lostfound")
    Single<Response> postCreateLostFound(
            @Header("x-access-token") String token,
            @Body LostFoundRequest request
    );

    @PUT("/lostfound")
    Single<Response> putLostFound(
            @Header("x-access-token") String token,
            @Body LostFoundRequest request
    );

    @DELETE("/lostfound")
    Single<Response> deleteLostFound(
            @Header("x-access-token") String token,
            @Body Integer idx
    );

    @GET("/lostfound/find")
    Single<retrofit2.Response<Response<LostFounds>>> getLostFoundSearch(
            @Header("x-access-token") String token,
            @Query("query") String search
    );

    @Multipart
    @POST("/upload/image")
    Single<Response> multiPartLostFoundImage(
            @Header("x-access-token") String token,
            @Part("name") String name,
            @Part("image") MultipartBody.Part body
    );
}
