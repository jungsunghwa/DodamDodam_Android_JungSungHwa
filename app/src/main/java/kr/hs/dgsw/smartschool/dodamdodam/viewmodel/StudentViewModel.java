package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

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
import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.ClassClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.StudentClient;

public class StudentViewModel extends ViewModel {

    private final MutableLiveData<List<ClassInfo>> classInfos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private ClassClient classClient;
    private StudentClient studentClient;
    private CompositeDisposable disposable;
    private DatabaseHelper helper;
    private TokenManager manager;

    public StudentViewModel(Context context) {
        classClient = new ClassClient();
        studentClient = new StudentClient();
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getInstance(context);
        manager = TokenManager.getInstance(context);
    }

    public LiveData<List<ClassInfo>> getClassInfos() {
        return classInfos;
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getError() {
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void getClasses() {
        loading.setValue(true);

        List<ClassInfo> classes = helper.getData(DatabaseManager.TABLE_CLASS, new DatabaseGetDataType<>(ClassInfo.class));

        if (!classes.isEmpty()) {
            loading.setValue(false);
            classInfos.setValue(classes);
            return;
        }

        Token token = manager.getToken();

        disposable.add(
                studentClient.getClasses(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<List<ClassInfo>>() {
                            @Override
                            public void onSuccess(List<ClassInfo> classes) {
                                helper.insert(DatabaseManager.TABLE_CLASS, classes);
                                classInfos.setValue(classes);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                loginErrorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));
    }

    public void getStudent() {
        loading.setValue(true);

        List<Member> students = Stream.of(helper.getAllMembers()).filter(value -> value.getAuth() == 1).toList();

        for (Member member : students) {
            if (member.getId().equals(manager.getMyId())) {
                Utils.identity = Identity.STUDENT;
                break;
            }
        }

        if (!students.isEmpty()) {
            loading.setValue(false);
            isSuccess.setValue(true);
            return;
        }

        Token token = manager.getToken();

        disposable.add(studentClient.getMember(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Member>>() {
                    @Override
                    public void onSuccess(List<Member> response) {
                        List<Member> members = new ArrayList<>();
                        for (Member member : response) {
                            if (member.getId().equals(manager.getMyId())) {
                                switch (member.getAuth()) {
                                    case 1:
                                        Utils.identity = Identity.STUDENT;
                                        break;
                                    case 2:
                                        Utils.identity = Identity.TEACHER;
                                        break;
                                }
                            }
                            members.add(new Member(member));
                        }

                        List<Member> studentList = Stream.of(response).filter(value -> value.getAuth() == 1).toList();
                        List<Member> teacherList = Stream.of(response).filter(value -> value instanceof Teacher).toList();

                        for (int i = 0; i < studentList.size(); i++) {
                            ((kr.hs.dgsw.b1nd.service.model.Student) studentList.get(i)).setMemberID(studentList.get(i).getId());
                        }

                        for (int i = 0; i < teacherList.size(); i++) {
                            ((Teacher) teacherList.get(i)).setMemberID(teacherList.get(i).getId());
                        }

                        helper.insert(DatabaseManager.TABLE_MEMBER, members);
                        helper.insert(DatabaseManager.TABLE_STUDENT, studentList);
                        helper.insert(DatabaseManager.TABLE_TEACHER, teacherList);
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
