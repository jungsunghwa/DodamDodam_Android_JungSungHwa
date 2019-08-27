package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Offbase;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MyinfoActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.MyinfoOffBaseAdapter;

public class MyInfoActivity extends BaseActivity<MyinfoActivityBinding> {

    private MyinfoOffBaseAdapter myinfoOffBaseAdapter;
    private List<OffbaseItem> offbaseItems = new ArrayList<>();

    private LocationViewModel locationViewModel;
    private OffbaseViewModel offbaseViewModel;
    private BusViewModel busViewModel;

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
            offbaseItems.clear();
            for (Leave leave: offbase.getLeaves()) {
                offbaseItems.add(leave);
            }
            for (Pass pass: offbase.getPasses()) {
                offbaseItems.add(pass);
            }
            myinfoOffBaseAdapter = new MyinfoOffBaseAdapter(this);
            myinfoOffBaseAdapter.setList(offbaseItems);
            setRecyclerView();
        });

        locationViewModel.getData().observe(this, data -> {
            List<LocationInfo> location = new ArrayList<>();
            for (Student student : data.keySet()) {
                location = data.get(student);
            }
            for (LocationInfo locationInfo: location) {
                switch (locationInfo.getTimetableIdx()) {
                    case 1:
                        binding.firstTime.setText(locationInfo.getPlace().getName());
                        break;
                    case 2:
                        binding.twoTime.setText(locationInfo.getPlace().getName());
                        break;
                    case 3:
                        binding.threeTime.setText(locationInfo.getPlace().getName());
                        break;
                    case 4:
                        binding.fourTime.setText(locationInfo.getPlace().getName());
                        break;
                }
            }
        });

        busViewModel.getData().observe(this, bus -> busViewModel.getBusType(bus.getType()));

        busViewModel.getResponseType().observe(this, type -> {
            binding.bus.setText(type.getName() + "역");

            switch (type.getName()) {
                case "동대구":
                    binding.busUseTime.setText("1시간");
                    break;
                case "용산":
                    binding.busUseTime.setText("35분");
                    break;
                case "대곡":
                    binding.busUseTime.setText("30분");
                    break;
            }

            String arriveTIme = type.getArrive_time().split(":")[0] + ":" + type.getArrive_time().split(":")[1];
            binding.busRideTime.setText(arriveTIme);
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

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.listOffbase.setAdapter(myinfoOffBaseAdapter);
        binding.listOffbase.setLayoutManager(linearLayoutManager);
    }

}
