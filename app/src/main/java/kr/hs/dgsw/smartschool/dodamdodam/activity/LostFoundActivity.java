package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LostfoundActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LostFoundViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.LostFoundAdapter;

public class LostFoundActivity extends BaseActivity<LostfoundActivityBinding> {
    List<LostFound> lostFoundList = new ArrayList<>();
    LostFoundAdapter lostFoundAdapter = new LostFoundAdapter(this, lostFoundList);

    LostFoundViewModel lostFoundViewModel;

    // 말 그대로 페이지 (게시물 10개씩)
    Integer page = 1;

    // 분실인지=1 습득인지=2 판단하는 타입
    Integer type = 1;

    // 전체 리스트 인지, 내 리스트 인지
    Integer myCheck = 0;

    private final Integer ALL = 0;
    private final Integer MY = 1;

    // List 에 넣기 위한 index 선언
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
        initScrollListener();
        observableLostFoundViewModel();

        // spinner 선택된 item (분실물, 습득물)에 따른 recyclerview 표시
        binding.lostfoundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                lostFoundList.clear();
                index = 0;
                page = 1;

                if (position == 0) {
                    type = 1;
                } else if (position == 1) {
                    type = 2;
                }

                lostFoundViewModel.getLostFound(page, type);
                setRecyclerViewManager();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 내 글 표시
        binding.myWritingCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                binding.myWritingCheckbox.setText("전체 글");
                myCheck = ALL;
            } else {
                binding.myWritingCheckbox.setText("내 글만");
                myCheck = MY;
            }
            index = 0;
            page = 1;
            lostFoundList.clear();
            lostFoundViewModel.getLostFound(page, type);
            setRecyclerViewManager();
        });

        // 검색 기능 (엔터키 누를 시 검색)
        binding.searchEditText.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                lostFoundList.clear();
                index = 0;
                page = 1;

                lostFoundViewModel.getLostFoundSearch(binding.searchEditText.getText().toString());

                setRecyclerViewManager();

                return true;
            }
            return false;
        });

        binding.lostfoundWritingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LostFoundWritingActivity.class);
            startActivity(intent);
            finish();
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
        lostFoundViewModel = ViewModelProviders.of(this).get(LostFoundViewModel.class);
    }

    private void observableLostFoundViewModel() {

        // 서버로부터 값이 들어올때마다 lostFoundList 에 넣어줌
        lostFoundViewModel.getData().observe(this, data -> {
            for (int i = 0; i < data.size(); i++) {
                if (myCheck == MY) {
                    if (data.get(i).getMemberId().equals(DatabaseHelper.getInstance(this).getMyInfo().getId())) {
                        lostFoundList.add(index, data.get(i));
                        lostFoundAdapter.notifyItemInserted(index);
                        index++;
                    }
                }
                else {
                    lostFoundList.add(index, data.get(i));
                    lostFoundAdapter.notifyItemInserted(index);
                    index++;
                }
            }
            if (lostFoundList.size() == 0) {
                Toast.makeText(getApplicationContext(), "검색된 내용이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        lostFoundViewModel.getErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

    }

    private void setRecyclerViewManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.lostfoundRecyclerView.setLayoutManager(linearLayoutManager);
        binding.lostfoundRecyclerView.setAdapter(lostFoundAdapter);
    }

    private void initScrollListener() {

        // 무한 스크롤 기능을 구현하기 위해서 마지막으로 1개 남는 걸 계산
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

    // 무한 스크롤 기능 구현
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