package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.NetworkClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.Request;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Login.LoginData;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Login.LoginRequest;

public class LoginViewModel extends ViewModel {

    NetworkClient networkClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public LoginViewModel(Context context) {
        networkClient = new NetworkClient();
        disposable = new CompositeDisposable();
        databaseHelper = new DatabaseHelper(context);
    }

    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getError() {
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @SuppressLint("CheckResult")
    public void login(String id, String pw) {
        loading.setValue(true);
        disposable.add(networkClient.login(new LoginRequest(id, pw)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<LoginData>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onSuccess(LoginData loginData) {
                                LoginData a = new LoginData();
                                databaseHelper.insert("token", loginData);
                                a = databaseHelper.<LoginData>getData("token", loginData);
                                Log.e("token", a.getToken());
                                isSuccess.setValue(true);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                loginErrorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));

    }
}
