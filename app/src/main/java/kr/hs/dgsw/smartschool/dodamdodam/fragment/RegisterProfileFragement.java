package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import kr.hs.dgsw.b1nd.service.model.StudentInfo;
import kr.hs.dgsw.b1nd.service.retrofit2.response.register.StudentRegisterRequest;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.LoginActivity;
import kr.hs.dgsw.smartschool.dodamdodam.activity.RegisterActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterProfileFragmentBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.RegisterViewModel;
import retrofit2.http.POST;

public class RegisterProfileFragement extends BaseFragment<RegisterProfileFragmentBinding> {

    private MutableLiveData<StudentInfo> registerStudentInfo = new MutableLiveData<>();
    private MutableLiveData<String> registerName = new MutableLiveData<>();
    private MutableLiveData<String> registerPhone = new MutableLiveData<>();
    private MutableLiveData<String> registerEmail = new MutableLiveData<>();
    private MutableLiveData<Boolean> register = new MutableLiveData<>();
    InputMethodManager inputMethodManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        inputMethodManager= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.finLayout.setOnClickListener(v -> {
            if (checkValue()) {
                setRequest();
            }
        });

        binding.backLayout.setOnClickListener(v -> {
            RegisterActivity registerActivity = (RegisterActivity) RegisterProfileFragement.this.getActivity();
            registerActivity.pageMove(0);
        });

        binding.registerProfileLayout.setOnClickListener(v -> inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0));
    }

    private boolean checkValue() {
        boolean check = false;
        try {
            if (Integer.parseInt(binding.gradeInfoEdittext.getText().toString()) > 3 || Integer.parseInt(binding.gradeInfoEdittext.getText().toString()) < 1 || binding.gradeInfoEdittext.getText().toString().replaceAll("\\p{Z}", "").length() > 1) {
                Toast.makeText(getContext(), "학년은 1~3학년 으로 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
                binding.gradeInfoEdittext.requestFocus();
            }
            else if (Integer.parseInt(binding.classInfoEdittext.getText().toString()) > 3 || Integer.parseInt(binding.classInfoEdittext.getText().toString()) < 1 || binding.classInfoEdittext.getText().toString().replaceAll("\\p{Z}", "").length() > 1) {
                Toast.makeText(getContext(), "학반은 1~3반 으로 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
                binding.classInfoEdittext.requestFocus();
            }
            else if (Integer.parseInt(binding.numberInfoEdittext.getText().toString()) > 20 || Integer.parseInt(binding.numberInfoEdittext.getText().toString()) < 1 || binding.numberInfoEdittext.getText().toString().replaceAll("\\p{Z}", "").length() > 2) {
                Toast.makeText(getContext(), "번호 1~20번 으로 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
                binding.numberInfoEdittext.requestFocus();
            }
            else if (binding.registerNameEdittext.getText().toString().replaceAll("\\p{Z}", "").length() > 10 || binding.registerNameEdittext.getText().toString().replaceAll("\\p{Z}", "").length() < 3) {
                Toast.makeText(getContext(), "이름은 2 ~ 10 자로 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
                binding.registerNameEdittext.requestFocus();
            }
            else if (binding.registerPhoneEdittext.getText().toString().replaceAll("\\p{Z}", "").length() > 15 || binding.registerPhoneEdittext.getText().toString().replaceAll("\\p{Z}", "").length() < 9) {
                Toast.makeText(getContext(), "휴대폰 번호는 9 ~ 15 자로 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
                binding.registerPhoneEdittext.requestFocus();
            }
            else if (!isStringDouble(binding.registerPhoneEdittext.getText().toString())) {
                Toast.makeText(getContext(), "휴대폰 번호는 숫자만 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
                binding.registerPhoneEdittext.requestFocus();
            }
            else if (binding.registerEmailEdittext.getText().toString().replaceAll("\\p{Z}", "").length() > 30 || binding.registerEmailEdittext.getText().toString().replaceAll("\\p{Z}", "").length() < 10) {
                Toast.makeText(getContext(), "이메일은 10 ~ 30 자로 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
                binding.registerEmailEdittext.requestFocus();
            }
            else {
                check = true;
            }
            return check;
        }
        catch (NumberFormatException e) {
            Toast.makeText(getContext(), "공백 없이 입력해 주세요.", Toast.LENGTH_SHORT).show();
            binding.gradeInfoEdittext.requestFocus();
            return false;
        }
    }

    private void setRequest() {
        registerStudentInfo.setValue(new StudentInfo(Integer.parseInt(binding.gradeInfoEdittext.getText().toString()), Integer.parseInt(binding.classInfoEdittext.getText().toString()), Integer.parseInt(binding.numberInfoEdittext.getText().toString())));
        registerName.setValue(binding.registerNameEdittext.getText().toString());
        registerPhone.setValue(binding.registerPhoneEdittext.getText().toString());
        registerEmail.setValue(binding.registerEmailEdittext.getText().toString());
        register.setValue(true);
    }

    private boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public MutableLiveData<StudentInfo> getRegisterStudentInfo() {
        return registerStudentInfo;
    }
    public MutableLiveData<String> getRegisterName() {
        return registerName;
    }
    public MutableLiveData<String> getRegisterPhone() {
        return registerPhone;
    }
    public MutableLiveData<String> getRegisterEmail() {
        return registerEmail;
    }
    public MutableLiveData<Boolean> getRegister() {
        return register;
    }

    @Override
    protected int layoutId() {
        return R.layout.register_profile_fragment;
    }
}
