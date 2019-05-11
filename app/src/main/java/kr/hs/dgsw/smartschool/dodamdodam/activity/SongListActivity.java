package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongListActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.SongViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.SongListAdapter;

public class SongListActivity extends BaseActivity<SongListActivityBinding> {

    private SongViewModel viewModel;

    private static final String TAG = "SongListActivity";

    @Override
    protected int layoutId() {
        return R.layout.song_list_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new SongViewModel(this);

        viewModel.getSongsData().observe(this, songs -> binding.songList.setAdapter(new SongListAdapter(this, songs)));
        viewModel.getError().observe(this, error -> {
            Log.w(TAG, "ERROR!", error);
            Snackbar.make(binding.rootLayout, error.getMessage(), Snackbar.LENGTH_SHORT).show();
        });
        viewModel.getLoading().observe(this, loading -> Log.d(TAG, "isLoading " + loading));

        viewModel.list();

        binding.songApplyFab.setOnClickListener(v -> startActivity(new Intent(this, SongApplyActivity.class)));
    }
}
