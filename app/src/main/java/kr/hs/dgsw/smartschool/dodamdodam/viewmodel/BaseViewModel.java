package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;

public class BaseViewModel<T> extends ViewModel {
    protected final MutableLiveData<String> successMessage = new MutableLiveData<>();
    protected final MutableLiveData<T> data = new MutableLiveData<>();
    protected final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    protected BaseViewModel(Context context) {
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getInstance(context);
    }

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

    /*protected void addDisposable(Single single) {
        disposable.add((Disposable) single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableSingleObserver<Response>() {
                            @Override
                            public void onSuccess(Response response) {
                                if (response.getData() == null) {
                                    successMessage.setValue(response.getMessage());
                                } else {
                                    data.setValue((T) convertLocationRequestToMap((LocationRequest<LocationInfo>) response.getData()));
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

    private Map<Time, Place> convertLocationRequestToMap(LocationRequest<LocationInfo> locations) {
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
                    DatabaseManager.TABLE_PLACE,
                    "idx",
                    Integer.toString(location.getPlaceIdx()),
                    new DatabaseGetDataType<>(Place.class)
            );

            result.put(time, place);
        }
        return result;
    }*/
}
