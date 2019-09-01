package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.RegisterActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterAccountFragmentBinding;

public class RegisterAccountFragment extends BaseFragment<RegisterAccountFragmentBinding>{
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.nextLayout.setOnClickListener(v -> {
            Log.d("Touch", "Pass");
            RegisterActivity registerActivity = (RegisterActivity) getActivity();
            registerActivity.pageMove(1);
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.register_account_fragment;
    }
}
