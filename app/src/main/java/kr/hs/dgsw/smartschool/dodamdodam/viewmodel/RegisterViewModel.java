package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.b1nd.service.retrofit2.response.register.StudentRegisterRequest;
import kr.hs.dgsw.b1nd.service.retrofit2.response.register.TeacherRegisterRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.RegisterClient;

public class RegisterViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private RegisterClient registerClient;
    private CompositeDisposable disposable;

    public RegisterViewModel(Application application) {
        super(application);
        registerClient = new RegisterClient(application);
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void studentRegister(StudentRegisterRequest request) {
        loading.setValue(true);
        disposable.add(registerClient.studentRegister(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<String>(){
            @Override
            public void onSuccess(String s) {
                successMessage.setValue(s);
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

    public void teacherRegister(TeacherRegisterRequest request) {
        loading.setValue(true);
        disposable.add(registerClient.teacherRegister(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<String>(){
            @Override
            public void onSuccess(String s) {
                successMessage.setValue(s);
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
