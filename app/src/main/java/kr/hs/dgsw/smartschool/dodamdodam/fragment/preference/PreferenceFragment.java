package kr.hs.dgsw.smartschool.dodamdodam.fragment.preference;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.annimon.stream.Optional;

import java.util.Objects;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.widget.DodamMultiWaveHeader;

public class PreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);

        final String prefKeyThumbnail = getString(R.string.pref_key_thumbnail);
        final String prefKeyFrame = getString(R.string.pref_key_frame);
        final String prefKeyDaylight = getString(R.string.pref_key_daynight);
        final String prefKeyWaveAnimation = getString(R.string.pref_key_wave_animation);
        final Preference prefThumbnail = findPreference(prefKeyThumbnail);
        final Preference prefFrame = findPreference(prefKeyFrame);
        final Preference prefDaylight = findPreference(prefKeyDaylight);
        final Preference prefWaveAnimation = findPreference(prefKeyWaveAnimation);

        SharedPreferences preferences = getPreferenceManager().getSharedPreferences();

        Optional.ofNullable(prefFrame).executeIfPresent(preference -> preference.setEnabled(Objects.equals(preferences.getString(prefKeyThumbnail, "low"), "frame")));
        Optional.ofNullable(prefThumbnail).executeIfPresent(preference -> preference.setOnPreferenceChangeListener((pref, newValue) -> {
            String value = String.valueOf(newValue);
            Optional.ofNullable(prefFrame).executeIfPresent(inner -> inner.setEnabled(value.equals("frame")));
            return true;
        }));
        Optional.ofNullable(prefDaylight).executeIfPresent(preference -> preference.setOnPreferenceChangeListener((pref, newValue) -> {
            Utils.settingDayNight(String.valueOf(newValue));
            return true;
        }));
        Optional.ofNullable(prefWaveAnimation).executeIfPresent(preference -> preference.setOnPreferenceChangeListener((pref, newValue) -> {
            boolean value = (boolean) newValue;
            if (value)
                DodamMultiWaveHeader.startHeaders();
            else
                DodamMultiWaveHeader.stopHeaders();
            return true;
        }));
    }
}
