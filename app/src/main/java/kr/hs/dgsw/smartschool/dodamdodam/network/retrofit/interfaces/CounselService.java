package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CounselService {
    @GET("/counsel")
    Single<retrofit2.Response<Response<List<Counsel>>>> getAllCounsel(
            @Header("x-access-token") String token
    );

    @POST("/counsel")
    Single<Response<Counsel>> postCounsel(
            @Header("x-access-token") String token,
            @Body CounselRequest request
    );

    @GET("/counsel/{counsel_idx}")
    Single<retrofit2.Response<Response<Counsel>>> getCertainCounsel(
            @Header("x-access-token") String token,
            @Path("counsel_idx") int counselIdx
    );

    @DELETE("/counsel/{counsel_idx}")
    Single<Response> deleteCounsel(
            @Header("x-access-token") String token,
            @Path("counsel_idx") int counselIdx
    );

    @POST("/counsel/allow")
    Single<Response> postCounselAllow(
            @Header("x-access-token") String token,
            @Body CounselRequest request
    );

    @POST("/counsel/cancel")
    Single<Response> postCounselCancel(
            @Header("x-access-token") String token,
            @Body CounselRequest request
    );

    @GET("/members/teachers")
    Single<retrofit2.Response<Response<Teacher>>> getTeacher(
            @Header("x-access-token") String token
    );
}
