package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LostFoundClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;

public class LostFoundViewModel extends BaseViewModel<List<LostFound>>{
    private LostFoundClient lostFoundClient;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<List<LostFound>> response = new MutableLiveData<>();

    public LostFoundViewModel(Context context) {
        super(context);
        lostFoundClient = new LostFoundClient();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<List<LostFound>> getResponse() {
        return response;
    }

    @SuppressLint("CheckResult")
    public void getLostFound(Integer page, Integer type) {
        loading.setValue(true);

        addDisposable(lostFoundClient.getLostFound(
                databaseHelper.getToken(), page, type), dataObserver);
    }

    @SuppressLint("CheckResult")
    public void postCreateLostFound(LostFoundRequest request) {
        loading.setValue(true);

        addDisposable(lostFoundClient.postCreateLostFound(
                databaseHelper.getToken(), request), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void putLostFound(LostFoundRequest request) {
        loading.setValue(true);

        addDisposable(lostFoundClient.postCreateLostFound(
                databaseHelper.getToken(), request), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void deleteLostFound(Integer idx) {
        loading.setValue(true);

        addDisposable(lostFoundClient.deleteLostFound(
                databaseHelper.getToken(), idx), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void getLostFoundSearch(String search) {
        loading.setValue(true);

        addDisposable(lostFoundClient.getLostFoundSearch(
                databaseHelper.getToken(), search), dataObserver);
    }
}
