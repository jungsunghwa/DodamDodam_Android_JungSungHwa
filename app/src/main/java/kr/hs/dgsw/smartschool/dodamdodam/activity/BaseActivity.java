package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Field;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.AppBarBinding;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.AppBarTabBinding;
import kr.hs.dgsw.smartschool.dodamdodam.widget.ViewUtils;

/**
 * @param <VB> ViewDataBinding 을 상속 받는 View 바인딩 클래스
 * @apiNote Layout 구조
 * <p>
 * ! ->  필요할 때
 * !! ->  필수
 * <p>
 *
 * <layout> <!-- !!For DataBinding -->
 * <SomeLayout
 * android:fitsSystemWindows="true"> <!-- !BaseLayout --> <!-- System UI 와 Navigation Bar 와 겹쳐도 문제 없는 View 를 위한 Layout. -->
 * <SomeView
 * android:id="@+id/background"
 * android:fitsSystemWindows="true"/> <!-- !배경 (ex. MultiWaveHeader {@link com.scwang.wave.MultiWaveHeader}) -->
 * <include
 * android:id="@+id/app_bar_layout"
 * layout="@layout/app_bar"/> <!-- !AppBar --> <!-- ID 를 'app_bar_layout' 로 설정할 경우 자동으로 System UI 와의 Margin 이 설정 됨. -->
 * <SomeAnotherLayout
 * android:id="@+id/root_layout"
 * android:layout_marginTop="88dp"> <!-- !!Root Layout --> <!-- System UI 와 Navigation Bar 와의 Margin 을 위해 'root_layout' ID 필요. 그렇지 않으면 System UI 또는 Navigation Bar 와 View 가 겹쳐보일 것. -->
 * <!-- Inner View... -->
 * </SomeAnotherLayout>
 * </SomeLayout>
 * </layout>
 *
 *
 * @author kimji
 *
 * binding 필드와 기본 레이아웃 설정이 들어 있는 기반 액티비티
 */
public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity {

    protected VB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getClass().getName().equals(LoginActivity.class.getName()))
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
            } catch (ClassCastException e) {
                try {
                    Field appBarField = binding.getClass().getField("appbarLayout");
                    AppBarTabBinding appBarBinding = (AppBarTabBinding) appBarField.get(binding);
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
                } catch (NoSuchFieldException | IllegalAccessException e2) {
                    ViewUtils.marginTopSystemWindow(rootView);
                }
            }

            try {
                Field containerField = binding.getClass().getField("container");
                ViewGroup viewGroup = (ViewGroup) containerField.get(binding);
                ViewUtils.marginBottomSystemWindow(viewGroup);
            } catch (NoSuchFieldException | IllegalStateException e) {
                ViewUtils.marginBottomSystemWindow(rootView);
            }

        } catch (NoSuchFieldException | IllegalAccessException | NullPointerException ignore) {
        }
        if (isTablet()) onCreateTablet(savedInstanceState);
        else onCreatePhone(savedInstanceState);
    }

    /**
     * onCreate for Phone
     */
    protected void onCreatePhone(@Nullable Bundle savedInstanceState) {

    }

    /**
     * onCreate for Tablet
     */
    protected void onCreateTablet(@Nullable Bundle savedInstanceState) {

    }

    @LayoutRes
    protected abstract int layoutId();

    /**
     * Checking current device is Tablet.
     *
     * @return Current device is Tablet (or Size like Tablet), is true. Or, result is false.
     */
    protected final boolean isTablet() {
        return getSystemProperty("ro.build.characteristics").equals("tablet") || getResources().getBoolean(R.bool.isTablet);
    }

    @SuppressLint("PrivateApi")
    protected final String getSystemProperty(@NonNull String key) {
        String value = null;

        try {
            value = (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
}
