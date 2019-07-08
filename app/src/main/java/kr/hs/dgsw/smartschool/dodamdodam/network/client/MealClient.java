package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meal;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meals;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.get.MealService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class MealClient extends NetworkClient {

    private MealService meal;

    public MealClient() {
        meal = Utils.RETROFIT.create(MealService.class);
    }

    public Single<List<Meal>> getAllMeal(Token token, int year, int month) {
        return meal.getAllMeal(token.getToken(), year, month).map(response -> {
            if (!response.isSuccessful()) {
                JSONObject errorBody = new JSONObject(Objects
                        .requireNonNull(
                                response.errorBody()).string());
                Log.e("aaa", errorBody.getString("message"));
                throw new Exception(errorBody.getString("message"));
            }
            Log.e("aaa", response.body().getStatus() + "");
            return response.body().getData().getMeals();
        });
    }

    public Single<Meal> getTodayMeal(Token token) {
        return meal.getTodayMeal(token.getToken()).map(getResponseObjectsFunction());
    }

}
