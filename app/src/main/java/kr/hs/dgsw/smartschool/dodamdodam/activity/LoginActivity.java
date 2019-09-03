package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LoginActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.text.SimpleTextWatcher;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LoginViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.StudentViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.InputMethodHelper;

public class LoginActivity extends BaseActivity<LoginActivityBinding> {

    private LoginViewModel loginViewModel;
    private StudentViewModel studentViewModel;
    private LocationViewModel locationViewModel;


    @Override
    protected int layoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutNoLimits(false);
        lightNavMode();


        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);

        studentViewModel.getLoading().observe(this, isLoading -> {
//            if (isLoading) {
//                binding.progress.setVisibility(View.VISIBLE);
//            } else {
//                binding.progress.setVisibility(View.GONE);
//            }
        });

        loginViewModel.getError().observe(this, error -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        loginViewModel.isSuccess().observe(this, success -> {
            if (success) {
                studentViewModel.getClasses();
                studentViewModel.getStudent();
            }
        });

        studentViewModel.getIsSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                overridePendingTransition(0, R.anim.fade_out);
                finish();
            }
        });

        binding.inputId.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s))
                    binding.inputLayoutId.setError(null);
            }
        });
        binding.inputPw.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s))
                    binding.inputLayoutPw.setError(null);
            }
        });
        binding.inputPw.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                login();
                return true;
            }

            return false;
        });
        binding.goSignupText.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
        binding.btnLogin.setOnClickListener(view -> login());
        binding.inputId.requestFocus();
    }

    private void login() {
//        keyboardManager.closeSoftKeyboard();
        boolean hasError = false;
        if (binding.inputId.getText().toString().isEmpty()) {
            binding.inputLayoutId.setError(getString(R.string.error_empty_id));
            hasError = true;
        }
        if (binding.inputPw.getText().toString().isEmpty()) {
            binding.inputLayoutPw.setError(getString(R.string.error_empty_pw));
            hasError = true;
        }
        if (hasError) return;

        loginViewModel.login(binding.inputId.getText().toString(), binding.inputPw.getText().toString());
    }
}
