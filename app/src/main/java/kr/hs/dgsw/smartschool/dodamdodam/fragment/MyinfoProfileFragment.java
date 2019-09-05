package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.b1nd.service.model.Student;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.ChangePasswordActivity;
import kr.hs.dgsw.smartschool.dodamdodam.activity.LoginActivity;
import kr.hs.dgsw.smartschool.dodamdodam.activity.MyinfoProfileChangeActivity;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.database.TokenManager;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MyinfoProfileFragmentBinding;

public class MyinfoProfileFragment extends BaseFragment<MyinfoProfileFragmentBinding> {

    private DatabaseHelper databaseHelper;
    private Member member;
    private Student student;
    private ClassInfo classInfo;
    private TokenManager manager;

    public MyinfoProfileFragment() {
        manager = TokenManager.getInstance(getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseHelper = DatabaseHelper.getInstance(getContext());

        initData();
        initView();

        binding.logoutLayout.setOnClickListener(v -> {
            manager.setToken(null, null);
            startActivity(new Intent(getActivity(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            getActivity().finishAffinity();
            Toast.makeText(getActivity(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
        });

        binding.changeProfile.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MyinfoProfileChangeActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            getActivity().finish();
        });

        binding.changePassword.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ChangePasswordActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            getActivity().finish();
        });
    }
    private void initData() {
        member = databaseHelper.getMyInfo();
        student = databaseHelper.getStudentByMember(member);
        classInfo = databaseHelper.getClassByClassIdx(student.getClassIdx());
    }

    private void initView() {
        if (member.getProfileImage() == null) {
            Glide.with(this).load(R.drawable.noprofileimg).into(binding.profileImage);
        }
        else {
            Glide.with(this).load(member.getProfileImage()).into(binding.profileImage);
        }
        binding.nameText.setText(member.getName());
        binding.gradeClassText.setText(classInfo.getGrade() + "학년 " + classInfo.getRoom() + "반 " + student.getNumber() + "번");
        binding.emailText.setText(student.getEmail());
    }

    @Override
    protected int layoutId() {
        return R.layout.myinfo_profile_fragment;
    }
}
