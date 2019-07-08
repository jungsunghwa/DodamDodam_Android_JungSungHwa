package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces;

import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.song.Video;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.SongCheckRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.SongRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SongService {

    @GET("wake-song")
    Single<retrofit2.Response<Response<List<Video>>>> getSongs(
            @Header("x-access-token") String token
    );

    @POST("wake-song")
    Single<Response> postSong(
            @Header("x-access-token") String token,
            @Body SongRequest request
    );

    @GET("wake-song/list")
    Single<retrofit2.Response<Response<List<Video>>>> getMySongs(
            @Header("x-access-token") String token
    );

    @GET("wake-song/allow")
    Single<retrofit2.Response<Response<List<Video>>>> getMyAllowSongs(
            @Header("x-access-token") String token
    );

    @POST("wake-song/allow")
    Single<Response> postAllowSong(
            @Header("x-access-token") String token,
            @Body SongCheckRequest request
    );

    @POST("wake-song/cancel")
    Single<Response> postDenySong(
            @Header("x-access-token") String token,
            @Body SongCheckRequest request
    );
}
