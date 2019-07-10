package kr.hs.dgsw.smartschool.dodamdodam.activity.song;

import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.Glide;

import kr.hs.dgsw.smartschool.dodamdodam.Model.song.Video;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongDetailActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.widget.ViewUtils;

public class SongDetailActivity extends BaseActivity<SongDetailActivityBinding> {

    public static final String EXTRA_VIDEO = "video";

    @Override
    protected int layoutId() {
        return R.layout.song_detail_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lightNavMode();
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewUtils.marginTopSystemWindow(binding.toolbar);

        Video source = getIntent().getParcelableExtra(EXTRA_VIDEO);
        setTitle(source.getVideoTitle());
        binding.textChannelTitle.setText(source.getChannelTitle());
        Glide.with(this).load(source.getThumbnail()).into(binding.imageThumbnail);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        binding.collapsingLayout.setTitle(title);
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
