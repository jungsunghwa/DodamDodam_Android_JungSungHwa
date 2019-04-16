package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextClock;

import java.util.Calendar;
import java.util.Locale;

public class DodamTextDate extends TextClock {
    public DodamTextDate(Context context) {
        super(context);
        setDefaultLocale();
    }

    public DodamTextDate(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultLocale();
    }

    public DodamTextDate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDefaultLocale();
    }

    public DodamTextDate(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setDefaultLocale();
    }

    private void setDefaultLocale() {
        String monthName = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);

        this.setFormat12Hour("yyyy-MM-dd  '" + monthName + "'");
        this.setFormat24Hour("yyyy-MM-dd  '" + monthName + "'");
    }
}
