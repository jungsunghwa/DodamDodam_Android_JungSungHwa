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
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.PlaceList;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseManager;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.PlaceClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;

public class PlaceViewModel extends ViewModel {
    private final MutableLiveData<List<Place>> data = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private PlaceClient placeClient;
    private CompositeDisposable disposable;
    private DatabaseHelper helper;
    private TokenManager manager;

    public PlaceViewModel(Context context) {
        placeClient = new PlaceClient();
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getInstance(context);
        manager = TokenManager.getInstance(context);
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
        List<Place> placeList = helper.getData(DatabaseManager.TABLE_PLACE,
                new DatabaseGetDataType<>(Place.class));
        if (!placeList.isEmpty()) {
            loading.setValue(false);
            data.setValue(placeList);
            return;
        }

        disposable.add(placeClient.getAllPlace(manager.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Response>() {
                            @Override
                            @SuppressWarnings("unchecked")
                            public void onSuccess(Response response) {
                                List<Place> placeList = ((Response<PlaceList>) response).getData().getPlaces();
                                helper.insert(DatabaseManager.TABLE_PLACE, placeList);
                                data.setValue(placeList);
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
