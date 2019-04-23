package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.PlaceClient;

public class PlaceViewModel extends ViewModel {
    private PlaceClient placeClient;
    private CompositeDisposable disposable;
    private DatabaseHelper databaseHelper;

    private final MutableLiveData<List<Place>> response = new MutableLiveData<>();
    private final MutableLiveData<String> loginErrorMessage = new MutableLiveData<>();
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
        return loginErrorMessage;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void getAllPlace() {
        loading.setValue(true);
        ArrayList<Place> placeList =
                (ArrayList<Place>) databaseHelper.getData("place",
                        new DatabaseGetDataType<>(Place.class));
        if (!placeList.isEmpty()){
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
                                databaseHelper.insert("place", placeList);
                                response.setValue(placeList);
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
