package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LostfoundWritingActivityBinding;

public class LostFoundWritingActivity extends BaseActivity<LostfoundWritingActivityBinding> {
    @Override
    protected int layoutId() {
        return R.layout.lostfound_writing_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }
}
