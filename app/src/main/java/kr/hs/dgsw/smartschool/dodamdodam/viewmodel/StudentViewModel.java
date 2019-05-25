package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import com.annimon.stream.Stream;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.ClassClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.StudentClient;

public class StudentViewModel extends ViewModel {

    private ClassClient classClient;
    private StudentClient studentClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<ArrayList<ClassInfo>> classInfos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public StudentViewModel(Context context) {
        classClient = new ClassClient();
        studentClient = new StudentClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<ArrayList<ClassInfo>> getClassInfos() {
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

        ArrayList<ClassInfo> classes = (ArrayList<ClassInfo>) databaseHelper.getData("class", new DatabaseGetDataType<>(ClassInfo.class));

        if (!classes.isEmpty()) {
            loading.setValue(false);
            classInfos.setValue(classes);
            return;
        }

        Token token = databaseHelper.getToken();

        disposable.add(
                studentClient.getClasses(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<ArrayList<ClassInfo>>() {
                            @Override
                            public void onSuccess(ArrayList<ClassInfo> classes) {
                                databaseHelper.insert("class", classes);
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

        ArrayList<Member> students = (ArrayList<Member>) Stream.of(databaseHelper.getAllMembers()).filter(value -> value.getAuth() == 1).toList();

        for (Member member : students) {
            if (member.getId().equals(Utils.myId)) {
                Utils.identity = Identity.STUDENT;
                break;
            }
        }

        if (!students.isEmpty()) {

            loading.setValue(false);
            isSuccess.setValue(true);
            return;
        }

        Token token = databaseHelper.getToken();

        disposable.add(studentClient.getMember(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Member>>() {
                    @Override
                    public void onSuccess(ArrayList<Member> response) {
                        ArrayList<Member> members = new ArrayList();
                        for (Member member : response) {
                            if (member.getId().equals(Utils.myId)) {
                                switch (member.getAuth()) {
                                    case 1:
                                        Utils.identity = Identity.STUDENT;
                                        break;
                                    case 2:
                                        Utils.identity = Identity.STUDENT;
                                        break;
                                }
                            }
                            members.add(new Member(member));
                        }

                        List<Member> studentList = Stream.of(response).filter(value -> value.getAuth() == 1).toList();
                        List<Member> teacherList = Stream.of(response).filter(value -> value.getAuth() == 2).toList();

                        for (int i = 0; i < studentList.size(); i++) {
                            ((kr.hs.dgsw.b1nd.service.model.Student) studentList.get(i)).setMemberID(studentList.get(i).getId());
                        }

                        for (int i = 0; i < teacherList.size(); i++) {
                            ((Teacher)teacherList.get(i)).setMemberID(teacherList.get(i).getId());
                        }

                        databaseHelper.insert(DatabaseManager.TABLE_MEMBER, members);
                        databaseHelper.insert(DatabaseManager.TABLE_STUDENT, studentList);
                        databaseHelper.insert(DatabaseManager.TABLE_TEACHER, teacherList);
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
