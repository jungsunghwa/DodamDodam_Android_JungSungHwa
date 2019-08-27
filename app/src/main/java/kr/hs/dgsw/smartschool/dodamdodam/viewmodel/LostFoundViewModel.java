package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.disposables.CompositeDisposable;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LostFoundClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;

public class LostFoundViewModel extends BaseViewModel<List<LostFound>> {
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Integer> viewType = new MutableLiveData<>();
    public LostFoundRequest request = new LostFoundRequest();
    private LostFoundClient lostFoundClient;
    private TokenManager manager;

    public LostFoundViewModel(Application application) {
        super(application);
        lostFoundClient = new LostFoundClient(application);
        manager = TokenManager.getInstance(application);
    }

    public void getLostFound(Integer page, Integer type) {
        loading.setValue(true);

        addDisposable(lostFoundClient.getLostFound(
                manager.getToken(), page, type), getDataObserver());
    }

    public void postCreateLostFound() {
        loading.setValue(true);

        addDisposable(lostFoundClient.postCreateLostFound(
                manager.getToken(), request), getBaseObserver());
    }

    public void putLostFound() {
        loading.setValue(true);

        addDisposable(lostFoundClient.putLostFound(
                manager.getToken(), request), getBaseObserver());
    }

    public void deleteLostFound(Integer idx) {
        loading.setValue(true);

        addDisposable(lostFoundClient.deleteLostFound(
                manager.getToken(), idx), getBaseObserver());
    }

    public void getLostFoundSearch(String search) {
        loading.setValue(true);

        addDisposable(lostFoundClient.getLostFoundSearch(
                manager.getToken(), search), getDataObserver());
    }
}
