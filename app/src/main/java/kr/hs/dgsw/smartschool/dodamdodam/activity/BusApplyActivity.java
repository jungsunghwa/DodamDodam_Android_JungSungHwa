package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

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
        buttonCheckStatus();


        busViewModel.getResponse().observe(this, bus -> {
            //Toast.makeText(this, bus.toString(), Toast.LENGTH_SHORT).show();
            idx = bus.getIdx();

            Log.e("response", bus.getType().toString());

            applyStatus =  (bus.getType() == 1 || bus.getType() == 2  || bus.getType() == 3 || bus.getType() == 0);
            busType = bus.getType();

            initCheckbox(bus.getType());

            String busTypeMessage = "";

            switch (bus.getType()) {
                case 1 :
                    busTypeMessage = "동대구";
                    break;
                case 2 :
                    busTypeMessage = "대곡";
                    break;
                case 3 :
                    busTypeMessage = "용산";
                    break;
            }

            Toast.makeText(this, busTypeMessage+"역이 이미 신청되어있습니다.", Toast.LENGTH_SHORT).show();
        });

        busViewModel.getSuccessMessage().observe(this, successMessage -> {
            Intent intent = new Intent(getApplicationContext(), ApplySuccessActivity.class);
            startActivity(intent);
            finish();
        });

        busViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage.equals("신청을 찾을 수 없습니다")) {
                initCheckbox(0);
                applyStatus = false;
            } else {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("error", errorMessage);
            }
        });

        binding.applyBtn.setOnClickListener(view -> {
            int selectedBusType = getSelectedBusType();

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
                        Log.e("type", selectedBusType+""+idx);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private void initCheckbox(int busType)
    {
        binding.eastdaeguCheckBtn.setChecked(false);
        binding.daekokCheckBtn.setChecked(false);
        binding.yongsanCheckBtn.setChecked(false);
        binding.notbusCheckBtn.setChecked(false);

        switch (busType)
        {
            case 1:
                binding.eastdaeguCheckBtn.setChecked(true);
                break;

            case 2:
                binding.daekokCheckBtn.setChecked(true);
                break;

            case 3:
                binding.yongsanCheckBtn.setChecked(true);
                break;

            default:
                binding.notbusCheckBtn.setChecked(true);
                break;
        }
    }

    private int getSelectedBusType()
    {
        if (binding.eastdaeguCheckBtn.isChecked()) {
            return 1;
        } else if (binding.daekokCheckBtn.isChecked()) {
            return 2;
        } else if (binding.yongsanCheckBtn.isChecked()) {
            return 3;
        } else if (binding.notbusCheckBtn.isChecked()) {
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
        binding.eastdaeguCheckBtn.setOnClickListener(view -> {
            binding.daekokCheckBtn.setChecked(false);
            binding.yongsanCheckBtn.setChecked(false);
            binding.notbusCheckBtn.setChecked(false);
        });

        binding.daekokCheckBtn.setOnClickListener(view -> {
            binding.eastdaeguCheckBtn.setChecked(false);
            binding.yongsanCheckBtn.setChecked(false);
            binding.notbusCheckBtn.setChecked(false);
        });

        binding.yongsanCheckBtn.setOnClickListener(view -> {
            binding.daekokCheckBtn.setChecked(false);
            binding.eastdaeguCheckBtn.setChecked(false);
            binding.notbusCheckBtn.setChecked(false);
        });

        binding.notbusCheckBtn.setOnClickListener(view -> {
            binding.daekokCheckBtn.setChecked(false);
            binding.yongsanCheckBtn.setChecked(false);
            binding.eastdaeguCheckBtn.setChecked(false);
        });
    }

    private void initViewModel() {
        busViewModel = new BusViewModel(this);

        busViewModel.getMyBusApply();
    }
}
