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

    private RegisterViewModel registerViewModel;
    private StudentRegisterRequest request;
    private StudentInfo studentInfo;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();

//        binding.finLayout.setOnTouchListener((v, event) -> {
//            boolean returnValue = true;
//
//            if (event.getAction() == MotionEvent.ACTION_UP) {
//                returnValue = false;
//                request = setData(id, pw);
//
//                Log.d("RegisterData", request.getId() + request.getPw());
//                registerViewModel.studentRegister(request);
//            }
//            return returnValue;
//        });

        binding.finLayout.setOnClickListener(v -> {
            Bundle bundle = getArguments();
            String id = bundle.getString("id");
            String pw = bundle.getString("pw");
            Toast.makeText(getActivity(), id + pw, Toast.LENGTH_SHORT).show();
            request = setData(id, pw);
            registerViewModel.studentRegister(request);
        });

        registerViewModel.getIsSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                Toast.makeText(getActivity(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        registerViewModel.getErrorMessage().observe(this, errorMessage -> Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show());

        //            RegisterActivity registerActivity = (RegisterActivity) getActivity();
//            registerActivity.pageMove(0);

//            registerViewModel.studentRegister(request);
    }

    @Override
    protected int layoutId() {
        return R.layout.register_profile_fragment;
    }

    private void initViewModel() {
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
    }

    private StudentRegisterRequest setData(String id, String pw) {
        StudentInfo studentInfo = new StudentInfo(Integer.parseInt(binding.gradeInfoEdittext.getText().toString()), Integer.parseInt(binding.classInfoEdittext.getText().toString()), Integer.parseInt(binding.numberInfoEdittext.getText().toString()));

//        studentInfo.setGrade(Integer.parseInt(binding.gradeInfoEdittext.getText().toString()));
//        studentInfo.setRoom(Integer.parseInt(binding.classInfoEdittext.getText().toString()));
//        studentInfo.setNumber(Integer.parseInt(binding.numberInfoEdittext.getText().toString()));

        StudentRegisterRequest studentRegisterRequest = new StudentRegisterRequest(id, pw, binding.registerEmailEdittext.getText().toString(), binding.registerPhoneEdittext.getText().toString(), binding.registerNameEdittext.getText().toString(), studentInfo);

//        request.setStudent(studentInfo);
//
//        request.setName(binding.registerNameEdittext.getText().toString());
//        request.setMobile(binding.registerPhoneEdittext.getText().toString());
//        request.setEmail(binding.registerEmailEdittext.getText().toString());
//        request.setId(id);
//        request.setPw(pw);
        return studentRegisterRequest;
    }
}
