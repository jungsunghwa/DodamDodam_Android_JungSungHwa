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
import androidx.lifecycle.MutableLiveData;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.RegisterActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.RegisterAccountFragmentBinding;

public class RegisterAccountFragment extends BaseFragment<RegisterAccountFragmentBinding>{

    private MutableLiveData<String> registerId = new MutableLiveData<>();
    private MutableLiveData<String> registerPw = new MutableLiveData<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.nextLayout.setOnTouchListener((v, event) -> {
            boolean returnValue = true;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                returnValue = false;

                registerId.setValue(binding.registerIdEdittext.getText().toString());
                registerPw.setValue(binding.registerPwEdittext.getText().toString());

                RegisterActivity registerActivity = (RegisterActivity) RegisterAccountFragment.this.getActivity();
                registerActivity.pageMove(1);
            }
            return returnValue;
        });
    }

    public MutableLiveData<String> getRegisterId() {
        return registerId;
    }
    public MutableLiveData<String> getRegisterPw() {
        return registerPw;
    }

    @Override
    protected int layoutId() {
        return R.layout.register_account_fragment;
    }
}
