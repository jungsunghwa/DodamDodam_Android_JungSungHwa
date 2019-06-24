package kr.hs.dgsw.smartschool.dodamdodam;

import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Utils {

    public static boolean isWeekEnd = isWeekEnd(new Date());
    public static Identity identity = Identity.TEACHER;

    public static Retrofit RETROFIT =
            new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl(Constants.DEFAULT_HOST)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create()))
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

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG)
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        return builder.build();
    }
}
