package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;


import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.CounselActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.CounselViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.CounselAdapter;

public class CounselActivity extends BaseActivity<CounselActivityBinding> {

    CounselViewModel counselViewModel;
    CounselAdapter counselAdapter;
    List<Counsel> counselList = new ArrayList<>();
    int index = 0;

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
        observableCounselViewModel();
        setRecyclerView();

        binding.counselApplyFab.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CounselApplyActivity.class);
            startActivity(intent);
        });
    }

    private void initViewModel() {
        counselViewModel = new CounselViewModel(this);
        counselViewModel.getAllCounsel();
    }

    private void observableCounselViewModel() {
        counselViewModel.getData().observe(this, data -> {
            Log.d("Tag", data.size() + "");
            for (int i = 0; i<data.size(); i++) {
                counselList.add(index, data.get(i));
                counselAdapter.notifyItemInserted(1);
                index++;
            }
        });

        counselViewModel.getError().observe(this, Throwable::printStackTrace);

        counselViewModel.getErrorMessage().observe(this, errorMessage -> Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show());
    }

    private void setRecyclerView() {
        counselAdapter = new CounselAdapter(this, counselList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.counselRecycler.setAdapter(counselAdapter);
        binding.counselRecycler.setLayoutManager(linearLayoutManager);
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
