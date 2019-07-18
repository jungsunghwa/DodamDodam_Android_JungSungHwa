package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import io.reactivex.Single;
import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MemberService {

    @GET("members/search")
    Single<retrofit2.Response<Response<Member>>> findMember(
            @Header("x-access-token") String token,
            @Query("id") String id
            );
}
