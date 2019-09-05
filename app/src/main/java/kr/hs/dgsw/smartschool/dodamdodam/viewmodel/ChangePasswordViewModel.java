package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.ChangePasswordClient;

public class ChangePasswordViewModel extends LoginViewModel {

    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private ChangePasswordClient changePasswordClient;
    private CompositeDisposable disposable;
    private TokenManager manager;

    public ChangePasswordViewModel(Application application) {
        super(application);
        changePasswordClient = new ChangePasswordClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(application);
    }

    public void changePassword() {
        loading.setValue(true);

        //disposable.add(changePasswordClient.)
    }
}
