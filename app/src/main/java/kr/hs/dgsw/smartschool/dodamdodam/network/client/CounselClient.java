package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsels;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.CounselService;
import retrofit2.Call;
import retrofit2.Callback;

public class CounselClient extends NetworkClient {
    private CounselService counsel;

    public CounselClient() { counsel = Utils.RETROFIT.create(CounselService.class); }

    public Single<Counsel> getAllCounsel(Token token) {
        
        return counsel.getAllCounsel(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<String> postCounsel(Token token, CounselRequest request) {
        return counsel.postCounsel(token.getToken(), request).map(Response::getMessage);
    }

    public Single<Counsel> getCertainCounsel(Token token, int counselIdx) {
        return counsel.getCertainCounsel(token.getToken(), counselIdx).map(getResponseObjectsFunction());
    }

    public Single<String> deleteCounsel(Token token, int counselIdx) {
        return counsel.deleteCounsel(token.getToken(), counselIdx).map(Response::getMessage);
    }

    public Single<String> postCounselAllow(Token token, CounselRequest request) {
        return counsel.postCounselAllow(token.getToken(), request).map(Response::getMessage);
    }

    public Single<String> postCounselCancel(Token token, CounselRequest request) {
        return counsel.postCounselCancel(token.getToken(), request).map(Response::getMessage);
    }
}
