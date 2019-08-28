package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.b1nd.service.model.Student;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MyinfoProfileFragmentBinding;

public class MyinfoProfileFragment extends BaseFragment<MyinfoProfileFragmentBinding> {

    private DatabaseHelper databaseHelper;
    private Member member;
    private Student student;
    private ClassInfo classInfo;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaseHelper = DatabaseHelper.getInstance(getContext());

        initData();
        initView();
    }

    private void initData() {
        member = databaseHelper.getMyInfo();
        student = databaseHelper.getStudentByMember(member);
        classInfo = databaseHelper.getClassByClassIdx(student.getClassIdx());
    }

    private void initView() {
        if (member.getProfileImage() == "") {
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
