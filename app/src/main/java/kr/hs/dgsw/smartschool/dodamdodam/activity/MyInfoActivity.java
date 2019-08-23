package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MyinfoActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;

public class MyInfoActivity extends BaseActivity<MyinfoActivityBinding> {

    TextView myinfo_bus;

    LocationViewModel locationViewModel;
    OffbaseViewModel offbaseViewModel;
    BusViewModel busViewModel;

    @Override
    protected int layoutId() {
        return R.layout.myinfo_activity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();
        initData();

        offbaseViewModel.getData().observe(this, offbase -> {
            Date passDate = offbase.getPasses().get(0).getStartTime();
            Date leaveDate = offbase.getLeaves().get(0).getStartTime();
            binding.passTime.setText(passDate.toString());
            binding.leaveTime.setText(leaveDate.toString());
        });

        locationViewModel.getData().observe(this, studentListMap -> {

        });

        busViewModel.getData().observe(this, bus -> {
            Integer type = bus.getType();
            busViewModel.getBusType(type);
        });


//        myinfo_bus = findViewById(R.id.bus_type);

//        busViewModel.getData().observe(this, bus -> {
//
//            busType = bus.getType();
//
//            switch (bus.getType())
//            {
//                case 1:
//                  //  myinfo_bus.setText("동대구역");
//                    break;
//                case 2:
//                  //  myinfo_bus.setText("대곡역");
//                    break;
//                case 3:
//                  //  myinfo_bus.setText("용산역");
//                    break;
//            }
//        });
//
//        locationViewModel.getData().observe(this, location -> {
//
//        });
//
//        offbaseViewModel.getData().observe(this, offbase -> {
//
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private void initViewModel() {
        offbaseViewModel = ViewModelProviders.of(this).get(OffbaseViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        busViewModel = ViewModelProviders.of(this).get(BusViewModel.class);
    }

    private void initData() {
        offbaseViewModel.list();
        locationViewModel.getMyLocation();
        busViewModel.getMyBusApply();
    }

}
