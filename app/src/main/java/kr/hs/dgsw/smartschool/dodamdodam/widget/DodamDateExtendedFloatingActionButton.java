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

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private Date currentDate;

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

        boolean isNight = getResources().getBoolean(R.bool.isNight);

        ColorStateList colorControlNormal = ColorStateList.valueOf(typedValue.data);
        ColorStateList white = ColorStateList.valueOf(Color.WHITE);

        ColorStateList background = isNight ? colorControlNormal : white;
        ColorStateList foreground = isNight ? white : colorControlNormal;

        setIconResource(R.drawable.ic_calendar);
        setIconTint(foreground);
        setRippleColor(foreground);
        setTextColor(foreground.getDefaultColor());
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        try {
            setTypeface(ResourcesCompat.getFont(getContext(), R.font.nanum_square_regular), Typeface.BOLD);
        } catch (Resources.NotFoundException e) {
            setTypeface(getTypeface(), Typeface.BOLD);
        }
        setBackgroundTintList(background);
        setText("");
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (currentDate == null) currentDate = new Date();
        String dateString = format.format(currentDate);
        Log.w(TAG, String.format("Ignore Text, replaced to date ('%s')", dateString));
        super.setText(dateString, type);
    }

    public void setCurrentDate(Date date) {
        currentDate.setTime(date.getTime());
        String dateString = format.format(currentDate);
        super.setText(dateString, BufferType.NORMAL);
    }

    public Date getCurrentDate() {
        return currentDate;
    }
}
