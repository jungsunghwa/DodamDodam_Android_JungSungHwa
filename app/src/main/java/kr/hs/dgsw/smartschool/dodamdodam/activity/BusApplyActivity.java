package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import java.util.ArrayList;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Type;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.BusApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;

public class BusApplyActivity extends BaseActivity<BusApplyActivityBinding> {

    BusViewModel busViewModel;
    Boolean applyStatus = false;
    Integer idx;
    Integer busType;
    BusRequest request = new BusRequest();
    ArrayList<Type> typeArrayList = new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.bus_apply_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();

        busViewModel.getResponseTypes().observe(this, types -> {

            for (int i = 0; i < types.getTypes().size(); i++) {
                typeArrayList.add(types.getTypes().get(i));
            }

            if (types.getTypes().size() == 3) {
                binding.busCheckBtnOne.setText(typeArrayList.get(0).getName());
                binding.busCheckBtnTwo.setText(typeArrayList.get(1).getName());
                binding.busCheckBtnThree.setText(typeArrayList.get(2).getName());
            } else {
                Toast.makeText(this, "서버 오류", Toast.LENGTH_SHORT).show();
            }

        });

        buttonCheckStatus();


        busViewModel.getData().observe(this, bus -> {
            //Toast.makeText(this, bus.toString(), Toast.LENGTH_SHORT).show();
            idx = bus.getIdx();

            if (typeArrayList.isEmpty()) {
                Toast.makeText(this, "서버 오류 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
            else {
                applyStatus =  (bus.getType().equals(typeArrayList.get(0).getIdx()) || bus.getType().equals(typeArrayList.get(1).getIdx())  || bus.getType().equals(typeArrayList.get(2).getIdx()) || bus.getType() == 0);
                busType = bus.getType();

                initCheckbox(bus.getType());


                Toast.makeText(this, "이미 신청되어있는 역이 있습니다. 다른 역을 신청할 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        busViewModel.getSuccessMessage().observe(this, successMessage -> {
            Intent intent = new Intent(getApplicationContext(), ApplySuccessActivity.class);
            startActivity(intent);
            finish();
        });

        busViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage.equals("The mapper function returned a null value.")) {
                initCheckbox(0);
                applyStatus = false;
            } else {
                Log.e("Error", errorMessage);
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        binding.applyBtn.setOnClickListener(view -> {
            Integer selectedBusType = getSelectedBusType();

            if (!applyStatus)
            {
                if (selectedBusType > 0)
                {
                    request.setType(selectedBusType);
                    busViewModel.postBusApply(request);
                }
                else if (selectedBusType == 0)
                {
                    Toast.makeText(this, "이미 신청되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "행선지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                if (isSameBusType(selectedBusType))
                {
                    Toast.makeText(this, "이미 신청되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (selectedBusType > 0)
                    {
                        request.setType(selectedBusType);
                        busViewModel.putModifyBusApply(idx, request);
                    }
                    else if (selectedBusType == 0)
                    {
                        busViewModel.deleteBusApply(idx);
                    }
                    else
                    {
                        Toast.makeText(this, "행선지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initCheckbox(int busType)
    {
        Log.d("initCheckbox", busType+"");
        binding.busCheckBtnOne.setChecked(false);
        binding.busCheckBtnTwo.setChecked(false);
        binding.busCheckBtnThree.setChecked(false);
        binding.busCheckBtnFour.setChecked(false);

        if(typeArrayList.isEmpty()) return;

        if (busType == typeArrayList.get(0).getIdx()) {
            binding.busCheckBtnOne.setChecked(true);
        } else if (busType == typeArrayList.get(1).getIdx()) {
            binding.busCheckBtnTwo.setChecked(true);
        } else if (busType == typeArrayList.get(2).getIdx()) {
            binding.busCheckBtnThree.setChecked(true);
        } else if (busType == 0) {
            binding.busCheckBtnFour.setChecked(true);
        }
    }

    private int getSelectedBusType()
    {
        if (binding.busCheckBtnOne.isChecked()) {
            return typeArrayList.get(0).getIdx();
        } else if (binding.busCheckBtnTwo.isChecked()) {
            return typeArrayList.get(1).getIdx();
        } else if (binding.busCheckBtnThree.isChecked()) {
            return typeArrayList.get(2).getIdx();
        } else if (binding.busCheckBtnFour.isChecked()) {
            return 0;
        } else {
            return -1;
        }
    }

    private boolean isSameBusType(int busType)
    {
        return busType == this.busType;
    }

    private void buttonCheckStatus() {
        binding.busCheckBtnOne.setOnClickListener(view -> {
            binding.busCheckBtnTwo.setChecked(false);
            binding.busCheckBtnThree.setChecked(false);
            binding.busCheckBtnFour.setChecked(false);
        });

        binding.busCheckBtnTwo.setOnClickListener(view -> {
            binding.busCheckBtnOne.setChecked(false);
            binding.busCheckBtnThree.setChecked(false);
            binding.busCheckBtnFour.setChecked(false);
        });

        binding.busCheckBtnThree.setOnClickListener(view -> {
            binding.busCheckBtnOne.setChecked(false);
            binding.busCheckBtnTwo.setChecked(false);
            binding.busCheckBtnFour.setChecked(false);
        });

        binding.busCheckBtnFour.setOnClickListener(view -> {
            binding.busCheckBtnOne.setChecked(false);
            binding.busCheckBtnTwo.setChecked(false);
            binding.busCheckBtnThree.setChecked(false);
        });
    }

    private void initViewModel() {
        busViewModel = new BusViewModel(this);

        busViewModel.getCurrentBusTypes();
        busViewModel.getMyBusApply();
    }
}
