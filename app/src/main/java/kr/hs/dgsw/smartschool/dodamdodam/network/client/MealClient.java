package kr.hs.dgsw.smartschool.dodamdodam.network.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meal;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meals;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.get.MealService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.internal.EverythingIsNonNull;

public class MealClient {

    private MealService meal;

    public MealClient() {
        meal = Utils.RETROFIT.create(MealService.class);
    }







    
    public Single<List<Meal>> getAllMeal(Token token, int year, int month) {
        return Single.create(observer -> meal.getAllMeal(token.getToken(), year, month).enqueue(new Callback<Response<Meals>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Meals>> call, retrofit2.Response<Response<Meals>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData().getMeals());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Meals>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }

    public Single<Meal> getTodayMeal(Token token) {
        return Single.create(observer -> meal.getTodayMeal(token.getToken()).enqueue(new Callback<Response<Meal>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<Response<Meal>> call, retrofit2.Response<Response<Meal>> response) {
                if (response.isSuccessful())
                    observer.onSuccess(response.body().getData());
                else
                    try {
                        JSONObject errorBody = new JSONObject(Objects
                                .requireNonNull(
                                        response.errorBody()).string());
                        observer.onError(new Throwable(errorBody.getString("message")));
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Response<Meal>> call, Throwable t) {
                observer.onError(t);
            }
        }));
    }
}
