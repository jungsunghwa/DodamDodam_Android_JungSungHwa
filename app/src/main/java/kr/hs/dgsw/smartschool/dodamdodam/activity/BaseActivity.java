package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
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
        try {
            Field field = binding.getClass().getField("appbarLayout");
            AppBarBinding appBarBinding = (AppBarBinding) field.get(binding);
            ViewUtils.marginTopSystemWindow(getWindow(), appBarBinding.toolbar);
        } catch (NoSuchFieldException | IllegalAccessException ignore) {
        }
    }

    @LayoutRes
    protected abstract int layoutId();
}
