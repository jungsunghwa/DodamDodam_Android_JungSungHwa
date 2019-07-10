package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LostFoundClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;

public class LostFoundViewModel extends BaseViewModel<List<LostFound>>{
    private LostFoundClient lostFoundClient;

    private final MutableLiveData<String> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private CompositeDisposable disposable;
    private TokenManager manager;

    public LostFoundViewModel(Context context) {
        super(context);
        lostFoundClient = new LostFoundClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(context);
    }

    public void getLostFound(Integer page, Integer type) {
        loading.setValue(true);

        addDisposable(lostFoundClient.getLostFound(
                manager.getToken(), page, type), dataObserver);
    }

    public void postCreateLostFound(LostFoundRequest request) {
        loading.setValue(true);

        addDisposable(lostFoundClient.postCreateLostFound(
                manager.getToken(), request), baseObserver);
    }

    public void putLostFound(LostFoundRequest request) {
        loading.setValue(true);

        addDisposable(lostFoundClient.postCreateLostFound(
                manager.getToken(), request), baseObserver);
    }

    public void deleteLostFound(Integer idx) {
        loading.setValue(true);

        addDisposable(lostFoundClient.deleteLostFound(
                manager.getToken(), idx), baseObserver);
    }

    public void getLostFoundSearch(String search) {
        loading.setValue(true);

        addDisposable(lostFoundClient.getLostFoundSearch(
                manager.getToken(), search), dataObserver);
    }
}
