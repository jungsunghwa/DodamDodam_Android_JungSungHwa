package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import io.reactivex.Single;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadService {

    @Multipart
    @POST("/upload/image")
    Call<Response> uploadFile(
            @Header("x-access-token") String token,
            @Part("name") String name,
            @Part("image") MultipartBody.Part file
    );
}
