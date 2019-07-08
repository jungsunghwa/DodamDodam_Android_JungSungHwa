package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsels;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CounselService {
    @GET("/counsel")
    Call<Response<Counsel>> getAllCounsel(
            @Header("x-access-token") String token
    );

    @POST("/counsel")
    Call<Response<Counsels>> postCounsel(
            @Header("x-access-token") String token,
            @Body CounselRequest request
    );

    @GET("/counsel/{counsel_idx}")
    Call<Response<Counsel>> getCertainCounsel(
            @Header("x-access-token") String token,
            @Path("counsel_idx") int counselIdx
    );

    @DELETE("/counsel/{counsel_idx}")
    Call<Response> deleteCounsel(
            @Header("x-access-token") String token,
            @Path("counsel_idx") int counselIdx
    );

    @POST("/counsel/allow")
    Call<Response<Counsels>> postCounselAllow(
            @Header("x-access-token") String token,
            @Body CounselRequest request
    );

    @POST("/counsel/cancel")
    Call<Response<Counsels>> postCounselCancel(
            @Header("x-access-token") String token,
            @Body CounselRequest request
    );

    @GET("/members/teachers")
    Call<Response<Teacher>> getTeacher(
            @Header("x-access-token") String token
    );
}
