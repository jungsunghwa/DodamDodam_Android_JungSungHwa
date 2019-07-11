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
    }

    public Single<List<Video>> getMySongs(Token token) {
        return song.getMySongs(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<List<Video>> getAllowSongs(Token token) {
        return song.getAllowSongs(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<List<Video>> getMyAllowSongs(Token token) {
        return song.getMyAllowSongs(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<String> postSong(Token token, SongRequest request) {
        return song.postSong(token.getToken(), request).map(Response::getMessage);
    }

    public Single<String> postAllowSong(Token token, SongCheckRequest request) {
        return song.postAllowSong(token.getToken(), request).map(Response::getMessage);
    }

    public Single<String> postDenySong(Token token, SongCheckRequest request) {
        return song.postDenySong(token.getToken(), request).map(Response::getMessage);
    }
}
