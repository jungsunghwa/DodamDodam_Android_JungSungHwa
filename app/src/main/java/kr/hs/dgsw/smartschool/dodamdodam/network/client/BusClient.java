package kr.hs.dgsw.smartschool.dodamdodam.network.client;


import java.util.List;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.BusResponse;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.PostBusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.BusService;

public class BusClient extends NetworkClient {

    private BusService bus;

    public BusClient() {
        bus = Utils.RETROFIT.create(BusService.class);
    }

    public Single<List<BusResponse>> getCurrentBus(Token token) {
        return bus.getCurrentBus(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<List<Bus>> getMyBusApply(Token token) {
        return bus.getMyBusApply(token.getToken()).map(getResponseObjectsFunction());
    }

    public Single<String> postBusApply(Token token, PostBusRequest request) {
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
//    public Single<Bus> getBusType(Token token, int idx) {
//        return bus.getBusType(token.getToken(), idx).map(getResponseObjectsFunction());
//    }
//
//    public Single<String> putModifyBusApply(Token token, int idx, BusRequest request) {
//        return bus.putModifyBusApply(token.getToken(), idx, request).map(getResponseMessageFunction());
//    }
//
//    public Single<Bues> getCurrentBusType(Token token) {
//        return bus.getCurrentBusType(token.getToken()).map(getResponseObjectsFunction());
//    }


}