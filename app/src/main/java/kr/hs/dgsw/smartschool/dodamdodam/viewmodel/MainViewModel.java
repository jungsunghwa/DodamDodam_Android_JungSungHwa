package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.content.Context;
import android.util.SparseArray;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meal;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.MealClient;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.get.MealService;

/**
 * @author kimji
 */
public class MainViewModel extends BaseViewModel<Meal> {

    private MealClient client;
    private DatabaseHelper helper;

    private static SparseArray<List<Meal>> cacheMonthMeal = new SparseArray<>();

    public MainViewModel(Context context) {
        super(context);
        client = new MealClient();
        helper = DatabaseHelper.getDatabaseHelper(context);
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public void today() {
        loading.setValue(true);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        List<Meal> meals = cacheMonthMeal.get(month);

        if (meals == null) {
            Single single = client.getTodayMeal(helper.getToken());
            addDisposable(single, dataObserver);
        } else dataObserver.onSuccess(meals.get(day));
    }

    public void date(int year, int month, int day) {
        DisposableSingleObserver<List<Meal>> observer = new DisposableSingleObserver<List<Meal>>() {
            @Override
            public void onSuccess(List<Meal> meal) {
                if (cacheMonthMeal.get(month) == null)
                    cacheMonthMeal.put(month, meal);
                data.setValue(cacheMonthMeal.get(month).get(day - 1));
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
            addDisposable(client.getAllMeal(helper.getToken(), year, month), observer);
        else
            observer.onSuccess(meals);
    }
}
