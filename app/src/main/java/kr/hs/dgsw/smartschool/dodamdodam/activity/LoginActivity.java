package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Toast;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LoginActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LoginViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.StudentViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.InputMethodHelper;

public class LoginActivity extends BaseActivity<LoginActivityBinding> {

    private LoginViewModel loginViewModel;
    private StudentViewModel studentViewModel;

    private InputMethodHelper keyboardManager;

    @Override
    protected int layoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutNoLimits(false);

        keyboardManager = new InputMethodHelper(this);

        loginViewModel = new LoginViewModel(this);
        studentViewModel = new StudentViewModel(this);

        studentViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.progress.show();
            } else {
                binding.progress.hide();
            }
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

        binding.inputId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s))
                    binding.inputLayoutId.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s))
                    binding.inputLayoutPw.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.inputPw.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                login();
                return true;
            }

            return false;
        });
        binding.btnLogin.setOnClickListener(view -> login());
        binding.inputId.requestFocus();
    }

    private void login() {
        keyboardManager.closeSoftKeyboard();
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
