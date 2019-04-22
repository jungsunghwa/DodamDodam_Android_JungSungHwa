package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.ApplySuccessActivityBinding;

public class ApplySuccessActivity extends BaseActivity<ApplySuccessActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_success_activity);
        binding = DataBindingUtil.setContentView(this, layoutId());

        Toast.makeText(this, "신청에 성공 하셨습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int layoutId() {
        return R.layout.apply_success_activity;
    }
}
