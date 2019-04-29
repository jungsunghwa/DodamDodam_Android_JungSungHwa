package kr.hs.dgsw.smartschool.dodamdodam.network.retrofit.interfaces.get;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Meal;
import kr.hs.dgsw.smartschool.dodamdodam.Model.MealPlaceHolder;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Meals;
import kr.hs.dgsw.smartschool.dodamdodam.network.response.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MealService {
    @GET("meal")
    Call<Response<Meals>> getAllMeal(
            @Header("x-access-token") String token,
            @Query("year") int year,
            @Query("month") int month
    );

    @GET("meal/today")
    Call<Response<MealPlaceHolder>> getTodayMeal(
            @Header("x-access-token") String token
    );
}
