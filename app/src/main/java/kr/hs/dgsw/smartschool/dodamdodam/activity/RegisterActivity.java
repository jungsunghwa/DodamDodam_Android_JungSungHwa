package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import kr.hs.dgsw.b1nd.service.retrofit2.response.register.StudentRegisterRequest;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterProfileFragmentBinding;
import kr.hs.dgsw.smartschool.dodamdodam.fragment.RegisterAccountFragment;
import kr.hs.dgsw.smartschool.dodamdodam.fragment.RegisterProfileFragement;
import kr.hs.dgsw.smartschool.dodamdodam.widget.viewpager.RegisterPagerAdapter;

public class RegisterActivity extends BaseActivity<RegisterActivityBinding>{

    private final int PAGE_COUNT = 2;

    RegisterAccountFragment registerAccountFragment;
    RegisterProfileFragement registerProfileFragement;
    private String id = "";
    private String pw = "";

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
        // todo viewpager 간 데이터 전달, 버튼으로 viewpager 넘김, register_profile_activity.xml 작업 필요
    }

    public void pageMove(int pageNum) {
        binding.registerViewPager.setCurrentItem(pageNum);
    }
}
