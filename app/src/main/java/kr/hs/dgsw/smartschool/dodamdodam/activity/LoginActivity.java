package kr.hs.dgsw.smartschool.dodamdodam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LoginActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.Login;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LoginViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    LoginActivityBinding binding;

    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);

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
