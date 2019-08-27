package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.TimeTables;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.TimeTableClient;

public class TimeTableViewModel extends BaseViewModel<List<Time>> {

    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private TimeTableClient timeTableClient;
    private CompositeDisposable disposable;
    private DatabaseHelper helper;
    private TokenManager manager;

    public TimeTableViewModel(Application application) {
        super(application);
        timeTableClient = new TimeTableClient(application);
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getInstance(application);
        manager = TokenManager.getInstance(application);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void getTimeTable() {
        loading.setValue(true);

        DisposableSingleObserver<TimeTables> observer = new DisposableSingleObserver<TimeTables>() {
            @Override
            public void onSuccess(TimeTables timeTables) {
                List<Time> timeTable = timeTables.getTimeTables();

                helper.insert(DatabaseManager.TABLE_TIME, timeTable);

                if (Utils.isWeekEnd)
                    timeTable = Stream.of(timeTable).filter(time -> time.getType() == 2).collect(Collectors.toList());
                else
                    timeTable = Stream.of(timeTable).filter(time -> time.getType() == 1).collect(Collectors.toList());

                data.setValue(timeTable);
                loading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                errorMessage.setValue(e.getMessage());
                loading.setValue(false);
            }
        };
        List<Time> timeList = helper.getData(DatabaseManager.TABLE_TIME, new DatabaseGetDataType<>(Time.class));
        if (!timeList.isEmpty()) {
            loading.setValue(false);

            if (Utils.isWeekEnd)
                timeList = Stream.of(timeList).filter(time -> time.getType() == 2).collect(Collectors.toList());
            else
                timeList = Stream.of(timeList).filter(time -> time.getType() == 1).collect(Collectors.toList());

            data.setValue(timeList);
            return;
        }
        addDisposable(timeTableClient.getTimeTable(manager.getToken()), observer);
    }
}
