package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import androidx.appcompat.widget.AppCompatSpinner;

import java.lang.reflect.Field;

public class VisibleScrollbarSpinner extends AppCompatSpinner {
    @Override
    public boolean performClick() {
        final boolean superResult =  super.performClick();

        try {
            final Field mPopupField = Spinner.class.getDeclaredField("mPopup");
            mPopupField.setAccessible(true);

            ((ListPopupWindow) mPopupField.get(this)).getListView().setScrollbarFadingEnabled(false);
            mPopupField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return superResult;
    }

    public VisibleScrollbarSpinner(Context context) {
        super(context);
    }

    public VisibleScrollbarSpinner(Context context, int mode) {
        super(context, mode);
    }

    public VisibleScrollbarSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VisibleScrollbarSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VisibleScrollbarSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public VisibleScrollbarSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }
}