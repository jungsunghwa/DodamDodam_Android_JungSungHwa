package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.PlaceList;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.PlaceClient;

public class PlaceViewModel extends BaseViewModel {
    private PlaceClient placeClient;
    private CompositeDisposable disposable;

    private TokenManager manager;

    public PlaceViewModel(Application application) {
        super(application);
        placeClient = new PlaceClient(application);
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(application);
    }

    @Override
    public LiveData<List<Place>> getData() {
        return data;
    }


    public LiveData<Boolean> getLoading() {
        return loading;
    }


    public void getAllPlace() {

        loading.setValue(true);

        DisposableSingleObserver<PlaceList> observer = new DisposableSingleObserver<PlaceList>() {
            @Override
            public void onSuccess(PlaceList places) {
                data.setValue(places.getPlaces());
                helper.insert(DatabaseManager.TABLE_PLACE, places.getPlaces());
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

        addDisposable(placeClient.getAllPlace(manager.getToken()), observer);
    }
}
