package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.get;

import java.util.function.Function;

import io.reactivex.Single;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meal;
import kr.hs.dgsw.smartschool.dodamdodam.Model.meal.Meals;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MealService {

    @GET("meal")
    Single<retrofit2.Response<Response<Meals>>> getAllMeal(
            @Header("x-access-token") String token,
            @Query("year") int year,
            @Query("month") int month
    );

    @GET("meal/today")
    Single<retrofit2.Response<Response<Meal>>> getTodayMeal(
            @Header("x-access-token") String token
    );

}
