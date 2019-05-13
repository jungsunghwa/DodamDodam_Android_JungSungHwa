package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.util.SparseArray;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meal;
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

    private static SparseArray<List<Meal>> cacheMonthMeal = new SparseArray<>();

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
        DisposableSingleObserver<Meal> observer = new DisposableSingleObserver<Meal>() {
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
        };

        loading.setValue(true);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        List<Meal> meals = cacheMonthMeal.get(month);

        if (meals == null)
            disposable.add(client.getTodayMeal(helper.getToken()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));
        else
            observer.onSuccess(meals.get(day));
    }

    public void date(int year, int month, int day) {
        DisposableSingleObserver<List<Meal>> observer = new DisposableSingleObserver<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> meal) {
                if (cacheMonthMeal.get(month) == null)
                    cacheMonthMeal.put(month, meal);
                mealData.setValue(cacheMonthMeal.get(month).get(day - 1));
                loading.setValue(false);
            }

            @Override
            public void onError(Throwable e) {
                error.setValue(e);
                loading.setValue(false);
            }
        };

        loading.setValue(true);

        List<Meal> meals = cacheMonthMeal.get(month);

        if (meals == null)
            disposable.add(client.getAllMeal(helper.getToken(), year, month).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer));
        else
            observer.onSuccess(meals);
    }
}
