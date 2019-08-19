package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.CounselActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.CounselViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.CounselAdapter;

public class CounselActivity extends BaseActivity<CounselActivityBinding> implements SwipeRefreshLayout.OnRefreshListener {

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
        counselViewModel = ViewModelProviders.of(this).get(CounselViewModel.class);
        counselViewModel.getAllCounsel();
    }

    private void observableCounselViewModel() {
        counselViewModel.getData().observe(this, data -> {
//            Log.d("Tag", data.size() + "");
            for (int i = 0; i<data.size(); i++) {
                counselList.add(index, data.get(i));
                counselAdapter.notifyItemInserted(index);
                index++;
            }
            if (counselList.size() == 0) {
                Toast.makeText(getApplicationContext(), "신청된 상담이 없습니다", Toast.LENGTH_SHORT).show();
            }
        });

        counselViewModel.getLoading().observe(this, loading -> new Handler().postDelayed(() -> binding.swipeRefreshLayout.setRefreshing(loading), 500));

        counselViewModel.getError().observe(this, Throwable::printStackTrace);

        counselViewModel.getErrorMessage().observe(this, errorMessage -> Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show());

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
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

    @Override
    public void onRefresh() {
        counselViewModel.getAllCounsel();
    }
}
