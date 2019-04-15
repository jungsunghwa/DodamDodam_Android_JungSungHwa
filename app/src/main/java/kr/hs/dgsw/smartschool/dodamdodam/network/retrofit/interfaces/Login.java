package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LoginRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface Login {
    @POST("auth/login")
    public Call<Response<Token>> login(
            @Body LoginRequest request
    );
}
