package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leaves;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Offbase;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Passes;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.OffbaseRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.OffbaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class OffbaseClient extends NetworkClient {

    private OffbaseService offbase;

    public OffbaseClient(Context context) {
        super(context);
        offbase = Utils.RETROFIT.create(OffbaseService.class);
    }

    public Single<Offbase> getOffbase(Token token) {
        return offbase.getOffbase(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Offbase> getOffbaseAllow(Token token) {
        return offbase.getOffbaseAllow(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Offbase> getOffbaseCancel(Token token) {
        return offbase.getOffbaseCancel(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Leaves> getLeaves(Token token) {
        return offbase.getLeaves(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Leaves> getLeaveAllows(Token token) {
        return offbase.getLeaveAllows(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Leaves> getLeaveCancels(Token token) {
        return offbase.getLeaveCancels(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Leave> getLeaveById(Token token, int leaveId) {
        return offbase.getLeaveById(token.getToken(), leaveId).map(getResponseObjectsFunction());
    }

    public Single<Passes> getPasses(Token token) {
        return offbase.getPasses(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Passes> getPassAllows(Token token) {
        return offbase.getPassAllows(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Passes> getPassCancels(Token token) {
        return offbase.getPassCancels(token.getToken()).map(getResponseObjectsFunction());
        
    }

    public Single<Pass> getPassById(Token token, int passId) {
        return offbase.getPassById(token.getToken(), passId).map(getResponseObjectsFunction());
    }

    public Single<String> postLeave(Token token, OffbaseRequest request) {
        return offbase.postLeave(token.getToken(), request).map(Response::getMessage);
    }

    public Single<String> putLeave(Token token, int leaveId, OffbaseRequest request) {
        return offbase.putLeave(token.getToken(), leaveId, request).map(Response::getMessage);
    }

    public Single<String> postPass(Token token, OffbaseRequest request) {
        return offbase.postPass(token.getToken(), request).map(Response::getMessage);
    }

    public Single<String> putPass(Token token, int passId, OffbaseRequest request) {
        return offbase.putPass(token.getToken(), passId, request).map(Response::getMessage);
    }

    public Single<String> deleteLeave(Token token, int leaveId) {
        return offbase.deleteLeave(token.getToken(), leaveId).map(Response::getMessage);
    }

    public Single<String> deletePass(Token token, int passId) {
        return offbase.deletePass(token.getToken(), passId).map(Response::getMessage);
    }
}
