package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.ChangePasswordActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LoginViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.MemberViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.StudentViewModel;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordActivityBinding> {

    private MemberViewModel memberViewModel;

    @Override
    protected int layoutId() {
        return R.layout.change_password_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setLayoutNoLimits(false);
        initViewModel();

        observeMemberViewModel();

        clickEvent();
    }

    private void observeMemberViewModel() {
        memberViewModel.getSuccessMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            finish();
        });

        memberViewModel.getErrorMessage().observe(this, message -> {
            Toast.makeText(this, message ,Toast.LENGTH_SHORT).show();
        });
    }

    private boolean changePasswordCheck() {
        if(binding.newPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "새 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(binding.newPasswordConfirm.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "새 비밀번호를 한번 더 입력해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(!(binding.newPassword.getText().toString()).equals(binding.newPasswordConfirm.getText().toString())) {
            Toast.makeText(getApplicationContext(), "새 비밀번호가 동일하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setRequest() {
        memberViewModel.request.setPassword(binding.newPassword.getText().toString());
    }

    private void clickEvent() {
        binding.changePasswordBtn.setOnClickListener(v -> {
            if (changePasswordCheck()) {
                setRequest();
                memberViewModel.change();
            }
        });
    }

    private void initViewModel() {
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
