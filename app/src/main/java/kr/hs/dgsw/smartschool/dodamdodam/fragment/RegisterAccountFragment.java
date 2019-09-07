package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    InputMethodManager inputMethodManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        inputMethodManager= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.nextLayout.setOnTouchListener((v, event) -> {
            boolean returnValue = true;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                returnValue = false;

                if (checkValue()) {
                    setRequest();
                    RegisterActivity registerActivity = (RegisterActivity) RegisterAccountFragment.this.getActivity();
                    registerActivity.pageMove(1);
                }
            }
            return returnValue;
        });

        binding.registerAccountLayout.setOnClickListener(v -> inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0));
    }

    private boolean checkValue() {
        boolean check = false;
        if (binding.registerIdEdittext.getText().toString().replaceAll("\\p{Z}", "").length() > 20 || binding.registerIdEdittext.getText().toString().replaceAll("\\p{Z}", "").length() < 5) {
            Toast.makeText(getContext(), "아이디는 5 ~ 20 자로 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
            binding.registerIdEdittext.requestFocus();
        }
        else if (binding.registerIdEdittext.getText().toString().matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            Toast.makeText(getContext(), "아이디는 영어나 숫자로만 입력 할 수 있습니다.", Toast.LENGTH_SHORT).show();
            binding.registerIdEdittext.requestFocus();
        }
        else if (binding.registerPwEdittext.getText().toString().replaceAll("\\p{Z}", "").length() > 20 || binding.registerPwEdittext.getText().toString().replaceAll("\\p{Z}", "").length() < 7) {
            Toast.makeText(getContext(), "비밀번호는 7 ~ 20 자로 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
            binding.registerPwEdittext.requestFocus();
        }
        else if (binding.registerPwEdittext.getText().toString().matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
            Toast.makeText(getContext(), "비밀번호는 영어나 숫자로만 입력 할 수 있습니다.", Toast.LENGTH_SHORT).show();
            binding.registerPwEdittext.requestFocus();
        }
        else if (!binding.registerPwEdittext.getText().toString().equals(binding.registerPwCheckEdittext.getText().toString())) {
            Toast.makeText(getContext(), "비밀번호가 서로 다릅니다", Toast.LENGTH_SHORT).show();
            binding.registerPwCheckEdittext.requestFocus();
        }
        else {
            check = true;
        }
        return check;
    }

    private void setRequest() {
        registerId.setValue(binding.registerIdEdittext.getText().toString());
        registerPw.setValue(binding.registerPwEdittext.getText().toString());
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
