package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.song.Video;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.SongCheckRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.SongRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.SongService;

public class SongClient extends NetworkClient {

    private SongService song;

    public SongClient() {
        song = Utils.RETROFIT.create(SongService.class);
    }

    public Single<List<Video>> getSongs(Token token) {
        return song.getSongs(token.getToken()).map(getResponseObjectsFunction());

//        return Single.create(observer -> song.getSongs(token.getToken()).enqueue(new Callback<Response<List<Video>>>() {
//            @Override
//            @EverythingIsNonNull
//            public void onResponse(Call<Response<List<Video>>> call, retrofit2.Response<Response<List<Video>>> response) {
//                if (response.isSuccessful())
//                    observer.onSuccess(response.body().getData());
//                else
//                    try {
//                        JSONObject errorBody = new JSONObject(Objects
//                                .requireNonNull(
//                                        response.errorBody()).string());
//                        observer.onError(new Throwable(errorBody.getString("message")));
//                    } catch (JSONException | IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//
//            @Override
//            @EverythingIsNonNull
//            public void onFailure(Call<Response<List<Video>>> call, Throwable t) {
//                observer.onError(t);
//            }
//        }));
    }

    public Single<List<Video>> getMySongs(Token token) {
        return song.getMySongs(token.getToken()).map(getResponseObjectsFunction());
//        return Single.create(observer -> song.getMySongs(token.getToken()).enqueue(new Callback<Response<List<Video>>>() {
//            @Override
//            @EverythingIsNonNull
//            public void onResponse(Call<Response<List<Video>>> call, retrofit2.Response<Response<List<Video>>> response) {
//                if (response.isSuccessful())
//                    observer.onSuccess(response.body().getData());
//                else
//                    try {
//                        JSONObject errorBody = new JSONObject(Objects
//                                .requireNonNull(
//                                        response.errorBody()).string());
//                        observer.onError(new Throwable(errorBody.getString("message")));
//                    } catch (JSONException | IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//
//            @Override
//            @EverythingIsNonNull
//            public void onFailure(Call<Response<List<Video>>> call, Throwable t) {
//                observer.onError(t);
//            }
//        }));
    }

    public Single<List<Video>> getAllowSongs(Token token) {
        return song.getAllowSongs(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<List<Video>> getMyAllowSongs(Token token) {
        return song.getMyAllowSongs(token.getToken()).map(getResponseObjectsFunction());
//        return Single.create(observer -> song.getMyAllowSongs(token.getToken()).enqueue(new Callback<Response<List<Video>>>() {
//            @Override
//            @EverythingIsNonNull
//            public void onResponse(Call<Response<List<Video>>> call, retrofit2.Response<Response<List<Video>>> response) {
//                if (response.isSuccessful())
//                    observer.onSuccess(response.body().getData());
//                else
//                    try {
//                        JSONObject errorBody = new JSONObject(Objects
//                                .requireNonNull(
//                                        response.errorBody()).string());
//                        observer.onError(new Throwable(errorBody.getString("message")));
//                    } catch (JSONException | IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//
//            @Override
//            @EverythingIsNonNull
//            public void onFailure(Call<Response<List<Video>>> call, Throwable t) {
//                observer.onError(t);
//            }
//        }));
    }

    public Single<String> postSong(Token token, SongRequest request) {
        return song.postSong(token.getToken(), request).map(Response::getMessage);
//        return Single.create(observer -> song.postSong(token.getToken(), request).enqueue(new Callback<Response>() {
//            @Override
//            @EverythingIsNonNull
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                if (response.isSuccessful())
//                    observer.onSuccess(response.body().getMessage());
//                else
//                    try {
//                        JSONObject errorBody = new JSONObject(Objects
//                                .requireNonNull(
//                                        response.errorBody()).string());
//                        observer.onError(new Throwable(errorBody.getString("message")));
//                    } catch (JSONException | IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//
//            @Override
//            @EverythingIsNonNull
//            public void onFailure(Call<Response> call, Throwable t) {
//                observer.onError(t);
//            }
//        }));
    }

    public Single<String> postAllowSong(Token token, SongCheckRequest request) {
        return song.postAllowSong(token.getToken(), request).map(Response::getMessage);
//        return Single.create(observer -> song.postAllowSong(token.getToken(), request).enqueue(new Callback<Response>() {
//            @Override
//            @EverythingIsNonNull
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                if (response.isSuccessful())
//                    observer.onSuccess(response.body().getMessage());
//                else
//                    try {
//                        JSONObject errorBody = new JSONObject(Objects
//                                .requireNonNull(
//                                        response.errorBody()).string());
//                        observer.onError(new Throwable(errorBody.getString("message")));
//                    } catch (JSONException | IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//
//            @Override
//            @EverythingIsNonNull
//            public void onFailure(Call<Response> call, Throwable t) {
//                observer.onError(t);
//            }
//        }));
    }

    public Single<String> postDenySong(Token token, SongCheckRequest request) {
        return song.postDenySong(token.getToken(), request).map(Response::getMessage);
//        return Single.create(observer -> song.postDenySong(token.getToken(), request).enqueue(new Callback<Response>() {
//            @Override
//            @EverythingIsNonNull
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                if (response.isSuccessful())
//                    observer.onSuccess(response.body().getMessage());
//                else
//                    try {
//                        JSONObject errorBody = new JSONObject(Objects
//                                .requireNonNull(
//                                        response.errorBody()).string());
//                        observer.onError(new Throwable(errorBody.getString("message")));
//                    } catch (JSONException | IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//
//            @Override
//            @EverythingIsNonNull
//            public void onFailure(Call<Response> call, Throwable t) {
//                observer.onError(t);
//            }
//        }));
    }
}
