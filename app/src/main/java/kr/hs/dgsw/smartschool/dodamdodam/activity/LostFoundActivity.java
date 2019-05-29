package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
    final Integer type = 1; //분실인지=1 습득인지=2 판단하는 타입
    int index = 0;

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

        setRecyclerViewManager();

        binding.lostfoundRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!binding.lostfoundRecyclerView.canScrollVertically(1)) {
                    if (lostFoundList.size() == 10) {
                        page++;
                        lostFoundViewModel.getLostFound(page, type);
                        lostFoundAdapter.notifyDataSetChanged();
                    }
                }
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
        lostFoundViewModel.getLostFound(page, type);

        lostFoundViewModel.getResponse().observe(this, data -> {
            for (int i = 0; i < data.size(); i++) {
                lostFoundList.add(index, data.get(i));
                index++;
            }

            binding.lostfoundRecyclerView.setAdapter(lostFoundAdapter);

            if (page != 1) {
                binding.lostfoundRecyclerView.scrollToPosition(lostFoundAdapter.getItemCount()-8);
            }

        });

        lostFoundViewModel.getLoginErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void setRecyclerViewManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.lostfoundRecyclerView.setLayoutManager(linearLayoutManager);
    }

//    private void setSpinner() {
//        ArrayAdapter<String> arrayAdapter;
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("분실물");
//        arrayList.add("습득물");
//
//        arrayAdapter = new ArrayAdapter<>(getApplicationContext(), binding.lostfoundSpinner, arrayList);
//    }
}