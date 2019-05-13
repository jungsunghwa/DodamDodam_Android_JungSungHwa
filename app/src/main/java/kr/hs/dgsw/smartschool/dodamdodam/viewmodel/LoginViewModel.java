package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginData;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginRequest;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LoginClient;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private LoginClient loginClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    public LoginViewModel(Context context) {
        loginClient = new LoginClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getError() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void login(String id, String pw) {
        loading.setValue(true);
        disposable.add(loginClient.login(new LoginRequest(id, pw)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<LoginData>() {
                            @Override
                            public void onSuccess(LoginData loginData) {
                                databaseHelper.insert(DatabaseManager.TABLE_TOKEN, loginData);
                                Log.i("token", loginData.getToken());
                                Utils.myId = id;
                                isSuccess.setValue(true);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));
    }
}
