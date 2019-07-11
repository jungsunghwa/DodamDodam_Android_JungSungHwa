package kr.hs.dgsw.smartschool.dodamdodam;

import android.app.Application;
import android.preference.PreferenceManager;

import com.annimon.stream.Optional;

public class DodamDodamApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Optional.ofNullable(PreferenceManager.getDefaultSharedPreferences(this))
                .executeIfPresent(sharedPreferences ->
                        Utils.settingDayNight(
                                Optional.ofNullable(
                                        sharedPreferences.getString(
                                                getString(R.string.pref_key_daynight), Utils.getDefaultDayNightSetting()))
                                        .orElse(Utils.getDefaultDayNightSetting())));
    }
}
