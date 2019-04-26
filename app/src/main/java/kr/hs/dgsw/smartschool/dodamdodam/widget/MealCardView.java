package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.card.MaterialCardView;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class MealCardView extends MaterialCardView {

    private AppCompatTextView mealText;

    private CharSequence typeText;
    @ColorInt
    private int dividerColor;

    public MealCardView(Context context) {
        this(context, null);
    }

    public MealCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MealCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MealCardView);

        typeText = typedArray.getText(R.styleable.MealCardView_typeText);
        dividerColor = typedArray.getColor(R.styleable.MealCardView_dividerColor, ContextCompat.getColor(getContext(), R.color.mealTextColor));

        setRadius(ViewUtils.dpToPx(getContext(), 20));

        ConstraintLayout layout = new ConstraintLayout(getContext());
        layout.setMinimumHeight((int) ViewUtils.dpToPx(getContext(), 90));

        AppCompatTextView mealType = new AppCompatTextView(getContext());
        mealText = new AppCompatTextView(getContext());
        mealText.setText(typedArray.getText(R.styleable.MealCardView_mealText));
        View divider = new View(getContext());

        typedArray.recycle();

        mealType.setId(R.id.meal_type);
        mealText.setId(R.id.meal_text);
        divider.setId(R.id.divider);

        try {
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.nanum_square_round);
            mealType.setTypeface(typeface);
            mealText.setTypeface(typeface);
        } catch (Resources.NotFoundException e) {
            mealType.setTypeface(mealType.getTypeface());
            mealText.setTypeface(mealText.getTypeface());
        }

        mealType.setTextSize(20);
        mealText.setTextSize(16);

        mealType.setTextColor(ContextCompat.getColor(getContext(), R.color.mealTextColor));
        mealText.setTextColor(ContextCompat.getColor(getContext(), R.color.mealTextColor));
        divider.setBackgroundColor(dividerColor);

        mealType.setText(typeText);

        ConstraintLayout.LayoutParams mealTypeLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mealTypeLayoutParams.setMarginStart((int) ViewUtils.dpToPx(getContext(), 24));
        mealTypeLayoutParams.startToEnd = divider.getId();
        mealTypeLayoutParams.topToTop = divider.getId();

        ConstraintLayout.LayoutParams mealTextLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mealTextLayoutParams.setMarginStart((int) ViewUtils.dpToPx(getContext(), 24));
        mealTextLayoutParams.topMargin = ((int) ViewUtils.dpToPx(getContext(), 16));
        mealTextLayoutParams.topToBottom = mealType.getId();
        mealTextLayoutParams.bottomToBottom = divider.getId();
        mealTextLayoutParams.startToEnd = divider.getId();

        ConstraintLayout.LayoutParams dividerLayoutParams = new ConstraintLayout.LayoutParams((int) ViewUtils.dpToPx(getContext(), 5), ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
        dividerLayoutParams.topMargin = (int) ViewUtils.dpToPx(getContext(), 18);
        dividerLayoutParams.bottomMargin = (int) ViewUtils.dpToPx(getContext(), 18);
        dividerLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        dividerLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        dividerLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;

        layout.addView(divider, dividerLayoutParams);
        layout.addView(mealType, mealTypeLayoutParams);
        layout.addView(mealText, mealTextLayoutParams);


        addView(layout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setMeal(CharSequence text) {
        mealText.setText(text);
    }
}
