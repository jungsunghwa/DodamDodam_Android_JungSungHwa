package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.PlaceClient;

public class PlaceViewModel extends ViewModel {
    private PlaceClient placeClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<List<Place>> response = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public PlaceViewModel(Context context) {
        placeClient = new PlaceClient();
        disposable = new CompositeDisposable();
        databaseHelper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<List<Place>> getIsSuccess() {
        return response;
    }

    public LiveData<String> getError() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void getAllPlace() {
        loading.setValue(true);
        List<Place> placeList = databaseHelper.getData(DatabaseManager.TABLE_PLACE,
                new DatabaseGetDataType<>(Place.class));
        if (!placeList.isEmpty()) {
            loading.setValue(false);
            response.setValue(placeList);
            return;
        }
        disposable.add(placeClient.getAllPlace(databaseHelper.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<List<Place>>() {
                            @Override
                            public void onSuccess(List<Place> placeList) {
                                databaseHelper.insert(DatabaseManager.TABLE_PLACE, placeList);
                                response.setValue(placeList);
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
