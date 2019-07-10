package kr.hs.dgsw.smartschool.dodamdodam.activity;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.CounselApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TeacherViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.TeacherAdapter;

public class CounselApplyActivity extends BaseActivity<CounselApplyActivityBinding> {

    TeacherViewModel teacherViewModel;
    TeacherAdapter teacherAdapter;
    List<Teacher> teacherList = new ArrayList<>();
    int index = 0;

    @Override
    protected int layoutId() {
        return R.layout.counsel_apply_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();
        observableTeacherViewModel();
        setRecyclerView();
    }

    private void initViewModel() {
        teacherViewModel = new TeacherViewModel(this);

        teacherViewModel.getTeacher();

        Log.d("TAG", teacherList.size()+"");
    }

    private void observableTeacherViewModel() {
        teacherViewModel.getData().observe(this, data -> {
            for (int i = 0; i<data.getTeachers().size(); i++) {
                teacherList.add(index, data.getTeachers().get(i));
                teacherAdapter.notifyItemInserted(index);
                index++;
            }
        });

        teacherViewModel.getError().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void setRecyclerView() {
        teacherAdapter = new TeacherAdapter(this, teacherList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.teacherRecycler.setAdapter(teacherAdapter);
        binding.teacherRecycler.setLayoutManager(linearLayoutManager);
    }

}
