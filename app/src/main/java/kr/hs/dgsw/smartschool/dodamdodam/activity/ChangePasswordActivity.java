package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.ChangePasswordBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LoginViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.StudentViewModel;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordBinding> {

    StudentViewModel studentViewModel;
    LoginViewModel loginViewModel;

    @Override
    protected int layoutId() {
        return R.layout.change_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();

        binding.changePasswordBtn.setOnClickListener(view -> {
            changePassword_check();


        });
    }

    private void changePassword_check() {

        boolean isError = false;
        if(binding.newPassword.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "새 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            isError = true;
        }

        else if(binding.newPasswordConfirm.getText().toString().isEmpty())
        {
            Toast.makeText(getApplicationContext(), "새 비밀번호를 한번 더 입력해주세요", Toast.LENGTH_SHORT).show();
            isError = true;
        }

        else if(!(binding.newPassword.getText().toString()).equals(binding.newPasswordConfirm.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "새 비밀번호가 동일하지 않습니다.", Toast.LENGTH_SHORT).show();
            isError = true;
        }

        else if(isError)
        {
            return;
        }
    }

    private void initViewModel() {
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        studentViewModel.getStudent();
    }
}
