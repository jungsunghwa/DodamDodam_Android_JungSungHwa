package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import kr.hs.dgsw.smartschool.dodamdodam.Model.ListType;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Type;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Types;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.BusClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.PostBusRequest;

public class BusViewModel extends BaseViewModel<Type> {

    private final MutableLiveData<Types> responseTypes = new MutableLiveData<>();
    private final MutableLiveData<List<Type>> responseTypeList = new MutableLiveData<>();
    private final MutableLiveData<List<Type>> responseAllTypeList = new MutableLiveData<>();
    private final MutableLiveData<String> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private BusClient busClient;
    private TokenManager manager;

    public BusViewModel(Application application) {
        super(application);
        busClient = new BusClient();
        manager = TokenManager.getInstance(application);
    }

    public MutableLiveData<List<Type>> getResponseAllTypeList() {
        return responseAllTypeList;
    }

    public MutableLiveData<List<Type>> getResponseTypeList() {
        return responseTypeList;
    }

    public void getCurrentBus() {
        loading.setValue(true);

        DisposableSingleObserver<List<Type>> observer = new DisposableSingleObserver<List<Type>>() {
            @Override
            public void onSuccess(List<Type> types) {
                responseAllTypeList.setValue(types);

                loading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                errorMessage.setValue(e.getMessage());

                loading.setValue(false);
            }
        };

        addDisposable(busClient.getCurrentBus(manager.getToken()), observer);
    }

    public void getMyBusApply() {
        loading.setValue(true);

        DisposableSingleObserver<List<Type>> observer = new DisposableSingleObserver<List<Type>>() {
            @Override
            public void onSuccess(List<Type> types) {
                responseTypeList.setValue(types);

                loading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                errorMessage.setValue(e.getMessage());

                loading.setValue(false);
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
//        DisposableSingleObserver<Type> observer = new DisposableSingleObserver<Type>() {
//            @Override
//            public void onSuccess(Type type) {
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
//        DisposableSingleObserver<Types> observer = new DisposableSingleObserver<Types>() {
//            @Override
//            public void onSuccess(Types types) {
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
