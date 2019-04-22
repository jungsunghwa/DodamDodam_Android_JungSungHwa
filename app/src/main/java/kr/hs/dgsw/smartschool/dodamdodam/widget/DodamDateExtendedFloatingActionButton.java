package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class DodamDateExtendedFloatingActionButton extends ExtendedFloatingActionButton {

    private static final String TAG = "DodamDateExtendedFAB";

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public DodamDateExtendedFloatingActionButton(Context context) {
        this(context, null);
    }

    public DodamDateExtendedFloatingActionButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon);
    }

    public DodamDateExtendedFloatingActionButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        TypedValue typedValue = new TypedValue();

        getContext().getTheme().resolveAttribute(R.attr.colorControlNormal, typedValue, true);

        setIconResource(R.drawable.ic_calendar);
        setIconTintResource(typedValue.resourceId);
        setRippleColor(ColorStateList.valueOf(typedValue.data));
        setTextColor(typedValue.data);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        try {
            setTypeface(ResourcesCompat.getFont(getContext(), R.font.nanum_square_regular), Typeface.BOLD);
        } catch (Resources.NotFoundException e) {
            setTypeface(getTypeface(), Typeface.BOLD);
        }
        setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        setText("");
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.w(TAG, String.format("Ignore Text, replaced to date (%s)", date));
        super.setText(date, type);
    }


}
