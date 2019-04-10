package kr.hs.dgsw.smartschool.dodamdodam;

import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    public static Retrofit RETROFIT =
            new Retrofit.Builder()
                    .baseUrl(StaticResources.DEFAULT_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build();
}
