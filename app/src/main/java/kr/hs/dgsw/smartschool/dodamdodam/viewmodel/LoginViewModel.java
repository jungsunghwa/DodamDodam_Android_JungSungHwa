package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginData;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginRequest;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.LoginClient;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private LoginClient loginClient;
    private CompositeDisposable disposable;
    private TokenManager manager;

    public LoginViewModel(Application application) {
        super(application);
        loginClient = new LoginClient(application);
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(application);
    }

    public LiveData<Boolean> isSuccess() {
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
                                if (!TokenManager.isValidate(id, loginData.getToken()))
                                    onError(new Throwable("잘못된 정보가 반환되었습니다. 다시 시도해주세요."));
                                manager.setToken(loginData.getToken(), loginData.getRefreshToken());
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
