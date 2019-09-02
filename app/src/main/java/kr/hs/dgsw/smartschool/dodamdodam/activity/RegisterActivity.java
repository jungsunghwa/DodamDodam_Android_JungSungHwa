package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import kr.hs.dgsw.b1nd.service.retrofit2.response.register.StudentRegisterRequest;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterProfileFragmentBinding;
import kr.hs.dgsw.smartschool.dodamdodam.fragment.RegisterAccountFragment;
import kr.hs.dgsw.smartschool.dodamdodam.fragment.RegisterProfileFragement;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.RegisterViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.viewpager.RegisterPagerAdapter;

public class RegisterActivity extends BaseActivity<RegisterActivityBinding>{

    private RegisterViewModel registerViewModel;

    private final int PAGE_COUNT = 2;

    @Override
    protected int layoutId() {
        return R.layout.register_activity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayoutNoLimits(false);

        initViewModel();

        RegisterPagerAdapter adapter = new RegisterPagerAdapter(getSupportFragmentManager());

        binding.registerViewPager.setOffscreenPageLimit(PAGE_COUNT);
        binding.registerViewPager.setAdapter(adapter);

        adapter.registerAccountFragment.getRegisterId().observe(this, id -> {
            registerViewModel.studentRegisterRequest.setId(id);
            System.out.println(id);
        });
        adapter.registerAccountFragment.getRegisterPw().observe(this, pw -> {
            registerViewModel.studentRegisterRequest.setPw(pw);
            System.out.println(pw);
        });
        // todo viewpager 간 데이터 전달, 버튼으로 viewpager 넘김, register_profile_activity.xml 작업 필요
    }

    private void initViewModel() {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
    }

    public void pageMove(int pageNum) {
        binding.registerViewPager.setCurrentItem(pageNum);
    }
}
