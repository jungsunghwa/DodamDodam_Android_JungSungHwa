package kr.hs.dgsw.smartschool.dodamdodam.activity.song;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongListActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.SongViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.SongListAdapter;

/*
 * TODO
 * 썸네일 화질 사용자 설정 (낮음, 보통, 높음 | 서버가 반환하는 값: 높음)
 * 신청 정보 상세 보기 (카드 클릭시 카드가 펴지며 상세 정보 표시)
 */
public class SongListActivity extends BaseActivity<SongListActivityBinding> implements SwipeRefreshLayout.OnRefreshListener {

    public static final String REQ_SONG_APPLY_RESULT_MESSAGE = "message";
    private static final String TAG = "SongListActivity";
    private static final int REQ_SONG_APPLY = 1000;
    private SongViewModel viewModel;

    @Override
    protected int layoutId() {
        return R.layout.song_list_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new SongViewModel(this);

        viewModel.getSongsData().observe(this, songs -> binding.songList.setAdapter(new SongListAdapter(this, songs)));
        viewModel.getError().observe(this, error -> {
            Snackbar.make(binding.rootLayout, error.getMessage(), Snackbar.LENGTH_SHORT).show();
        });
        viewModel.getLoading().observe(this, loading -> {
            binding.swipeRefreshLayout.setRefreshing(loading);
            binding.progress.setVisibility(loading ? View.VISIBLE : View.GONE);
        });

        viewModel.list();

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.openSongApplyFab.setOnClickListener(v -> startActivityForResult(new Intent(this, SongApplyActivity.class), REQ_SONG_APPLY));
    }

    @Override
    public void onRefresh() {
        viewModel.list();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SONG_APPLY && resultCode == RESULT_OK) {
            Snackbar.make(binding.rootLayout, data.getStringExtra(REQ_SONG_APPLY_RESULT_MESSAGE), Snackbar.LENGTH_SHORT).show();
            viewModel.list();
        }
    }
}
