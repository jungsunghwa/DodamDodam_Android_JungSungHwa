package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Teachers;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.TeacherClient;

public class TeacherViewModel extends BaseViewModel<Teachers> {

    private final MutableLiveData<Teachers> response = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Teacher> selectedTeacher = new MutableLiveData<>();
    private TeacherClient teacherClient;
    private TokenManager manager;
    private CompositeDisposable disposable;

    public TeacherViewModel(Application application) {
        super(application);
        teacherClient = new TeacherClient();
        manager = TokenManager.getInstance(application);
        disposable = new CompositeDisposable();
    }

    public MutableLiveData<Teacher> getSelectedTeacher() {
        return selectedTeacher;
    }

    @SuppressLint("CheckResult")
    public void getTeacher() {
        loading.setValue(true);
        Token token = manager.getToken();

        addDisposable(teacherClient.getTeacher(token), getDataObserver());
    }

    public void select(Teacher teacher, boolean selected) {
        disposable.add(Single.<Teacher>create(observer -> {
            if (selected)
                observer.onSuccess(teacher);
            else
                observer.onSuccess(new Teacher());
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                new DisposableSingleObserver<Teacher>() {
                    @Override
                    public void onSuccess(Teacher teacher) {
                        selectedTeacher.setValue(teacher);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }
        ));
    }
}

