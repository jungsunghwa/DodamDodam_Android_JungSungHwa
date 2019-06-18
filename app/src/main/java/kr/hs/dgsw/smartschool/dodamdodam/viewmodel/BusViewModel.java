package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.BusClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;

public class BusViewModel extends BaseViewModel<Bus> {
    private BusClient busClient;
    private DatabaseHelper databaseHelper;


    public BusViewModel(Context context) {
        super(context);
        busClient = new BusClient();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    @SuppressLint("CheckResult")
    public void postBusApply(BusRequest request) {
        loading.setValue(true);
        addDisposable(busClient.postBusApply(databaseHelper.getToken(), request), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void deleteBusApply(int idx) {
        loading.setValue(true);
        addDisposable(busClient.deleteBusApply(databaseHelper.getToken(), idx), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void getMyBusApply() {
        loading.setValue(true);
        addDisposable(busClient.getMyBusApply(databaseHelper.getToken()), dataObserver);
    }

    @SuppressLint("CheckResult")
    public void putModifyBusApply(int idx, BusRequest request) {
        loading.setValue(true);
        addDisposable(busClient.putModifyBusApply(
                databaseHelper.getToken(), idx, request), baseObserver);
    }

}
