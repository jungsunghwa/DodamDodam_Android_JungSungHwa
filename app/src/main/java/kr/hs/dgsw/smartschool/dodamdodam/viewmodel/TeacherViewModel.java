package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Teachers;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.TeacherClient;

public class TeacherViewModel extends ViewModel {

    private TeacherClient teacherClient;
    private CompositeDisposable disposable;
    private TokenManager manager;
    private DatabaseHelper helper;

    private final MutableLiveData<Teachers> response = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public TeacherViewModel(Context context) {
        teacherClient = new TeacherClient();
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(context);
    }

    public LiveData<Teachers> getResponse() {
        return response;
    }


    public LiveData<String> getError() {
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @SuppressLint("CheckResult")
    public void getTeacher() {
        loading.setValue(true);

        Token token = manager.getToken();

        disposable.add(teacherClient.getTeacher(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Teachers>() {
                    @Override
                    public void onSuccess(Teachers teachers) {

                        response.setValue(teachers);
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

