package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bues;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.BusResponse;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.BusClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.PostBusRequest;

public class BusViewModel extends BaseViewModel<BusResponse> {

    private final MutableLiveData<List<Bus>> responseMyBusList = new MutableLiveData<>();
    private final MutableLiveData<List<BusResponse>> responseAllBusList = new MutableLiveData<>();
    private BusClient busClient;
    private TokenManager manager;

    public BusViewModel(Application application) {
        super(application);
        busClient = new BusClient();
        manager = TokenManager.getInstance(application);
    }

    public MutableLiveData<List<BusResponse>> getResponseAllBusList() {
        return responseAllBusList;
    }

    public MutableLiveData<List<Bus>> getResponseMyBusList() {
        return responseMyBusList;
    }

    public void getCurrentBus() {
        loading.setValue(true);

        DisposableSingleObserver<List<BusResponse>> observer = new DisposableSingleObserver<List<BusResponse>>() {
            @Override
            public void onSuccess(List<BusResponse> busResponseList) {
                responseAllBusList.setValue(busResponseList);

                loading.setValue(false);
                dispose();
            }

            @Override
            public void onError(Throwable e) {
                if (errorEvent(e))
                    dispose();
            }
        };

        addDisposable(busClient.getCurrentBus(manager.getToken()), observer);
    }

    public void getMyBusApply() {
        loading.setValue(true);

        DisposableSingleObserver<List<Bus>> observer = new DisposableSingleObserver<List<Bus>>() {
            @Override
            public void onSuccess(List<Bus> busResponseList) {
                responseMyBusList.setValue(busResponseList);

                loading.setValue(false);
                dispose();
            }

            @Override
            public void onError(Throwable e) {
                if (errorEvent(e))
                    dispose();
            }
        };

        addDisposable(busClient.getMyBusApply(manager.getToken()), observer);
    }

    public void postBusApply(PostBusRequest request) {
        loading.setValue(true);

        addDisposable(busClient.postBusApply(manager.getToken(), request), getBaseObserver());
    }

    public void putBusApply(BusRequest request) {
        loading.setValue(true);

        addDisposable(busClient.putBusApply(manager.getToken(), request), getBaseObserver());
    }

    public void deleteBusApply(int busIdx) {
        loading.setValue(true);

        addDisposable(busClient.deleteBusApply(manager.getToken(), busIdx), getBaseObserver());
    }

//    public void postBusApply(BusRequest request) {
//        loading.setValue(true);
//
//        addDisposable(busClient.postBusApply(
//                manager.getToken(), request), getBaseObserver());
//    }
//
//    public void deleteBusApply(int idx) {
//        loading.setValue(true);
//        addDisposable(busClient.deleteBusApply(manager.getToken(), idx), getBaseObserver());
//    }
//
//    public void getMyBusApply() {
//        loading.setValue(true);
//
//        addDisposable(busClient.getMyBusApply(manager.getToken()), getDataObserver());
//    }
//
//    public void getBusType(int idx) {
//        loading.setValue(true);
//
//        DisposableSingleObserver<Bus> observer = new DisposableSingleObserver<Bus>() {
//            @Override
//            public void onSuccess(Bus type) {
//                responseType.setValue(type);
//                loading.setValue(false);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                errorMessage.setValue(e.getMessage());
//                loading.setValue(false);
//            }
//        };
//
//        addDisposable(busClient.getBusType(manager.getToken(),idx), observer);
//    }
//
//    public void putModifyBusApply(int idx, BusRequest request) {
//        loading.setValue(true);
//
//        addDisposable(busClient.putModifyBusApply(
//                manager.getToken(), idx, request), getBaseObserver());
//    }
//
//    public void getCurrentBusTypes() {
//        loading.setValue(true);
//
//        DisposableSingleObserver<Bues> observer = new DisposableSingleObserver<Bues>() {
//            @Override
//            public void onSuccess(Bues types) {
//                responseTypes.setValue(types);
//                loading.setValue(false);
//                this.dispose();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                loginErrorMessage.setValue(e.getMessage());
//                loading.setValue(false);
//                this.dispose();
//            }
//        };
//
//        addDisposable(busClient.getCurrentBusType(
//                manager.getToken()), observer);
//    }

}
