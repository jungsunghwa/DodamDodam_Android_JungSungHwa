package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Location;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter.PlaceAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter.TimeTableAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.PlaceViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TimeTableViewModel;

public class LocationApplyActivity extends BaseActivity<LocationApplyActivityBinding> {

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

    @Override
    protected int layoutId() {
        return R.layout.location_apply_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
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
                if (timeTable.isEmpty() && location.isEmpty()) {
                    return;
                }
                this.timeTable.put(timeList.get(timePosition), null);
                this.location.remove(timePosition);
                this.location.add(timePosition, null);
                timeTableAdapter.notifyItemChanged(timePosition);
                return;
            }
            this.timeTable.put(timeList.get(timePosition), placeList.get(position));
            this.location.remove(timePosition);
            this.location.add(timePosition, placeList.get(position));
            timeTableAdapter.notifyItemChanged(timePosition);
        });

        binding.locationApplyBtn.setOnClickListener(view -> {
            locationViewModel.postLocation(timeTable);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void observableLocationViewModel() {
        locationViewModel.getIsPostSuccess().observe(this, successMessage -> {
            Intent intent = new Intent(getApplicationContext(), ApplySuccessActivity.class);
            startActivity(intent);
            finish();
        });

        locationViewModel.getStudentLocationValue().observe(this, locationRequest ->{
            
        });

        locationViewModel.getError().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
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

    private void initViewModel() {
        timeTableViewModel = new TimeTableViewModel(this);
        placeViewModel = new PlaceViewModel(this);
        locationViewModel = new LocationViewModel(this);

        locationViewModel.getStudentLocation();
        timeTableViewModel.getTimeTable();
        placeViewModel.getAllPlace();
    }
}
