package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Field;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.AppBarBinding;
import kr.hs.dgsw.smartschool.dodamdodam.widget.ViewUtils;

public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity {

    protected VB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = DataBindingUtil.setContentView(this, layoutId());
        ViewUtils.setOnApplyWindowInsetsListenerToWindow(getWindow());
        try {
            Field rootField = binding.getClass().getField("rootLayout");
            View rootView = (View) rootField.get(binding);

            try {
                Field appBarField = binding.getClass().getField("appbarLayout");
                AppBarBinding appBarBinding = (AppBarBinding) appBarField.get(binding);
                ViewUtils.marginTopSystemWindow(appBarBinding.toolbar);
                setSupportActionBar(appBarBinding.toolbar);

                appBarBinding.wave.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int flags = getWindow().getDecorView().getSystemUiVisibility();
                        if (appBarBinding.wave.getVisibility() == View.VISIBLE) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                flags ^= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                                getWindow().getDecorView().setSystemUiVisibility(flags);
                            } else
                                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                        }
                        appBarBinding.wave.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            } catch (NoSuchFieldException | IllegalAccessException e) {
                ViewUtils.marginTopSystemWindow(rootView);
            }

            ViewUtils.marginBottomSystemWindow(rootView);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @LayoutRes
    protected abstract int layoutId();
}
