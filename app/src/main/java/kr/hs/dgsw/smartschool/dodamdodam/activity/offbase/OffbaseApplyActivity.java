package kr.hs.dgsw.smartschool.dodamdodam.activity.offbase;

import android.os.Bundle;
import android.view.MenuItem;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BaseActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.OffbaseApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;

public class OffbaseApplyActivity extends BaseActivity<OffbaseApplyActivityBinding> {

    private OffbaseViewModel viewModel;

    @Override
    protected int layoutId() {
        return R.layout.offbase_apply_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new OffbaseViewModel(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
