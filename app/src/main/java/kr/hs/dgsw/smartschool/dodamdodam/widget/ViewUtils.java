package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Dimension;

public final class ViewUtils {

    private ViewUtils() {
    }

    /*public static void marginSystemWindows(Window window) {
        window.getDecorView().setOnApplyWindowInsetsListener((v, insets) -> {
            Log.d(TAG, "marginSystemWindowsTop: " + insets.getSystemWindowInsetTop());
            Log.d(TAG, "marginSystemWindowsBottom: " + insets.getSystemWindowInsetBottom());
            v.setOnApplyWindowInsetsListener(null);
            return insets;
        });
    }*/

    public static void marginTopSystemWindow(Window window, View view) {
        window.getDecorView().setOnApplyWindowInsetsListener((v, insets) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams;
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            } else {
                marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
            }
            marginLayoutParams.topMargin = insets.getSystemWindowInsetTop();
            view.setLayoutParams(layoutParams);
            v.setOnApplyWindowInsetsListener(null);

            return insets;
        });
    }

    public static void marginBottomSystemWindow(Window window, View view) {
        window.getDecorView().setOnApplyWindowInsetsListener((v, insets) -> {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams;
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            } else {
                marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
            }
            marginLayoutParams.bottomMargin = insets.getSystemWindowInsetBottom();
            view.setLayoutParams(layoutParams);
            v.setOnApplyWindowInsetsListener(null);

            return insets;
        });
    }

    public static float dpToPx(Context context, @Dimension(unit = Dimension.DP) int dp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
