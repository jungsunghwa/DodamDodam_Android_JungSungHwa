package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LoginActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LoginViewModel;

public class LoginActivity extends BaseActivity<LoginActivityBinding> {

    LoginViewModel loginViewModel;

    @Override
    protected int layoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        loginViewModel = new LoginViewModel(this);

        loginViewModel.getLoading().observe(this, isLoading -> {
            if (!isLoading) {
                binding.progress.setVisibility(View.GONE);
            } else {
                binding.progress.setVisibility(View.VISIBLE);
            }
        });

        loginViewModel.getError().observe(this, error -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        loginViewModel.getIsSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
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
        binding.btnLogin.setOnClickListener(view -> {
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
        });
        binding.background.setFactory(() -> {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT));
            return imageView;
        });
        binding.background.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        binding.background.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
        binding.background.setImageResource(R.drawable.back_12);
    }
}
