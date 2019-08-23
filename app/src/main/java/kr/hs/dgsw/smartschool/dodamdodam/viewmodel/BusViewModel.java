package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.observers.DisposableSingleObserver;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Types;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.BusClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;

public class BusViewModel extends BaseViewModel<Bus> {

    private final MutableLiveData<Types> responseTypes = new MutableLiveData<>();
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

    public MutableLiveData<Types> getResponseTypes() {
        return responseTypes;
    }

    public void postBusApply(BusRequest request) {
        loading.setValue(true);

        addDisposable(busClient.postBusApply(
                manager.getToken(), request), getBaseObserver());
    }

    public void deleteBusApply(int idx) {
        loading.setValue(true);
        addDisposable(busClient.deleteBusApply(manager.getToken(), idx), getBaseObserver());
    }

    public void getMyBusApply() {
        loading.setValue(true);

        addDisposable(busClient.getMyBusApply(manager.getToken()), getDataObserver());
    }

    public void getBusType(int idx) {
        loading.setValue(true);

        addDisposable(busClient.getBusType(manager.getToken(),idx), getDataObserver());
    }

    public void putModifyBusApply(int idx, BusRequest request) {
        loading.setValue(true);

        addDisposable(busClient.putModifyBusApply(
                manager.getToken(), idx, request), getBaseObserver());
    }

    public void getCurrentBusTypes() {
        loading.setValue(true);

        DisposableSingleObserver<Types> observer = new DisposableSingleObserver<Types>() {
            @Override
            public void onSuccess(Types types) {
                responseTypes.setValue(types);
                loading.setValue(false);
                this.dispose();
            }

            @Override
            public void onError(Throwable e) {
                loginErrorMessage.setValue(e.getMessage());
                loading.setValue(false);
                this.dispose();
            }
        };

        addDisposable(busClient.getCurrentBusType(
                manager.getToken()), observer);
    }

}
