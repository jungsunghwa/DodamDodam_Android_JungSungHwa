package kr.hs.dgsw.smartschool.dodamdodam.activity.song;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import kr.hs.dgsw.smartschool.dodamdodam.Model.song.VideoYoutubeData;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongListActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.SongViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.OnItemClickListener;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.SongListAdapter;

/*
 * TODO
 * 썸네일 화질 사용자 설정 (낮음, 보통, 높음 | 서버가 기본적으로 반환하는 값: 높음)
 * 신청 정보 상세 보기 (카드 클릭시 카드가 펴지며 상세 정보 표시)
 */
public class SongListActivity extends BaseActivity<SongListActivityBinding> implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener<VideoYoutubeData> {

    public static final String REQ_SONG_APPLY_RESULT_MESSAGE = "message";
    private static final String KEY_SHOW_ONLY_ALLOWED = "show_only_allowed";
    private static final int REQ_SONG_APPLY = 1000;
    private SongViewModel viewModel;
    private SongListAdapter adapter;

    private MenuItem showOnlyItem;

    @Override
    protected int layoutId() {
        return R.layout.song_list_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new SongViewModel(this);

        viewModel.getData().observe(this, songs -> {
            adapter.setList(songs);
            binding.songList.smoothScrollToPosition(0);
        });
        viewModel.getError().observe(this, error -> {
            Snackbar.make(binding.rootLayout, error.getMessage(), Snackbar.LENGTH_SHORT).show();
        });
        viewModel.getLoading().observe(this, loading -> new Handler().postDelayed(() -> binding.swipeRefreshLayout.setRefreshing(loading), 500));

        if (savedInstanceState != null && savedInstanceState.getBoolean(KEY_SHOW_ONLY_ALLOWED))
            viewModel.listAllow();
        else
            viewModel.list();

        binding.songList.setAdapter(adapter = new SongListAdapter(this, this));
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorSecondary);
        binding.swipeRefreshLayout.setOnRefreshListener(this);
        binding.fabOpenSongApply.setOnClickListener(v -> startActivityForResult(new Intent(this, SongApplyActivity.class), REQ_SONG_APPLY));
    }

    @Override
    public void onItemClick(VideoYoutubeData videoYoutubeData) {
        startActivity(new Intent(this, SongDetailActivity.class).putExtra(SongDetailActivity.EXTRA_VIDEO, videoYoutubeData.getSource()));
    }

    @Override
    public void onRefresh() {
        if (showOnlyItem.isChecked())
            viewModel.listAllow();
        else
            viewModel.list();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SHOW_ONLY_ALLOWED, showOnlyItem.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        showOnlyItem.setChecked(savedInstanceState.getBoolean(KEY_SHOW_ONLY_ALLOWED));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_song, menu);
        showOnlyItem = menu.findItem(R.id.menu_only_allowed);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_only_allowed:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    viewModel.listAllow();
                } else {
                    item.setChecked(false);
                    viewModel.list();
                }
                return true;
            case android.R.id.home:
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
