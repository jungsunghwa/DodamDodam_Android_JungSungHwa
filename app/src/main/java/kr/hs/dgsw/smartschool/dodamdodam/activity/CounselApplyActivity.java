package kr.hs.dgsw.smartschool.dodamdodam.activity;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.CounselApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.CounselRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.CounselViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TeacherViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.OnItemSelectedListener;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.TeacherAdapter;

public class CounselApplyActivity extends BaseActivity<CounselApplyActivityBinding> implements OnItemSelectedListener<Teacher> {

    TeacherViewModel teacherViewModel;
    CounselViewModel counselViewModel;
    TeacherAdapter teacherAdapter;
    List<Teacher> teacherList = new ArrayList<>();
    Context context;
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
        counselViewModel = new CounselViewModel(context);

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

        teacherViewModel.getSelectedTeacher().observe(this, teacher -> {
            if (teacher.getName() == null) {
                binding.applyLayout.setVisibility(View.GONE);
            } else
                binding.applyLayout.setVisibility(View.VISIBLE);
            binding.btnApply.setTag(teacher);
        });

        counselViewModel.getSuccess().observe(this, success -> {
            if(success)
                finish();
        });

        binding.btnApply.setOnClickListener(v -> {
            CounselRequest request = new CounselRequest();
            Teacher teacher = (Teacher) v.getTag();

            request.setManageTeacherId(teacher.getId());
            request.setReason(binding.reasonText.getText().toString());

            counselViewModel.postCounsel(request);
        });
    }

    private void setRecyclerView() {
        teacherAdapter = new TeacherAdapter(this, teacherList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.teacherRecycler.setAdapter(teacherAdapter);
        binding.teacherRecycler.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemSelected(Teacher teacher, boolean selected) {
        teacherViewModel.select(teacher, selected);
    }
}
