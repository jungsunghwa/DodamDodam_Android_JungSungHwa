package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.widget.Toast;

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
    private RegisterPagerAdapter adapter;

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
        initViewPager();

        observeRegisterViewPager();
        observeRegisterViewModel();
    }

    private void initViewPager() {
        adapter = new RegisterPagerAdapter(getSupportFragmentManager());

        binding.registerViewPager.setOffscreenPageLimit(PAGE_COUNT);
        binding.registerViewPager.setAdapter(adapter);
    }

    private void initViewModel() {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
    }

    private void observeRegisterViewModel() {
        registerViewModel.getSuccessMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            startActivityWithFinish(LoginActivity.class);
        });

        registerViewModel.getErrorMessage().observe(this, message -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    private void observeRegisterViewPager() {
        adapter.registerAccountFragment.getRegisterId().observe(this, id -> registerViewModel.studentRegisterRequest.setId(id));
        adapter.registerAccountFragment.getRegisterPw().observe(this, pw -> registerViewModel.studentRegisterRequest.setPw(pw));
        adapter.registerProfileFragement.getRegisterStudentInfo().observe(this, studentInfo -> registerViewModel.studentRegisterRequest.setStudent(studentInfo));
        adapter.registerProfileFragement.getRegisterName().observe(this, name -> registerViewModel.studentRegisterRequest.setName(name));
        adapter.registerProfileFragement.getRegisterPhone().observe(this, phone -> registerViewModel.studentRegisterRequest.setMobile(phone));
        adapter.registerProfileFragement.getRegisterEmail().observe(this, email -> registerViewModel.studentRegisterRequest.setEmail(email));
        adapter.registerProfileFragement.getRegister().observe(this, check -> {
            if (check) {
                registerViewModel.studentRegister();
            }
        });
    }

    public void pageMove(int pageNum) {
        binding.registerViewPager.setCurrentItem(pageNum);
    }
}
