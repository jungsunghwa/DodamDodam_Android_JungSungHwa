package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.widget.viewpager.RegisterPagerAdapter;

public class RegisterActivity extends BaseActivity<RegisterActivityBinding>{

    private final int PAGE_COUNT = 2;

    @Override
    protected int layoutId() {
        return R.layout.register_activity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayoutNoLimits(false);

        RegisterPagerAdapter adapter = new RegisterPagerAdapter(getSupportFragmentManager());

        binding.registerViewPager.setOffscreenPageLimit(PAGE_COUNT);
        binding.registerViewPager.setAdapter(adapter);
    }
}
