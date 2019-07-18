package kr.hs.dgsw.smartschool.dodamdodam;

import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Utils {

    public static boolean isWeekEnd = isWeekEnd(new Date());

    //FIXME
    public static Identity identity = Identity.TEACHER;

    public static Retrofit RETROFIT =
            new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl(Constants.DEFAULT_HOST)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build();

    public static boolean isWeekEnd(Date date) {
        boolean isWeekEnd = false;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayNum) {
            case Calendar.SUNDAY:
            case Calendar.SATURDAY:
                isWeekEnd = true;
                break;

        }

        return isWeekEnd;
    }

    public static void settingDayNight(String value) {
        switch (value) {
            case "system":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case "auto_battery":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                break;
            case "yes":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "no":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static String getDefaultDayNightSetting() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ? "system" : "auto_battery";
    }

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }
}
