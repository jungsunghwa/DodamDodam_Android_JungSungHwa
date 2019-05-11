package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginData;
import kr.hs.dgsw.b1nd.service.retrofit2.response.login.LoginRequest;
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
                                Log.i("token", loginData.getToken());
                                try {
                                    DecodedJWT decodedJWT = JWT.decode(loginData.getToken());
                                    String tokenMemberId = decodedJWT.getClaim("memberId").asString();
                                    if (!id.equals(tokenMemberId)) {
                                        //TODO TOKEN MISMATCH ERROR MESSAGE
                                        onError(new Throwable("잘못 된 정보가 반환되었습니다. 다시 시도해주세요."));
                                        return;
                                    }
                                } catch (JWTDecodeException ignore) {
                                }
                                databaseHelper.insert(DatabaseManager.TABLE_TOKEN, loginData);

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
