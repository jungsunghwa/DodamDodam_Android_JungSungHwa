package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.card.MaterialCardView;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class MealCardView extends MaterialCardView {

    private LinearLayoutCompat errorLayout;

    private AppCompatTextView mealTypeView;
    private AppCompatTextView mealTextView;
    private View divider;
    private AppCompatImageView errorImage;
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

        errorLayout = new LinearLayoutCompat(getContext());
        errorLayout.setId(generateViewId());
        errorLayout.setOrientation(LinearLayoutCompat.HORIZONTAL);
        errorLayout.setGravity(Gravity.CENTER);
        errorLayout.setVisibility(isError ? VISIBLE : GONE);

        mealTypeView = new AppCompatTextView(getContext());
        mealTextView = new AppCompatTextView(getContext());
        divider = new View(getContext());
        errorImage = new AppCompatImageView(getContext());
        errorTextView = new AppCompatTextView(getContext());
        progress = new ProgressBar(getContext());

        mealTypeView.setId(generateViewId());
        mealTextView.setId(generateViewId());
        errorTextView.setId(generateViewId());
        divider.setId(generateViewId());
        errorImage.setId(generateViewId());
        progress.setId(generateViewId());

        try {
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.nanum_square_round);
            mealTypeView.setTypeface(typeface);
            mealTextView.setTypeface(typeface);
        } catch (Resources.NotFoundException e) {
            mealTypeView.setTypeface(mealTypeView.getTypeface());
            mealTextView.setTypeface(mealTextView.getTypeface());
        }

        mealTypeView.setTextSize(20);
        mealTextView.setTextSize(16);

        errorTextView.setTextAppearance(getContext(), R.style.TextAppearance_MaterialComponents_Body1);

        mealTypeView.setTextColor(ContextCompat.getColor(getContext(), R.color.mealTextColor));
        mealTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.mealTextColor));
        divider.setBackgroundColor(dividerColor);

        mealTypeView.setText(typeText);
        mealTextView.setText(mealText);

        errorImage.setImageResource(R.drawable.ic_warning);

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

        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topToBottom = mealTypeView.getId();
        params.bottomToBottom = divider.getId();
        params.startToEnd = divider.getId();
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;

        LinearLayoutCompat.LayoutParams errorTextViewParam = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        errorTextViewParam.setMarginStart(ViewUtils.dpToPx(getContext(), 8));
        
        errorLayout.addView(errorImage);
        errorLayout.addView(errorTextView, errorTextViewParam);

        layout.addView(divider, dividerLayoutParams);
        layout.addView(mealTypeView, mealTypeLayoutParams);
        layout.addView(mealTextView, mealTextLayoutParams);
        layout.addView(errorLayout, params);
        layout.addView(progress, params);

        addView(layout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setMeal(CharSequence text) {
        mealText = text;
        mealTextView.setText(text);
    }

    public CharSequence getMeal() {
        return mealText;
    }

    public void setType(CharSequence text) {
        typeText = text;
        mealTypeView.setText(typeText);
    }

    public CharSequence getType() {
        return typeText;
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

    public CharSequence getErrorText() {
        return errorText;
    }

    public void setError(boolean error) {
        isError = error;
        errorLayout.setVisibility(isError ? VISIBLE : GONE);
    }

    public void setError(CharSequence text) {
        errorText = text;
        isError = !TextUtils.isEmpty(text);
        errorLayout.setVisibility(isError ? VISIBLE : GONE);
        errorTextView.setText(errorText);
    }

    public void setError(boolean error, CharSequence text) {
        isError = error;
        errorText = text;
        errorLayout.setVisibility(isError ? VISIBLE : GONE);
        errorTextView.setText(errorText);
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
