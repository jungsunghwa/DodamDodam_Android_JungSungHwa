package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;

import com.airbnb.lottie.LottieDrawable;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.ApplySuccessActivityBinding;

public class ApplySuccessActivity extends BaseActivity<ApplySuccessActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.lottie.playAnimation();
        binding.lottie.setRepeatCount(LottieDrawable.INFINITE);

        binding.finishBtn.setOnClickListener(view -> finish());
    }

    @Override
    protected int layoutId() {
        return R.layout.apply_success_activity;
    }
}
