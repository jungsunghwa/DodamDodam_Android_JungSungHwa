package kr.hs.dgsw.smartschool.dodamdodam.activity.song;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import kr.hs.dgsw.smartschool.dodamdodam.Model.song.Video;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongDetailActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.MemberViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.ViewUtils;

public class SongDetailActivity extends BaseActivity<SongDetailActivityBinding> {

    public static final String EXTRA_VIDEO = "video";

    private MemberViewModel viewModel;

    @Override
    protected int layoutId() {
        return R.layout.song_detail_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lightNavMode();
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ViewUtils.marginTopSystemWindow(binding.toolbar);

        viewModel = ViewModelProviders.of(this).get(MemberViewModel.class);

        Video source = getIntent().getParcelableExtra(EXTRA_VIDEO);
        setTitle(source.getVideoTitle());
        binding.textChannelTitle.setText("채널 이름 - " + source.getChannelTitle());
        binding.textSubmitdate.setText("신청날짜 - " + source.getSubmitDate());
        binding.textPlaydate.setText(String.valueOf(source.getSubmitDate()));
        viewModel.search(source.getApplyMemberId());
        viewModel.getData().observe(this, member -> binding.textUser.setText(member.getName()));
        binding.textAllowed.setText(isAllowTypeConversion(source.getIsAllow()));
        Glide.with(this).load(source.getThumbnail()).into(binding.imageThumbnail);

        binding.imageThumbnail.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(source.getVideoUrl()));
            startActivity(intent);
        });
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

    private String isAllowTypeConversion(int isAllow) {
        if (isAllow == 0) {
            return "대기중";
        } else if (isAllow == 1) {
            return "승인됨";
        } else {
            return "거절됨";
        }
    }
}
