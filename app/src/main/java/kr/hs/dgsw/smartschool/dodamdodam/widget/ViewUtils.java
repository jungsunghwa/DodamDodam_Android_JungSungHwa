package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Stack;

public final class ViewUtils {

    private static Stack<OnApplyWindowInsetsListener> listeners = new Stack<>();

    private ViewUtils() {
    }

    public static void setOnApplyWindowInsetsListenerToWindow(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(view, ((v, insets) -> {
            while (!listeners.empty()) {
                try {
                    listeners.pop().onApplyWindowInsets(v, insets);
                } catch (NullPointerException ignore) {
                }
            }

            return insets;
        }));
    }

    public static void marginSystemWindows(@NonNull View view, @NonNull View topView, @NonNull View bottomView) {
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            marginTopSystemWindow(topView, insets);
            marginBottomSystemWindow(bottomView, insets);
            ViewCompat.setOnApplyWindowInsetsListener(view, null);
            return insets;
        });
    }

    public static void marginTopSystemWindow(@NonNull final View view) {
        listeners.push((v, insets) -> {
            marginTopSystemWindow(view, insets);
            return insets;
        });
    }

    public static void marginBottomSystemWindow(@NonNull final View view) {
        listeners.push(((v, insets) -> {
            marginBottomSystemWindow(view, insets);
            return insets;
        }));
    }

    private static void marginTopSystemWindow(View view, WindowInsetsCompat insets) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        } else {
            marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        }
        marginLayoutParams.topMargin += insets.getSystemWindowInsetTop();
        view.setLayoutParams(layoutParams);
    }

    private static void marginBottomSystemWindow(View view, WindowInsetsCompat insets) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        } else {
            marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        }
        marginLayoutParams.bottomMargin += insets.getSystemWindowInsetBottom();
        view.setLayoutParams(layoutParams);
    }

    public static float dpToPx(Context context, @Dimension(unit = Dimension.DP) float dp) {
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static int dpToPx(Context context, @Dimension(unit = Dimension.DP) int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
