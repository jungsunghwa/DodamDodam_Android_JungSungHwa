package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;

import java.util.Stack;

public final class ViewUtils {

    private static Stack<View.OnApplyWindowInsetsListener> listeners = new Stack<>();

    private ViewUtils() {
    }

    public static void setOnApplyWindowInsetsListenerToWindow(Window window) {
        window.getDecorView().setOnApplyWindowInsetsListener(((v, insets) -> {
            while (!listeners.empty()) {
                try {
                    listeners.pop().onApplyWindowInsets(v, insets);
                } catch (NullPointerException ignore) {
                }
            }

            return insets;
        }));
    }

    public static void marginSystemWindows(@NonNull Window window, @NonNull View topView, @NonNull View bottomView, @NonNull View leftView, @NonNull View rightView) {
        window.getDecorView().setOnApplyWindowInsetsListener((v, insets) -> {
            marginTopSystemWindow(topView, insets);
            marginBottomSystemWindow(bottomView, insets);
            marginLeftSystemWindow(leftView, insets);
            marginRightSystemWindow(rightView, insets);
            v.setOnApplyWindowInsetsListener(null);
            return insets;
        });
    }

    public static void paddingBottomSystemWindow(@NonNull final View view) {
        listeners.push((v, insets) -> {
            paddingBottomSystemWindow(view, insets);
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

    public static void marginLeftSystemWindow(@NonNull final View view) {
        listeners.push(((v, insets) -> {
            marginLeftSystemWindow(view, insets);
            return insets;
        }));
    }

    public static void marginRightSystemWindow(@NonNull final View view) {
        listeners.push(((v, insets) -> {
            marginRightSystemWindow(view, insets);
            return insets;
        }));
    }

    private static void paddingBottomSystemWindow(View view, WindowInsets insets) {
        view.setPaddingRelative(0, 0, 0, insets.getSystemWindowInsetBottom());
    }

    private static void marginTopSystemWindow(View view, WindowInsets insets) {
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

    private static void marginBottomSystemWindow(View view, WindowInsets insets) {
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

    private static void marginLeftSystemWindow(View view, WindowInsets insets) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        } else {
            marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        }
        marginLayoutParams.leftMargin += insets.getSystemWindowInsetLeft();
        view.setLayoutParams(layoutParams);
    }

    private static void marginRightSystemWindow(View view, WindowInsets insets) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        } else {
            marginLayoutParams = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        }
        marginLayoutParams.rightMargin += insets.getSystemWindowInsetRight();
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
