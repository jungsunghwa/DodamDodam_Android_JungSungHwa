package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.ClassClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.TimeTableClient;

public class StudentViewModel extends ViewModel {

    private ClassClient classClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<ArrayList<ClassInfo>> response = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public StudentViewModel(Context context) {
        classClient = new ClassClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<ArrayList<ClassInfo>> getIsSuccess() {
        return response;
    }

    public LiveData<String> getError() {
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void getClasses(){
        loading.setValue(true);
        ArrayList<ClassInfo> timeList = (ArrayList<ClassInfo>) databaseHelper.getData("class", new DatabaseGetDataType<>(ClassInfo.class));
        if (!timeList.isEmpty()){
            loading.setValue(false);
            response.setValue(timeList);
            return;
        }
        disposable.add(classClient.getClasses(databaseHelper.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<ArrayList<ClassInfo>>() {
                            @Override
                            public void onSuccess(ArrayList<ClassInfo> classes) {
                                databaseHelper.insert("class", classes);

                                response.setValue(classes);
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
