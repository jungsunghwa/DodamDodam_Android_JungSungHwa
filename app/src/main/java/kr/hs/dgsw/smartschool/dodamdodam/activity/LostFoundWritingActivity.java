package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LostfoundWritingActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LostFoundViewModel;

public class LostFoundWritingActivity extends BaseActivity<LostfoundWritingActivityBinding> {

    LostFoundViewModel lostFoundViewModel;
    int type = 1;


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

        binding.lostfoundCardImageView.setOnClickListener(v -> {

        });

        // 분실/습득물 체크시 변환
        binding.kindofCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                binding.kindofCheckbox.setText("분실물");
                type = 1;
            } else {
                binding.kindofCheckbox.setText("습득물");
                type = 2;
            }
        });

        binding.postWritingLostfound.setOnClickListener(v -> {
            LostFoundRequest request = new LostFoundRequest();

            editTextEmptyCheck();
            setLostFoundRequestData(request);

            lostFoundViewModel.postCreateLostFound(request);
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

    private void editTextEmptyCheck() {
        if (binding.writingTitleEdittext.length() == 0) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            binding.writingTitleEdittext.requestFocus();
        }
        else if (binding.writingPlaceEdittext.length() == 0) {
            Toast.makeText(this, "장소를 입력해주세요.", Toast.LENGTH_SHORT).show();
            binding.writingPlaceEdittext.requestFocus();
        }
        else if (binding.writingContentEdittext.length() == 0) {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            binding.writingContentEdittext.requestFocus();
        }
        else if (binding.writingContactEdittext.length() == 0) {
            Toast.makeText(this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            binding.writingContactEdittext.requestFocus();
        }
    }

    private LostFoundRequest setLostFoundRequestData(LostFoundRequest request) {

        request.setPlace(binding.writingPlaceEdittext.getText().toString());
        request.setTitle(binding.writingTitleEdittext.getText().toString());
        request.setContent(binding.writingContentEdittext.getText().toString());
        request.setContact(binding.writingContactEdittext.getText().toString());
        if (type > 0) {
            request.setType(type);
        } else {
            Toast.makeText(this, "오류", Toast.LENGTH_SHORT).show();
            return null;
        }


        return request;
    }
}
