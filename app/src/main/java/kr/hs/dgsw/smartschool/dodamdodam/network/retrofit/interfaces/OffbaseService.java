package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leaves;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Offbase;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Passes;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.OffbaseRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OffbaseService {

    @GET("offbase")
    Single<retrofit2.Response<Response<Offbase>>> getOffbase(
            @Header("x-access-token") String token
    );

    @GET("offbase/allow")
    Single<retrofit2.Response<Response<Offbase>>> getOffbaseAllow(
            @Header("x-access-token") String token
    );

    @GET("offbase/cancel")
    Single<retrofit2.Response<Response<Offbase>>> getOffbaseCancel(
            @Header("x-access-token") String token
    );

    @GET("offbase/leave")
    Single<retrofit2.Response<Response<Leaves>>> getLeaves(
            @Header("x-access-token") String token
    );

    @GET("offbase/leave/allow")
    Single<retrofit2.Response<Response<Leaves>>> getLeaveAllows(
            @Header("x-access-token") String token
    );

    @GET("offbase/leave/cancel")
    Single<retrofit2.Response<Response<Leaves>>> getLeaveCancels(
            @Header("x-access-token") String token
    );

    @GET("offbase/leave/{leave_idx}")
    Single<retrofit2.Response<Response<Leave>>> getLeaveById(
            @Header("x-access-token") String token, @Path("leave_idx") int leaveIdx
    );

    @GET("offbase/pass")
    Single<retrofit2.Response<Response<Passes>>> getPasses(
            @Header("x-access-token") String token
    );

    @GET("offbase/pass/allow")
    Single<retrofit2.Response<Response<Passes>>> getPassAllows(
            @Header("x-access-token") String token
    );

    @GET("offbase/pass/cancel")
    Single<retrofit2.Response<Response<Passes>>> getPassCancels(
            @Header("x-access-token") String token
    );

    @GET("offbase/pass/{pass_idx}")
    Single<retrofit2.Response<Response<Pass>>> getPassById(
            @Header("x-access-token") String token, @Path("pass_idx") int passIdx
    );

    @POST("offbase/leave")
    Single<retrofit2.Response<Response>> postLeave(
            @Header("x-access-token") String token, @Body OffbaseRequest request
    );

    @PUT("offbase/leave/{leave_idx}")
    Single<retrofit2.Response<Response>> putLeave(
            @Header("x-access-token") String token, @Path("leave_idx") int leaveIdx, @Body OffbaseRequest request
    );

    @DELETE("offbase/leave/{leave_idx}")
    Single<retrofit2.Response<Response>> deleteLeave(
            @Header("x-access-token") String token, @Path("leave_idx") int leaveIdx
    );

    @POST("offbase/pass")
    Single<retrofit2.Response<Response>> postPass(
            @Header("x-access-token") String token, @Body OffbaseRequest request
    );

    @PUT("offbase/pass/{pass_idx}")
    Single<retrofit2.Response<Response>> putPass(
            @Header("x-access-token") String token, @Path("pass_idx") int leaveIdx, @Body OffbaseRequest request
    );

    @DELETE("offbase/pass/{pass_idx}")
    Single<retrofit2.Response<Response>> deletePass(
            @Header("x-access-token") String token, @Path("pass_idx") int leaveIdx
    );
}
