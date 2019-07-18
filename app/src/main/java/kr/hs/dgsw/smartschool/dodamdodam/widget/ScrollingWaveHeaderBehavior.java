package kr.hs.dgsw.smartschool.dodamdodam.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.wave.MultiWaveHeader;

import org.jetbrains.annotations.NotNull;

public class ScrollingWaveHeaderBehavior extends CoordinatorLayout.Behavior<MultiWaveHeader> {

    private int toolbarHeight;

    public ScrollingWaveHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = ViewUtils.dpToPx(context, 96 * 3);
    }

    @Override
    public boolean layoutDependsOn(@NotNull CoordinatorLayout parent, @NotNull MultiWaveHeader multiWaveHeader, @NotNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NotNull CoordinatorLayout parent, @NotNull MultiWaveHeader multiWaveHeader, @NotNull View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) multiWaveHeader.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = multiWaveHeader.getHeight() + fabBottomMargin;
            float ratio = dependency.getY() / (float) toolbarHeight;
            multiWaveHeader.setTranslationY(distanceToScroll * ratio);
        }
        return true;
    }
}
