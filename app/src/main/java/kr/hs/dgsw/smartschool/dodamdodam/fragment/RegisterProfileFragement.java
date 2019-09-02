package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.finLayout.setOnClickListener(v -> {
            registerStudentInfo.setValue(new StudentInfo(Integer.parseInt(binding.gradeInfoEdittext.getText().toString()), Integer.parseInt(binding.classInfoEdittext.getText().toString()), Integer.parseInt(binding.numberInfoEdittext.getText().toString())));
            registerName.setValue(binding.registerNameEdittext.getText().toString());
            registerPhone.setValue(binding.registerPhoneEdittext.getText().toString());
            registerEmail.setValue(binding.registerEmailEdittext.getText().toString());
            register.setValue(true);
        });
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
