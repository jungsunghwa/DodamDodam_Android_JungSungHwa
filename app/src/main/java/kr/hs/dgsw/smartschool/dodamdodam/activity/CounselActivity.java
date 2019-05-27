package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.CounselActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TeacherViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.CounselAdapter;

public class CounselActivity extends BaseActivity<CounselActivityBinding> {

    private TeacherViewModel teacherViewModel;
    private CounselAdapter counselAdapter;
    private Context context;
    private List<Teacher> teacherList;

    @Override
    protected int layoutId() {
        return R.layout.counsel_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();

        init();

    }

    private void initViewModel() {
        teacherViewModel = new TeacherViewModel(this);
        teacherViewModel.getTeacher();
    }

    private void init() {
        RecyclerView recyclerView = binding.teacherRecycler;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        counselAdapter = new CounselAdapter(context, teacherList);
        recyclerView.setAdapter(counselAdapter);
    }

}
