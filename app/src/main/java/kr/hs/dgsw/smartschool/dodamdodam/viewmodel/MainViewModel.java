package kr.hs.dgsw.smartschool.dodamdodam.viewmodel;

import android.app.Application;
import android.util.SparseArray;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meal;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.network.client.MealClient;

/**
 * @author kimji
 */
public class MainViewModel extends BaseViewModel<Meal> {

    private static SparseArray<List<Meal>> cacheMonthMeal = new SparseArray<>();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MealClient client;
    private CompositeDisposable disposable;
    private TokenManager manager;

    public MainViewModel(Application application) {
        super(application);
        client = new MealClient(application);
        disposable = new CompositeDisposable();
        manager = TokenManager.getInstance(application);
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
            Single single = client.getTodayMeal(manager.getToken());
            addDisposable(single, getDataObserver());
        } else getDataObserver().onSuccess(meals.get(day));
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
            addDisposable(client.getAllMeal(manager.getToken(), year, month), observer);
        else
            observer.onSuccess(meals);
    }
}
