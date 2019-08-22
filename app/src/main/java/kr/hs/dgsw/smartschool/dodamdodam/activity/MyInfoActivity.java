package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.widget.TextView;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;

public class MyInfoActivity extends BaseActivity {

    TextView myinfo_bus;

    private static final Integer applywaiting = 0;
    private static final Integer applycomplete = 1;

    LocationViewModel locationViewModel;
    OffbaseViewModel offbaseViewModel;
    BusViewModel busViewModel;
    Integer idx;
    Integer busType;

    @Override
    protected int layoutId() {
        return R.layout.myinfo_activity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();

        myinfo_bus = findViewById(R.id.bus_type);

        busViewModel.getData().observe(this, bus -> {

            busType = bus.getType();

            switch (bus.getType())
            {
                case 1:
                  //  myinfo_bus.setText("동대구역");
                    break;
                case 2:
                  //  myinfo_bus.setText("대곡역");
                    break;
                case 3:
                  //  myinfo_bus.setText("용산역");
                    break;
            }
        });

        locationViewModel.getData().observe(this, location -> {

        });

        offbaseViewModel.getData().observe(this, offbase -> {

        });
    }

    private void initViewModel() {
        offbaseViewModel = new OffbaseViewModel(this);
        locationViewModel = new LocationViewModel(this);
        busViewModel = new BusViewModel(this);

        offbaseViewModel.list();
        busViewModel.getMyBusApply();
        locationViewModel.getMyLocation();
    }

}
