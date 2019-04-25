package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextClock;

import androidx.core.content.res.ResourcesCompat;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class DodamTextClock extends TextClock {
    public DodamTextClock(Context context) {
        super(context);
        setFormats();
    }

    public DodamTextClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DodamTextClock(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DodamTextClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFormats();
        try {
            setTypeface(ResourcesCompat.getFont(getContext(), R.font.gotham_thin), Typeface.BOLD);
        } catch (Resources.NotFoundException e) {
            setTypeface(getTypeface(), Typeface.BOLD);
        }
        setLetterSpacing(.35f);
        setIncludeFontPadding(false);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    private void setFormats() {
        this.setFormat12Hour("kk:mm:ss");
        this.setFormat24Hour("hh:mm:ss");
    }
}
