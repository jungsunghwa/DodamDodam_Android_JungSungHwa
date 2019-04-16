package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.post;

import org.json.JSONObject;

import java.util.Map;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LoginRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.PostLocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LocationService {
    @POST("location")
    public Call<Response> postLocation(
            @Header("x-access-token") String token,
            @Body PostLocationRequest request
    );

    @POST("location/{student_idx}")
    public Call<Response> getStudentLoaction(
            @Header("x-access-token") String token,
            @Path(value = "student_idx", encoded = true) int studentIdx
    );
}
