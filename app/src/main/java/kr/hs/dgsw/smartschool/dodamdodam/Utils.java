package kr.hs.dgsw.smartschool.dodamdodam;

import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Utils {

    public static boolean isWeekEnd = isWeekEnd(new Date());

    public static Retrofit RETROFIT =
            new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl(Constants.DEFAULT_HOST)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").serializeNulls().create()))
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build();

    public static boolean isWeekEnd(Date date) {
        boolean isWeekEnd = false;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayNum) {
            case Calendar.SUNDAY:
                isWeekEnd = true;
                break;
            case Calendar.SATURDAY:
                isWeekEnd = true;
                break;

        }

        return isWeekEnd;
    }

    //TODO Remove to Release
    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();
    }
}
