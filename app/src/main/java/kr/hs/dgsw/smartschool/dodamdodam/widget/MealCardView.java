package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.card.MaterialCardView;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class MealCardView extends MaterialCardView {

    private AppCompatTextView mealTypeView;
    private AppCompatTextView mealTextView;
    private View divider;
    private AppCompatTextView errorTextView;
    private ProgressBar progress;

    private CharSequence typeText;
    private CharSequence mealText;
    private CharSequence errorText;

    private boolean isError;
    private boolean isLoading;

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
        setMinimumWidth(ViewUtils.dpToPx(getContext(), 480));

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MealCardView);

        typeText = typedArray.getText(R.styleable.MealCardView_typeText);
        mealText = typedArray.getText(R.styleable.MealCardView_mealText);
        errorText = typedArray.getText(R.styleable.MealCardView_errorText);
        dividerColor = typedArray.getColor(R.styleable.MealCardView_dividerColor, ContextCompat.getColor(getContext(), R.color.mealTextColor));
        isLoading = typedArray.getBoolean(R.styleable.MealCardView_showLoading, false);
        isError = typedArray.getBoolean(R.styleable.MealCardView_showError, false);

        typedArray.recycle();

        setRadius(ViewUtils.dpToPx(getContext(), 20f));

        ConstraintLayout layout = new ConstraintLayout(getContext());
        layout.setMinimumHeight(ViewUtils.dpToPx(getContext(), 90));

        mealTypeView = new AppCompatTextView(getContext());
        mealTextView = new AppCompatTextView(getContext());
        divider = new View(getContext());
        errorTextView = new AppCompatTextView(getContext());
        progress = new ProgressBar(getContext());

        mealTypeView.setId(generateViewId());
        mealTextView.setId(generateViewId());
        errorTextView.setId(generateViewId());
        divider.setId(generateViewId());
        progress.setId(generateViewId());

        try {
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.nanum_square_round);
            mealTypeView.setTypeface(typeface);
            mealTextView.setTypeface(typeface);
            errorTextView.setTypeface(typeface);
        } catch (Resources.NotFoundException e) {
            mealTypeView.setTypeface(mealTypeView.getTypeface());
            mealTextView.setTypeface(mealTextView.getTypeface());
            errorTextView.setTypeface(errorTextView.getTypeface());
        }

        mealTypeView.setTextSize(20);
        mealTextView.setTextSize(16);

        errorTextView.setTextAppearance(getContext(), R.style.TextAppearance_MaterialComponents_Body1);

        mealTypeView.setTextColor(ContextCompat.getColor(getContext(), R.color.mealTextColor));
        mealTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.mealTextColor));
        divider.setBackgroundColor(dividerColor);

        mealTypeView.setText(typeText);
        mealTextView.setText(mealText);
        errorTextView.setText(errorText);

        errorTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0);

        errorTextView.setVisibility(isError ? VISIBLE : GONE);
        progress.setVisibility(isLoading ? VISIBLE : GONE);

        ConstraintLayout.LayoutParams mealTypeLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mealTypeLayoutParams.setMarginStart(ViewUtils.dpToPx(getContext(), 24));
        mealTypeLayoutParams.startToEnd = divider.getId();
        mealTypeLayoutParams.topToTop = divider.getId();

        ConstraintLayout.LayoutParams mealTextLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mealTextLayoutParams.setMarginStart(ViewUtils.dpToPx(getContext(), 24));
        mealTextLayoutParams.topMargin = (ViewUtils.dpToPx(getContext(), 16));
        mealTextLayoutParams.topToBottom = mealTypeView.getId();
        mealTextLayoutParams.bottomToBottom = divider.getId();
        mealTextLayoutParams.startToEnd = divider.getId();

        ConstraintLayout.LayoutParams dividerLayoutParams = new ConstraintLayout.LayoutParams(ViewUtils.dpToPx(getContext(), 5), ConstraintLayout.LayoutParams.MATCH_CONSTRAINT);
        dividerLayoutParams.topMargin = ViewUtils.dpToPx(getContext(), 18);
        dividerLayoutParams.bottomMargin = ViewUtils.dpToPx(getContext(), 18);
        dividerLayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        dividerLayoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        dividerLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;

        ConstraintLayout.LayoutParams progressParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        progressParams.topToBottom = mealTypeView.getId();
        progressParams.bottomToBottom = divider.getId();
        progressParams.startToEnd = divider.getId();
        progressParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;

        ConstraintLayout.LayoutParams errorTextParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        errorTextParams.topToBottom = mealTypeView.getId();
        errorTextParams.bottomToBottom = divider.getId();
        errorTextParams.startToEnd = divider.getId();
        errorTextParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;

        layout.addView(divider, dividerLayoutParams);
        layout.addView(mealTypeView, mealTypeLayoutParams);
        layout.addView(mealTextView, mealTextLayoutParams);
        layout.addView(errorTextView, errorTextParams);
        layout.addView(progress, progressParams);

        addView(layout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public CharSequence getMeal() {
        return mealText;
    }

    public void setMeal(CharSequence text) {
        mealText = text;
        mealTextView.setText(text);
    }

    public CharSequence getType() {
        return typeText;
    }

    public void setType(CharSequence text) {
        typeText = text;
        mealTypeView.setText(typeText);
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerColor(@ColorInt int dividerColor) {
        this.dividerColor = dividerColor;
        divider.setBackgroundColor(dividerColor);
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
        errorTextView.setVisibility(isError ? VISIBLE : GONE);
        if (isError)
            mealTextView.setText(null);
    }

    public void setError(CharSequence text) {
        errorText = text;
        errorTextView.setText(errorText);
        setError(!TextUtils.isEmpty(text));
    }

    public CharSequence getErrorText() {
        return errorText;
    }

    public void setError(boolean error, CharSequence text) {
        errorText = text;
        errorTextView.setText(errorText);
        setError(error);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        progress.setVisibility(isLoading ? VISIBLE : GONE);
        if (loading)
            setError(null);
    }
}
