package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter.PlaceAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter.TimeTableAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.PlaceViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TimeTableViewModel;

public class LocationApplyActivity extends BaseActivity<LocationApplyActivityBinding> {

    LocationApplyActivityBinding binding;

    TimeTableViewModel timeTableViewModel;
    PlaceViewModel placeViewModel;
    LocationViewModel locationViewModel;

    TimeTableAdapter timeTableAdapter;
    PlaceAdapter placeAdapter;


    Map<Time, Place> timeTable = new HashMap<>();
    ArrayList<Time> timeList = new ArrayList<>();
    ArrayList<Place> placeList = new ArrayList<>();
    ArrayList<Place> location = new ArrayList<>();

    int timePosition;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_apply_activity);
        binding = DataBindingUtil.setContentView(this, R.layout.location_apply_activity);

        setSupportActionBar(binding.appbarLayout.toolbar);

        ActionBar actionBar = getSupportActionBar();

        binding.appbarLayout.toolbar.setTitle("랩실신청");

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        initViewModel();

        setTimeTableRecyclerView();
        setPlaceRecyclerView();

        observableTimeTableViewModel();
        observablePlaceViewModel();
        observableLocationViewModel();

        timeTableAdapter.getTimePosition().observe(this, position -> {
            if (position == null) return;

            placeAdapter.notifyDataSetChanged();
            timePosition = position;

            placeAdapter.setPosition(null);
        });

        placeAdapter.getPlacePosition().observe(this, position -> {
            if (position == null) {
                if (timeTable.isEmpty() && location.isEmpty()){
                    return;
                }
                this.timeTable.put(timeList.get(timePosition), null);
                this.location.remove(timePosition);
                this.location.add(timePosition,null);
                timeTableAdapter.notifyItemChanged(timePosition);
                return;
            }
            this.timeTable.put(timeList.get(timePosition), placeList.get(position));
            this.location.remove(timePosition);
            this.location.add(timePosition, placeList.get(position));
            timeTableAdapter.notifyItemChanged(timePosition);
        });

        binding.locationApplyBtn.setOnClickListener(view -> locationViewModel.postLocation(location));


    }

    @Override
    protected int layoutId() {
        return R.layout.location_apply_activity;
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

    private void setTimeTableRecyclerView() {
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViewModel() {
        timeTableViewModel = new TimeTableViewModel(this);
        placeViewModel = new PlaceViewModel(this);
        locationViewModel = new LocationViewModel(this);

        timeTableViewModel.getTimeTable();
        placeViewModel.getAllPlace();
    }
}
