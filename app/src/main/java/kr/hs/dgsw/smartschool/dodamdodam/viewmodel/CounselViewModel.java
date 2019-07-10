package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.CounselClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;

public class CounselViewModel extends BaseViewModel<List<Counsel>> {
    private CounselClient counselClient;
    private CompositeDisposable disposable;
    private TokenManager manager;
    private DatabaseHelper helper;

    private final MutableLiveData<String> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public CounselViewModel(Context context) {
        super(context);
        counselClient = new CounselClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(context);
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    @SuppressLint("CheckResult")
    public void getAllCounsel() {
        loading.setValue(true);

        addDisposable(counselClient.getAllCounsel(
                manager.getToken()), dataObserver);
    }

    @SuppressLint("CheckResult")
    public void postCounsel(CounselRequest request) {
        loading.setValue(true);

        addDisposable(counselClient.postCounsel(
                manager.getToken(), request), baseObserver);
    }

    public void getCertainCounsel(int counselIdx) {
        loading.setValue(true);

        addDisposable(counselClient.getCertainCounsel(
                manager.getToken(), counselIdx), dataObserver);
    }

    @SuppressLint("CheckResult")
    public void deleteCounsel(int counselIdx) {
        loading.setValue(true);

        addDisposable(counselClient.deleteCounsel(
                manager.getToken(), counselIdx), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void postCounselAllow(CounselRequest request) {
        loading.setValue(true);

        addDisposable(counselClient.postCounselAllow(
                manager.getToken(), request), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void postCounselCancel(CounselRequest request) {
        loading.setValue(true);

        addDisposable(counselClient.postCounselCancel(
                manager.getToken(), request), baseObserver);
    }


}
