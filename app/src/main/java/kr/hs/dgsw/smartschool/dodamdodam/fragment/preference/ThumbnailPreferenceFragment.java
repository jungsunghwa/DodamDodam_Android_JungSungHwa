package kr.hs.dgsw.smartschool.dodamdodam.fragment.preference;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.annimon.stream.Optional;

import java.util.Objects;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class ThumbnailPreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_thumbnail, rootKey);

        final String prefKeyThumbnail = getString(R.string.pref_key_thumbnail);
        final String prefKeyFrame = getString(R.string.pref_key_frame);
        final Preference prefThumbnail = getPreferenceManager().findPreference(prefKeyThumbnail);
        final Preference prefFrame = getPreferenceManager().findPreference(prefKeyFrame);
        SharedPreferences preferences = getPreferenceManager().getSharedPreferences();

        Optional.ofNullable(prefFrame).executeIfPresent(preference -> preference.setEnabled(Objects.equals(preferences.getString(prefKeyThumbnail, "low"), "frame")));
        Optional.ofNullable(prefThumbnail).executeIfPresent(preference -> preference.setOnPreferenceChangeListener((pref, newValue) -> {
            String value = String.valueOf(newValue);
            Optional.ofNullable(prefFrame).executeIfPresent(inner -> inner.setEnabled(value.equals("frame")));
            return true;
        }));
    }
}
