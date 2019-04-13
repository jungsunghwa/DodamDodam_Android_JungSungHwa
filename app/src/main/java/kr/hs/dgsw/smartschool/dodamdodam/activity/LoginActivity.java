package kr.hs.dgsw.smartschool.dodamdodam.activity;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LoginActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LoginViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends BaseActivity<LoginActivityBinding> {

    LoginViewModel loginViewModel;

    @Override
    protected int layoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = new LoginViewModel(this);

        loginViewModel.getLoading().observe(this, isLoading ->{
            if (!isLoading) {
                binding.progressLogin.setVisibility(View.GONE);
            }else {
                binding.progressLogin.setVisibility(View.VISIBLE);
            }
        });

        loginViewModel.getError().observe(this, error -> {
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show();
        });

        loginViewModel.getIsSuccess().observe( this, isSuccess ->{
            if (isSuccess){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        binding.loginBtn.setOnClickListener(view -> {
            if (binding.loginIdEt.getText().toString().isEmpty()){
                Toast.makeText(this,"아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            } else if (binding.loginPwEt.getText().toString().isEmpty()){
                Toast.makeText(this,"비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            loginViewModel.login(binding.loginIdEt.getText().toString(), binding.loginPwEt.getText().toString());
        });

    }
}
