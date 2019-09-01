package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.RegisterActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterProfileFragmentBinding;

public class RegisterProfileFragement extends BaseFragment<RegisterProfileFragmentBinding> {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.finLayout.setOnClickListener(v -> {
            RegisterActivity registerActivity = (RegisterActivity) getActivity();
            registerActivity.pageMove(0);
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.register_profile_fragment;
    }
}
