package kr.hs.dgsw.smartschool.dodamdodam.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

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
            offbaseItems.addAll(offbase.getLeaves());
            offbaseItems.addAll(offbase.getPasses());
            myinfoOffBaseAdapter = new MyinfoOffBaseAdapter(getContext());
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

//        busViewModel.getData().observe(this, bus -> busViewModel.getBusType(bus.getType()));

//        busViewModel.getResponseType().observe(this, type -> {
//            binding.bus.setText(type.getName() + "역");
//
//            switch (type.getName()) {
//                case "동대구":
//                    binding.busUseTime.setText("1시간");
//                    break;
//                case "용산":
//                    binding.busUseTime.setText("35분");
//                    break;
//                case "대곡":
//                    binding.busUseTime.setText("30분");
//                    break;
//            }
//
//            String arriveTIme = type.getArrive_time().split(":")[0] + ":" + type.getArrive_time().split(":")[1];
//            binding.busRideTime.setText(arriveTIme);
//        });
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
