package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.AppBarBinding;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.AppBarTabBinding;
import kr.hs.dgsw.smartschool.dodamdodam.widget.ViewUtils;

/**
 * @param <VB> ViewDataBinding 을 상속 받는 View 바인딩 클래스
 * @author kimji
 * <p>
 * binding 필드와 기본 레이아웃 설정이 들어 있는 기반 액티비티
 * @apiNote Layout 구조
 * </p>
 * <p>
 * ! ->  필요할 때
 * !! ->  필수
 * </p>
 *
 * <layout> <!-- !!For DataBinding -->
 * <SomeLayout
 * android:fitsSystemWindows="true"> <!-- !BaseLayout --> <!-- System UI 와 Navigation Bar 와 겹쳐도 문제 없는 View 를 위한 Layout. -->
 * <SomeView
 * android:id="@+id/background"
 * android:fitsSystemWindows="true"/> <!-- !배경 (ex. MultiWaveHeader {@link kr.hs.dgsw.smartschool.dodamdodam.widget.DodamMultiWaveHeader}) -->
 * <include
 * android:id="@+id/app_bar_layout"
 * layout="@layout/app_bar"/> <!-- !AppBar --> <!-- ID 를 'app_bar_layout' 로 설정할 경우 자동으로 System UI 와의 Margin 이 설정 됨. -->
 * <SomeInnerLayout
 * android:id="@+id/root_layout"
 * android:layout_marginTop="88dp"> <!-- !!Root Layout --> <!-- System UI 와 Navigation Bar 와의 Margin 을 위해 'root_layout' ID 필요. 그렇지 않으면 System UI 또는 Navigation Bar 와 View 가 겹쳐보일 것. -->
 * <!-- Inner View... -->
 * </SomeInnerLayout>
 * </SomeLayout>
 * </layout>
 */
public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity {

    protected VB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutNoLimits(true);
        binding = DataBindingUtil.setContentView(this, layoutId());
        ViewUtils.setOnApplyWindowInsetsListenerToWindow(getWindow());
        try {
            Field rootField = binding.getClass().getField("rootLayout");
            View rootView = (View) rootField.get(binding);

            try {
                Field appBarField = binding.getClass().getField("appbarLayout");
                AppBarBinding appBarBinding = (AppBarBinding) appBarField.get(binding);
                ViewUtils.marginTopSystemWindow(appBarBinding.toolbar);
                ViewUtils.marginLeftSystemWindow(appBarBinding.toolbar);
                ViewUtils.marginRightSystemWindow(appBarBinding.toolbar);
                setSupportActionBar(appBarBinding.toolbar);

                appBarBinding.wave.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (appBarBinding.wave.getVisibility() == View.VISIBLE) {
                            lightNavMode();
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
                            if (appBarBinding.wave.getVisibility() == View.VISIBLE) {
                                lightNavMode();
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
     * 네비게이션 바를 밝음(버튼 어둡게)으로 설정
     */
    protected void lightNavMode() {
        int flags = getWindow().getDecorView().getSystemUiVisibility();
        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        /*
            버전이 Oreo 미만 또는
            야간 모드일때, 네비바를 밝게 설정하지 않음
         */
        if (mode != Configuration.UI_MODE_NIGHT_YES)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                getWindow().getDecorView().setSystemUiVisibility(flags);
            } else
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 네비게이션 바를 어두움(버튼 밝게)으로 설정
     */
    protected void darkNavMode() {
        int flags = getWindow().getDecorView().getSystemUiVisibility();
        /*
            버전이 Oreo 미만일때, 네비바를 어둡게 설정하지 않음
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);
        } else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 상태바를 밝음(아이콘 어둡게)으로 설정
     */
    protected void lightStatusMode() {
        int flags = getWindow().getDecorView().getSystemUiVisibility();
        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        /*
            버전이 Marshmallow 미만 또는
            야간 모드일때, 상태바를 밝게 설정하지 않음
         */
        if (mode != Configuration.UI_MODE_NIGHT_YES)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                getWindow().getDecorView().setSystemUiVisibility(flags);
            } else
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 상태바를 어두움(아이콘 밝게)으로 설정
     */
    protected void darkStatusMode() {
        int flags = getWindow().getDecorView().getSystemUiVisibility();
        /*
            버전이 Marshmallow 미만일때, 상태바를 어둡게 설정하지 않음
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);
        } else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 폰 용 onCreate
     * <p>
     * 폰과 태블릿의 작업이 다를 경우 사용
     * 단독 또는 같을 경우 사용하지 않아도 됨
     */
    protected void onCreatePhone(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 태블릿 용 onCreate
     * <p>
     * 폰과 태블릿의 작업이 다를 경우 사용
     * 단독 또는 같을 경우 사용하지 않아도 됨
     */
    protected void onCreateTablet(@Nullable Bundle savedInstanceState) {

    }

    /**
     * setContentView 를 Base 에서 작업하기 위해 필요
     *
     * @return 설정할 레이아웃 ID
     */
    @LayoutRes
    protected abstract int layoutId();

    /**
     * 새 액티비티들을 시작 후 현재 액티비티는 종료
     *
     * @param activityClass 실행할 액티비티의 class 들
     */
    @SafeVarargs
    public final void startActivitiesWithFinish(Class<? extends Activity>... activityClass) {
        List<Intent> intents = new ArrayList<>();
        for (Class<? extends Activity> clazz : activityClass) {
            intents.add(new Intent(this, clazz));
        }
        startActivities(intents.toArray(new Intent[]{}));
        finish();
    }

    /**
     * 새 액티비티를 시작 후 현재 액티비티는 종료
     *
     * @param activityClass 실행할 액티비티의 class
     */
    public void startActivityWithFinish(Class<? extends Activity> activityClass) {
        startActivity(new Intent(this, activityClass));
        finish();
    }

    /**
     * LAYOUT_NO_LIMITS 설정, 기본적으로 사용 됨
     *
     * @param enable LAYOUT_NO_LIMITS 의 사용 여부
     */
    protected final void setLayoutNoLimits(boolean enable) {
        if (enable)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        else
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 현재 장비가 태블릿인지 확인
     * 크기에 따라 정의되어 있는 bool 값이 true 또는 build.prop 에 정의되어 있는 characteristics 가 tablet 인지를 확인
     *
     * @return 태블릿 또는 그것에 해당되는 크기일 경우: true, 아니면 false
     */
    protected final boolean isTablet() {
        return getSystemProperty("ro.build.characteristics").equals("tablet") || getResources().getBoolean(R.bool.isTablet);
    }

    /**
     * build.prop 의 값을 시스템 내부 클래스를 호출하여 가져옴
     *
     * @param key 가져올 값의 키 이름 (EX. "ro.product.device"
     * @return 키에 설정되있는 값, 키가 없으면 null (EX. "OnePlus3T"
     */
    @SuppressLint("PrivateApi")
    protected final String getSystemProperty(@NonNull String key) {
        String value = null;
        try {
            value = (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, key);
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException ignore) {
        }

        return value;
    }


    /**
     * 지원하지 않는 내용을 표시하는 Toast 를 띄움
     */
    public void notSupportToast() {
        Toast.makeText(this, "아직 지원하지 않는 기능입니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //binding 을 null 로 설정하여 누수되지 않고 GC 되도록 함
        binding = null;
    }
}
