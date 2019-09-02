package kr.hs.dgsw.smartschool.dodamdodam.network.client;


import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Types;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.BusService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class BusClient extends NetworkClient {

    private BusService bus;

    public BusClient() {
        bus = Utils.RETROFIT.create(BusService.class);
    }

    public Single<Types> getCurrentBus(Token token) {
        return bus.getCurrentBus(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<Types> getMyBusApply(Token token) {
        return bus.getMyBusApply(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<String> postBusApply(Token token, BusRequest request) {
        return bus.postBusApply(token.getToken(), request).map(getResponseMessageFunction());
    }

    public Single<String> putBusApply(Token token, BusRequest request) {
        return bus.putBusApply(token.getToken(), request).map(getResponseMessageFunction());
    }

    public Single<String> deleteBusApply(Token token, int busIdx) {
        return bus.deleteBusApply(token.getToken(), busIdx).map(getResponseMessageFunction());
    }

//    public Single<String> postBusApply(Token token, BusRequest request) {
//        return bus.postBusApply(token.getToken(), request).map(getResponseMessageFunction());
//    }
//
//    public Single<String> deleteBusApply(Token token, int idx) {
//        return bus.deleteBusApply(token.getToken(), idx).map(getResponseMessageFunction());
//    }
//
//    public Single<Bus> getMyBusApply(Token token) {
//        return bus.getMyBusApply(token.getToken()).map(getResponseObjectsFunction());
//    }
//
//    public Single<Type> getBusType(Token token, int idx) {
//        return bus.getBusType(token.getToken(), idx).map(getResponseObjectsFunction());
//    }
//
//    public Single<String> putModifyBusApply(Token token, int idx, BusRequest request) {
//        return bus.putModifyBusApply(token.getToken(), idx, request).map(getResponseMessageFunction());
//    }
//
//    public Single<Types> getCurrentBusType(Token token) {
//        return bus.getCurrentBusType(token.getToken()).map(getResponseObjectsFunction());
//    }


}