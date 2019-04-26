package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Meal;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Meals;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.MealClient;

/**
 * @author kimji
 */
public class MainViewModel extends ViewModel {

    private final MutableLiveData<Meal> mealData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private MealClient client;
    private CompositeDisposable disposable;
    private DatabaseHelper helper;

    public MainViewModel(Context context) {
        client = new MealClient();
        disposable = new CompositeDisposable();
        helper = DatabaseHelper.getDatabaseHelper(context);
    }

    public MutableLiveData<Meal> getMealData() {
        return mealData;
    }

    public MutableLiveData<Throwable> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void today() {
        loading.setValue(true);
        disposable.add(client.getTodayMeal(helper.getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<Meal>() {
                            @Override
                            public void onSuccess(Meal meal) {
                                mealData.setValue(meal);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }
                ));
    }

    public void date(int year, int month, int day) {
        loading.setValue(true);
        disposable.add(client.getAllMeal(helper.getToken(), year, month).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                        new DisposableSingleObserver<List<Meal>>() {
                            @Override
                            public void onSuccess(List<Meal> meal) {
                                mealData.setValue(meal.get(day - 1));
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                error.setValue(e);
                                loading.setValue(false);
                            }
                        }
                ));
    }
}
