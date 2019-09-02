package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.RegisterActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterAccountFragmentBinding;

public class RegisterAccountFragment extends BaseFragment<RegisterAccountFragmentBinding>{

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.nextLayout.setOnTouchListener((v, event) -> {
            boolean returnValue = true;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                returnValue = false;

                RegisterProfileFragement registerProfileFragement = new RegisterProfileFragement();
                Bundle bundle = new Bundle();
                bundle.putString("id", binding.registerIdEdittext.getText().toString());
                bundle.putString("pw", binding.registerPwEdittext.getText().toString());
                registerProfileFragement.setArguments(bundle);

                RegisterActivity registerActivity = (RegisterActivity) RegisterAccountFragment.this.getActivity();
                registerActivity.pageMove(1);
            }

            return returnValue;

        });

    }

    @Override
    protected int layoutId() {
        return R.layout.register_account_fragment;
    }
}
