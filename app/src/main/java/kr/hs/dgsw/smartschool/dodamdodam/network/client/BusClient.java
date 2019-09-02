package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Type;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Types;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.BusService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class BusClient extends NetworkClient {

    private BusService bus;

    public BusClient(Context context) {
        super(context);
        bus = Utils.RETROFIT.create(BusService.class);
    }

    public Single<String> postBusApply(Token token, BusRequest request) {
        return bus.postBusApply(token.getToken(), request).map(getResponseMessageFunction());
    }

    public Single<String> deleteBusApply(Token token, int idx) {
        return bus.deleteBusApply(token.getToken(), idx).map(getResponseMessageFunction());
    }

    public Single<Bus> getMyBusApply(Token token) {
        return bus.getMyBusApply(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Type> getBusType(Token token, int idx) {
        return bus.getBusType(token.getToken(), idx).map(getResponseObjectsFunction());
    }

    public Single<String> putModifyBusApply(Token token, int idx, BusRequest request) {
        return bus.putModifyBusApply(token.getToken(), idx, request).map(getResponseMessageFunction());
    }

    public Single<Types> getCurrentBusType(Token token) {
        return bus.getCurrentBusType(token.getToken()).map(getResponseObjectsFunction());
    }


}