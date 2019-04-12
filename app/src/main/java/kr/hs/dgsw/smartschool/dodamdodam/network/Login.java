package kr.hs.dgsw.smartschool.dodamdodam.network;

import androidx.annotation.Nullable;
import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Login.LoginData;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Login.LoginRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface Login {
    @POST("auth/login")
    public Call<Response<LoginData>> login(
            @Body LoginRequest request
    );
}
