package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import java.util.ArrayList;

import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LostfoundWritingActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LostFoundViewModel;

public class LostFoundItemActivity extends BaseActivity<LostfoundWritingActivityBinding>{

    LostFoundViewModel lostFoundViewModel;

    Intent intent = getIntent();

    ArrayList<LostFound> lostFoundList = intent.getParcelableArrayListExtra("lostfound");
    int position = intent.getIntExtra("position", 0);



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

        initViewModel();
        writingSetText();


    }

    private void initViewModel() {
        lostFoundViewModel = new LostFoundViewModel(this);
    }

    private void writingSetText() {
        binding.writingTitleEdittext.setText(lostFoundList.get(position).getTitle());
        binding.writingPlaceEdittext.setText(lostFoundList.get(position).getPlace());
        binding.writingContentEdittext.setText(lostFoundList.get(position).getContent());
        binding.writingContactEdittext.setText(lostFoundList.get(position).getContact());
    }
}
