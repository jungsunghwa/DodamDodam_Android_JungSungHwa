package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import io.reactivex.Single;

import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUploadService {

    @Multipart
    @POST("/upload/file")
    Call<Response> uploadFile(
            @Header("x-access-token") String token,
            @Part MultipartBody.Part file,
            @Part("name") String name
    );

    @Multipart
    @POST("/upload/image")
    Call<retrofit2.Response<Response>> uploadImg(
            @Header("x-access-token") String token,
            @Part MultipartBody.Part file,
            @Part("name") RequestBody name
    );
}
