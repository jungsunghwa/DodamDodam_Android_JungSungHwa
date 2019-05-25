package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;

public class BaseViewModel<T> extends ViewModel {
    protected final MutableLiveData<String> successMessage = new MutableLiveData<>();
    protected final MutableLiveData<T> data = new MutableLiveData<>();
    protected final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public MutableLiveData<T> getData() {
        return data;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    protected BaseViewModel(Context context){
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    protected void addDisposable(Single single){
        disposable.add((Disposable) single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Response>() {
                            @Override
                            public void onSuccess(Response response) {
                                if (response.getData() == null){
                                    successMessage.setValue(response.getMessage());
                                }else{
                                    data.setValue((T)
                                            convertLocationRequestToMap((LocationRequest) response.getData()));
                                }
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorMessage.setValue(e.getMessage());
                                loading.setValue(false);
                            }
                        }));
    }

    private Map convertLocationRequestToMap(LocationRequest<Location> locations){
        Map<Time, Place> result = new HashMap<>();
        List<Time> times = databaseHelper.getData(DatabaseManager.TABLE_TIME, new DatabaseGetDataType<>(Time.class));
        if (Utils.isWeekEnd)
            times = Stream.of(times).filter(time -> time.getType() == 2).collect(Collectors.toList());
        else
            times = Stream.of(times).filter(time -> time.getType() == 1).collect(Collectors.toList());

        for (Time time : times) {
            result.put(time, null);
        }

        for (Location location : locations.getLocations()) {

            Time time = Stream.of(times)
                    .filter(a -> a.getIdx() == location.getTimetableIdx())
                    .collect(Collectors.toList()).get(0);

            if (location.getPlaceIdx() == null) {
                result.put(time, null);
                continue;
            }
            Place place = databaseHelper.getData(
                    DatabaseManager.TABLE_PLACE
                    , "idx"
                    , Integer.toString(location.getPlaceIdx())
                    , new DatabaseGetDataType<Place>(Place.class)
            );

            result.put(time, place);
        }
        return result;
    }
}
