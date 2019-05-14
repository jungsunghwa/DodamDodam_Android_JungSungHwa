package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Toast;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Identity;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.Utils;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LoginActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LoginViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.StudentViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.SoftKeyboardManager;

public class LoginActivity extends BaseActivity<LoginActivityBinding> {

    private LoginViewModel loginViewModel;
    private StudentViewModel studentViewModel;

    private SoftKeyboardManager keyboardManager;

    @Override
    protected int layoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        keyboardManager = new SoftKeyboardManager(this);
        super.onCreate(savedInstanceState);

        loginViewModel = new LoginViewModel(this);
        studentViewModel = new StudentViewModel(this);

        studentViewModel.getLoading().observe(this, isLoading -> {
            if (!isLoading) {
                binding.progress.hide();
            } else {
                binding.progress.show();
            }
        });

        loginViewModel.getError().observe(this, error -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        loginViewModel.isSuccess().observe(this, success -> {
            if (success) {
                studentViewModel.getClasses();
                studentViewModel.getStudent();
            } else {
                binding.progress.show();
            }
        });

        studentViewModel.getIsSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                if (Utils.identity == Identity.TEACHER)
                    intent = new Intent(getApplicationContext(), LocationCheckActivity.class);

                binding.progress.hide();

                startActivity(intent);
                overridePendingTransition(0, R.anim.fade_out);
                finish();
            }
        });

        binding.textInputId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s))
                    binding.textInputLayoutId.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.textInputPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s))
                    binding.textInputLayoutPw.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.textInputPw.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                login();
                return true;
            }

            return false;
        });
        binding.btnLogin.setOnClickListener(view -> login());
    }

    private void login() {
        keyboardManager.closeSoftKeyboard();
        boolean hasError = false;
        if (binding.textInputId.getText().toString().isEmpty()) {
            binding.textInputLayoutId.setError(getString(R.string.error_empty_id));
            hasError = true;
        }
        if (binding.textInputPw.getText().toString().isEmpty()) {
            binding.textInputLayoutPw.setError(getString(R.string.error_empty_pw));
            hasError = true;
        }
        if (hasError) return;

        loginViewModel.login(binding.textInputId.getText().toString(), binding.textInputPw.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardManager.unregisterSoftKeyboardCallback();
    }
}
