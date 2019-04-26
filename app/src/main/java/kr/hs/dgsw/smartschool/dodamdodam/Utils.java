package kr.hs.dgsw.smartschool.dodamdodam;

import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    public static boolean isWeekEnd = isWeekEnd(new Date());

    public static Retrofit RETROFIT =
            new Retrofit.Builder()
                    .baseUrl(Constants.DEFAULT_HOST)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build();

    public static boolean isWeekEnd(Date date) {
        String dateType = "yyyy-MM-dd";
        String day = "" ;
        boolean isWeekEnd = false;

        Calendar cal = Calendar.getInstance() ;
        cal.setTime(date);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;

        switch(dayNum){
            case 1:
                isWeekEnd = true;
                break ;
            case 7:
                isWeekEnd = true;
                break ;

        }



        return isWeekEnd;
    }
}
