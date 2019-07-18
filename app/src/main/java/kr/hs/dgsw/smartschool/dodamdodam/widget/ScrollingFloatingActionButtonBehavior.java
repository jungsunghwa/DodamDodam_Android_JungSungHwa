package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class ScrollingFloatingActionButtonBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    private int toolbarHeight;

    public ScrollingFloatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = ViewUtils.dpToPx(context, 56);
    }

    @Override
    public boolean layoutDependsOn(@NotNull CoordinatorLayout parent, @NotNull FloatingActionButton floatingActionButton, @NotNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NotNull CoordinatorLayout parent, @NotNull FloatingActionButton floatingActionButton, @NotNull View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = floatingActionButton.getHeight() + fabBottomMargin;
            float ratio = dependency.getY() / (float) toolbarHeight;
            floatingActionButton.setTranslationY(-distanceToScroll * ratio);
        }
        return true;
    }
}
