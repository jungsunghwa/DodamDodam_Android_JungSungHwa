package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LostfoundActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LostFoundViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.LostFoundAdapter;

public class LostFoundActivity extends BaseActivity<LostfoundActivityBinding> {
    List<LostFound> lostFoundList = new ArrayList<>();
    LostFoundAdapter lostFoundAdapter = new LostFoundAdapter(this, lostFoundList);

    LostFoundViewModel lostFoundViewModel;
    Integer page = 1;
    Integer type; //분실인지=1 습득인지=2 판단하는 타입
    int index = 0;
    boolean isLoading = false;

    @Override
    protected int layoutId() {
        return R.layout.lostfound_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();
        observableLostFoundViewModel();

        binding.lostfoundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lostFoundList.clear();
                index = 0;

                if (position == 0) {
                    type = 1;
                } else if (position == 1) {
                    type = 2;
                }

                lostFoundViewModel.getLostFound(page, type);
                setRecyclerViewManager();
                initScrollListener();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private void initViewModel() {
        lostFoundViewModel = new LostFoundViewModel(this);
    }

    private void observableLostFoundViewModel() {

        lostFoundViewModel.getResponse().observe(this, data -> {
            for (int i = 0; i < data.size(); i++) {
                lostFoundList.add(index, data.get(i));
                lostFoundAdapter.notifyItemInserted(index);
                index++;
            }
        });

        lostFoundViewModel.getLoginErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
        
    }

    private void setRecyclerViewManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.lostfoundRecyclerView.setLayoutManager(linearLayoutManager);
        Log.d("TAG", "listsize2 = " + lostFoundList.size());
        binding.lostfoundRecyclerView.setAdapter(lostFoundAdapter);
    }

    private void initScrollListener() {
        binding.lostfoundRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == lostFoundList.size() - 1) {
                        if (index % 10 == 0) {
                            loadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        lostFoundList.add(null);
        lostFoundAdapter.notifyItemInserted(lostFoundList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed( ()-> {

            lostFoundList.remove(lostFoundList.size() - 1);
            int scrollPosition = lostFoundList.size();
            lostFoundAdapter.notifyItemRemoved(scrollPosition);

            page++;
            lostFoundViewModel.getLostFound(page, type);
            lostFoundAdapter.notifyDataSetChanged();
            isLoading = false;

        }, 1000);
    }
}