package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.PlaceClient;

public class PlaceViewModel extends BaseViewModel {
    private PlaceClient placeClient;
    private CompositeDisposable disposable;
    private DatabaseHelper helper;

    private final MutableLiveData<List<Place>> data = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public PlaceViewModel(Context context) {
        super(context);
        placeClient = new PlaceClient();
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getDatabaseHelper(context);
    }

    public LiveData<List<Place>> getData() {
        return data;
    }

    public LiveData<String> getError() {
        return errorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }
    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public void getAllPlace() {

        loading.setValue(true);

        DisposableSingleObserver<List<Place>> observer = new DisposableSingleObserver<List<Place>>() {
            @Override
            public void onSuccess(List<Place> places) {
                data.setValue(places);
                helper.insert(DatabaseManager.TABLE_PLACE, places);
                loading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                errorMessage.setValue(e.toString());
                loading.setValue(false);
            }
        };

        List<Place> placeList = helper.getData(DatabaseManager.TABLE_PLACE,
                new DatabaseGetDataType<>(Place.class));

        if (!placeList.isEmpty()) {
            loading.setValue(false);
            data.setValue(placeList);
            return;
        }

        addDisposable(placeClient.getAllPlace(helper.getToken()), observer);
    }
}
