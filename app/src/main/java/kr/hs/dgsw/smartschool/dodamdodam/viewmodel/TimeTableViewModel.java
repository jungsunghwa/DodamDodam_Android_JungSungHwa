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
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.TimeTableClient;

public class TimeTableViewModel extends ViewModel {

    private TimeTableClient timeTableClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<List<Time>> response = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public TimeTableViewModel(Context context) {
        timeTableClient = new TimeTableClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<List<Time>> getIsSuccess() {
        return response;
    }

    public LiveData<String> getError() {
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void getTimeTable() {
        loading.setValue(true);
        List<Time> timeList = (ArrayList<Time>) databaseHelper.getData("time", new DatabaseGetDataType<>(Time.class));
        if (!timeList.isEmpty()){
            loading.setValue(false);

            if (Utils.isWeekEnd)
                timeList = Stream.of(timeList).filter(time -> time.getType() == 2).collect(Collectors.toList());
            else
                timeList = Stream.of(timeList).filter(time -> time.getType() == 1).collect(Collectors.toList());

            response.setValue(timeList);
            return;
        }
        disposable.add(timeTableClient.getTimeTable(databaseHelper.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<List<Time>>() {
                            @Override
                            public void onSuccess(List<Time> timeTable) {
                                databaseHelper.insert("time", timeTable);

                                if (Utils.isWeekEnd)
                                    timeTable = Stream.of(timeTable).filter(time -> time.getType() == 2).collect(Collectors.toList());
                                else
                                    timeTable = Stream.of(timeTable).filter(time -> time.getType() == 1).collect(Collectors.toList());

                                response.setValue(timeTable);
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
