package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.scwang.wave.MultiWaveHeader;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class DodamMultiWaveHeader extends MultiWaveHeader {

    static List<DodamMultiWaveHeader> headers = new ArrayList<>();

    public DodamMultiWaveHeader(Context context) {
        this(context, null);
    }

    public DodamMultiWaveHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DodamMultiWaveHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        boolean wave = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getContext().getString(R.string.pref_key_wave_animation), true);
        if (wave) start();
        else stop();
    }

    @Override
    public void start() {
        boolean wave = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getContext().getString(R.string.pref_key_wave_animation), true);
        if (wave) super.start();
    }

    private void startIgnoreSetting() {
        super.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        headers.add(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        headers.remove(this);
    }

    public static void startHeaders() {
        for (DodamMultiWaveHeader header : headers) {
            header.startIgnoreSetting();
        }
    }

    public static void stopHeaders() {
        for (DodamMultiWaveHeader header : headers) {
            header.stop();
        }
    }
}