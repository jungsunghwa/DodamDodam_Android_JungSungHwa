package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.CounselClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;

public class CounselViewModel extends BaseViewModel<List<Counsel>> {
    private CounselClient counselClient;
    private TokenManager manager;

    public CounselViewModel(Application application) {
        super(application);
        counselClient = new CounselClient(application);
        manager = TokenManager.getInstance(application);
    }

    public void getAllCounsel() {
        loading.setValue(true);

        addDisposable(counselClient.getAllCounsel(
                manager.getToken()), getDataObserver());
    }

    public void postCounsel(CounselRequest request) {
        loading.setValue(true);

        addDisposable(counselClient.postCounsel(
                manager.getToken(), request), getBaseObserver());
    }

    public void getCertainCounsel(int counselIdx) {
        loading.setValue(true);

        addDisposable(counselClient.getCertainCounsel(
                manager.getToken(), counselIdx), getDataObserver());
    }

    public void deleteCounsel(int counselIdx) {
        loading.setValue(true);

        addDisposable(counselClient.deleteCounsel(
                manager.getToken(), counselIdx), getBaseObserver());
    }

    @SuppressLint("CheckResult")
    public void postCounselAllow(CounselRequest request) {
        loading.setValue(true);

        addDisposable(counselClient.postCounselAllow(
                manager.getToken(), request), getBaseObserver());
    }

    @SuppressLint("CheckResult")
    public void postCounselCancel(CounselRequest request) {
        loading.setValue(true);

        addDisposable(counselClient.postCounselCancel(
                manager.getToken(), request), getBaseObserver());
    }


}
