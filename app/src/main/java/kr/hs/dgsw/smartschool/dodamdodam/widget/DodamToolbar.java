package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;

import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Field;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class DodamToolbar extends Toolbar {

    public DodamToolbar(Context context) {
        super(context);
    }

    public DodamToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DodamToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        try {
            Class superClass = getClass().getSuperclass();
            if (superClass != null) {
                Field field = superClass.getDeclaredField("mTitleTextView");
                field.setAccessible(true);
                TextView textView = (TextView) field.get(this);
                if (textView == null) return;
                try {
                    textView.setTypeface(ResourcesCompat.getFont(getContext(), R.font.nanum_square_regular));
                } catch (Resources.NotFoundException ignore) {
                }
                textView.setLayoutParams(generateDefaultLayoutParams());
            }
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        super.setSubtitle(subtitle);
        try {
            Class superClass = getClass().getSuperclass();
            if (superClass != null) {
                Field field = superClass.getDeclaredField("mSubtitleTextView");
                field.setAccessible(true);
                TextView textView = (TextView) field.get(this);
                if (textView == null) return;
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.nanum_square_regular);
                textView.setTypeface(typeface);
                LayoutParams layoutParams = generateDefaultLayoutParams();
                layoutParams.setMarginStart((int) ViewUtils.dpToPx(getContext(), 24));
                textView.setLayoutParams(layoutParams);
            }
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        return layoutParams;
    }
}
