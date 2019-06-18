package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.CounselClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;

public class CounselViewModel extends BaseViewModel<Counsel> {
    private CounselClient counselClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<Counsel> response = new MutableLiveData<>();

    public CounselViewModel(Context context) {
        super(context);
        counselClient = new CounselClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<Counsel> getResponse() {
        return response;
    }

    @SuppressLint("CheckResult")
    public void getAllCounsel() {
        loading.setValue(true);

        addDisposable(counselClient.getAllCounsel(
                databaseHelper.getToken()), dataObserver);
    }

    @SuppressLint("CheckResult")
    public void postCounsel(CounselRequest request) {
        loading.setValue(true);

        addDisposable(counselClient.postCounsel(
                databaseHelper.getToken(), request), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void getCertainCounsel(int counselIdx) {
        loading.setValue(true);

        addDisposable(counselClient.getCertainCounsel(
                databaseHelper.getToken(), counselIdx), dataObserver);
    }

    @SuppressLint("CheckResult")
    public void deleteCounsel(int counselIdx) {
        loading.setValue(true);
        addDisposable(counselClient.deleteCounsel(
                databaseHelper.getToken(), counselIdx), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void postCounselAllow(CounselRequest request) {
        loading.setValue(true);

        addDisposable(counselClient.postCounselAllow(
                databaseHelper.getToken(), request), baseObserver);
    }

    @SuppressLint("CheckResult")
    public void postCounselCancel(CounselRequest request) {
        loading.setValue(true);

        addDisposable(counselClient.postCounselCancel(
                databaseHelper.getToken(), request), baseObserver);
    }
}
