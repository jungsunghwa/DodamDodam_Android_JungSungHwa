package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;

import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.CounselService;

public class CounselClient extends NetworkClient {
    private CounselService counsel;

    public CounselClient() {
        counsel = Utils.RETROFIT.create(CounselService.class);
    }

    public Single<List<Counsel>> getAllCounsel(Token token) {
        return counsel.getAllCounsel(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<String> postCounsel(Token token, CounselRequest request) {
        return counsel.postCounsel(token.getToken(), request).map(getResponseMessageFunction());
    }

    public Single<Counsel> getCertainCounsel(Token token, int counselIdx) {
        return counsel.getCertainCounsel(token.getToken(), counselIdx).map(getResponseObjectsFunction());
    }

    public Single<String> deleteCounsel(Token token, int counselIdx) {
        return counsel.deleteCounsel(token.getToken(), counselIdx).map(getResponseMessageFunction());
    }

    public Single<String> postCounselAllow(Token token, CounselRequest request) {
        return counsel.postCounselAllow(token.getToken(), request).map(getResponseMessageFunction());
    }

    public Single<String> postCounselCancel(Token token, CounselRequest request) {
        return counsel.postCounselCancel(token.getToken(), request).map(getResponseMessageFunction());
    }
}
