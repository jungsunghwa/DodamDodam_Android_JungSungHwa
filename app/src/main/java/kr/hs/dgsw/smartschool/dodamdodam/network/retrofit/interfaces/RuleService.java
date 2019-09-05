package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.rule.Rule;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.RuleRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RuleService {
    @GET("rule")
    Single<retrofit2.Response<Response<List<Rule>>>> getRule (
            @Header("x-access-token") String token
    );

    @POST("rule")
    Single<Response> postRuleAdd (
            @Header("x-access-token") String token,
            @Body RuleRequest request
    );

    @GET("rule/{idx}")
    Single<retrofit2.Response<Response<Rule>>> getlookRule (
            @Header("x-access-token") String token,
            @Path("idx") int idx
    );

    @PUT("rule/{idx}")
    Single<Response> putModifyRule (
            @Header("x-access-token") String token,
            @Path("idx") int idx,
            @Body RuleRequest request
    );

    @DELETE("rule/{idx}")
    Single<Response> deleteRule (
            @Header("x-access-token") String token,
            @Path("idx") int idx
    );


}
