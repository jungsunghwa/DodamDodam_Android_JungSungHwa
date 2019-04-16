package kr.hs.dgsw.smartschool.dodamdodam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kr.hs.dgsw.smartschool.dodamdodam.model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter.PlaceAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter.TimeTableAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.PlaceViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TimeTableViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationApplyActivity extends AppCompatActivity {

    LocationApplyActivityBinding binding;

    TimeTableViewModel timeTableViewModel;
    PlaceViewModel placeViewModel;
    LocationViewModel locationViewModel;

    TimeTableAdapter timeTableAdapter;
    PlaceAdapter placeAdapter;


    Map<Time, Place> timeTable = new HashMap<>();
    List<Time> timeList = new ArrayList<>();
    List<Place> placeList = new ArrayList<>();
    List<Place> location = new ArrayList<>();

    int timePosition;
    int palcePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_apply_activity);
        binding = DataBindingUtil.setContentView(this, R.layout.location_apply_activity);

        initViewModel();

        setTimeTableRecyclerView();
        setPlaceRecyclerView();

        observableTimeTableViewModel();
        observablePlaceViewModel();
        observableLocationViewModel();

        timeTableAdapter.getPosition().observe(this, postion -> {
            binding.placeRecyclerView.setVisibility(View.VISIBLE);
            timePosition = postion;
        });

        placeAdapter.getPosition().observe(this, postion -> {
            binding.placeRecyclerView.setVisibility(View.GONE);
            this.timeTable.put(timeList.get(timePosition), placeList.get(postion));
            this.location.remove(timePosition);
            this.location.add(timePosition, placeList.get(postion));
            timeTableAdapter.notifyItemChanged(timePosition);
        });

        binding.locationApplyBtn.setOnClickListener(view -> locationViewModel.postLocation(location));


    }

    private void observableLocationViewModel() {
        locationViewModel.getIsPostSuccess().observe(this, successMessage -> Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show());
    }

    private void setPlaceRecyclerView() {
        placeAdapter = new PlaceAdapter(this, placeList);
        RecyclerView.LayoutManager placeRecyclerViewLayoutManager
                = new GridLayoutManager(this, 3);

        binding.placeRecyclerView.setAdapter(placeAdapter);
        binding.placeRecyclerView.setLayoutManager(placeRecyclerViewLayoutManager);
    }

    private void setTimeTableRecyclerView(){
        timeTableAdapter = new TimeTableAdapter(this, timeTable, timeList);

        LinearLayoutManager timeTableRecyclerViewLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        binding.timeTableRecyclerView.setAdapter(timeTableAdapter);
        binding.timeTableRecyclerView.setLayoutManager(timeTableRecyclerViewLayoutManager);
        binding.timeTableRecyclerView.setNestedScrollingEnabled(false);
    }

    private void observablePlaceViewModel() {
        placeViewModel.getIsSuccess().observe(this, placeList -> {

            this.placeList.clear();
            this.placeList.addAll(placeList);

            placeAdapter.notifyDataSetChanged();
        });
    }

    private void observableTimeTableViewModel() {
        timeTableViewModel.getError().observe(this, error -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        timeTableViewModel.getIsSuccess().observe(this, timeList -> {
            location.clear();

            int i = 0;
            for (Time time : timeList) {
                location.add(i++, new Place());
            }

            this.timeList.clear();
            this.timeList.addAll(timeList);

            timeTable.clear();
            for (Time time : timeList) {
                timeTable.put(time, null);
            }

            timeTableAdapter.notifyDataSetChanged();
        });
    }

    private void initViewModel() {
        timeTableViewModel = new TimeTableViewModel(this);
        placeViewModel = new PlaceViewModel(this);
        locationViewModel = new LocationViewModel(this);

        timeTableViewModel.getTimeTable();
        placeViewModel.getAllPlace();
    }
}
