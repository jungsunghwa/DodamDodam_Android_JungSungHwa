package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextClock;

import androidx.core.content.res.ResourcesCompat;

import java.util.Calendar;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class DodamTextDate extends TextClock {
    public DodamTextDate(Context context) {
        super(context);
        setDefaultLocale();
    }

    public DodamTextDate(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DodamTextDate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDefaultLocale();
        try {
            setTypeface(ResourcesCompat.getFont(getContext(), R.font.nanum_square_regular), Typeface.BOLD);
        } catch (Resources.NotFoundException e) {
            setTypeface(getTypeface(), Typeface.BOLD);
        }
        setIncludeFontPadding(false);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    private void setDefaultLocale() {
        String monthName = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);

        this.setFormat12Hour("yyyy-MM-dd  '" + monthName + "'");
        this.setFormat24Hour("yyyy-MM-dd  '" + monthName + "'");
    }
}
