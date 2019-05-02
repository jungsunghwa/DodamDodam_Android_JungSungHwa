package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongListActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.widget.ViewUtils;

public class SongListActivity extends BaseActivity<SongListActivityBinding> {

    @Override
    protected int layoutId() {
        return R.layout.song_list_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.marginBottomSystemWindow(binding.songApplyFab);

        binding.songApplyFab.setOnClickListener(v -> startActivity(new Intent(this, SongApplyActivity.class)));
    }
}
