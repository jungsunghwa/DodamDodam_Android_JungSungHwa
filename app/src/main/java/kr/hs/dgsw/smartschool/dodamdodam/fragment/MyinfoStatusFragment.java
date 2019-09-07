package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MyinfoStatusFragmentBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.PlaceViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TimeTableViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.MyinfoOffBaseAdapter;

public class MyinfoStatusFragment extends BaseFragment<MyinfoStatusFragmentBinding> {

    private MyinfoOffBaseAdapter myinfoOffBaseAdapter;
    private List<OffbaseItem> offbaseItems = new ArrayList<>();

    private TimeTableViewModel timeTableViewModel;
    private PlaceViewModel placeViewModel;
    private LocationViewModel locationViewModel;
    private OffbaseViewModel offbaseViewModel;
    private BusViewModel busViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();
        initData();

        offbaseViewModel.getData().observe(this, offbase -> {
            offbaseItems.clear();
            List<OffbaseItem> offbaseItems = new ArrayList<>();

            for (OffbaseItem offbaseItem: offbase.getAll()) {
                if (offbaseItem.getEndTime().after(new Date())) {
                    offbaseItems.add(offbaseItem);
                }
            }
            myinfoOffBaseAdapter = new MyinfoOffBaseAdapter(getContext());
            myinfoOffBaseAdapter.setList(offbaseItems);
            setRecyclerView();
        });


        // todo 리팩토링 필수
        locationViewModel.getData().observe(this, data -> {
            List<LocationInfo> location = new ArrayList<>();
            for (Student student : data.keySet()) {
                location = data.get(student);
            }

            if (location.isEmpty()) {
                binding.firstTime.setText("X");
                binding.twoTime.setText("위치 정보가");
                binding.threeTime.setText("없습니다.");
                binding.fourTime.setText("X");
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
                    case 5:
                        binding.firstTime.setText(locationInfo.getPlace().getName());
                        binding.firstTimeShowText.setText("09시~12시");
                        binding.firstTimeShowText.setTextSize(13);
                        binding.firstTimeShowText.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                        break;
                    case 6:
                        binding.twoTime.setText(locationInfo.getPlace().getName());
                        binding.twoTimeShowText.setText("12시~15시");
                        binding.twoTimeShowText.setTextSize(13);
                        binding.twoTimeShowText.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                        break;
                    case 7:
                        binding.threeTime.setText(locationInfo.getPlace().getName());
                        binding.threeTimeShowText.setText("15시~18시");
                        binding.threeTimeShowText.setTextSize(13);
                        binding.threeTimeShowText.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                        break;
                    case 8:
                        binding.fourTime.setText(locationInfo.getPlace().getName());
                        binding.fourTimeShowText.setText("18시~21시");
                        binding.fourTimeShowText.setTextSize(13);
                        binding.fourTimeShowText.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                        break;
                }
            }
        });

        busViewModel.getResponseMyBusList().observe(this, myList -> {
            Date tempDate = null;
            Bus recentlyBus = new Bus();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            for (Bus bus: myList) {
                try {
                    if (tempDate == null || tempDate.after(format.parse(bus.getStartDate()))) {
                        tempDate = format.parse(bus.getStartDate());
                        recentlyBus = bus;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            binding.bus.setText(recentlyBus.getBusName());
            binding.busUseTime.setText(recentlyBus.getTimeRequired());
            binding.busRideTime.setText(recentlyBus.getStartDate());
        });

        offbaseViewModel.getErrorMessage().observe(this, message -> Snackbar.make(binding.rootLayout, message, Snackbar.LENGTH_SHORT).show());
    }

    private void initViewModel() {
        offbaseViewModel = ViewModelProviders.of(this).get(OffbaseViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        busViewModel = ViewModelProviders.of(this).get(BusViewModel.class);
        timeTableViewModel = ViewModelProviders.of(this).get(TimeTableViewModel.class);
        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
    }

    private void initData() {
        offbaseViewModel.list();
        timeTableViewModel.getTimeTable();
        placeViewModel.getAllPlace();
        locationViewModel.postLocation();
        busViewModel.getMyBusApply();
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.listOffbase.setAdapter(myinfoOffBaseAdapter);
        binding.listOffbase.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected int layoutId() {
        return R.layout.myinfo_status_fragment;
    }
}
