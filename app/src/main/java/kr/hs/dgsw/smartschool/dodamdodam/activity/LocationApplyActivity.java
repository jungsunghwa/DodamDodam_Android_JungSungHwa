package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationApplyActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.PlaceViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TimeTableViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.PlaceAdapter;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.TimeTableAdapter;

public class LocationApplyActivity extends BaseActivity<LocationApplyActivityBinding> {

    TimeTableViewModel timeTableViewModel;
    PlaceViewModel placeViewModel;
    LocationViewModel locationViewModel;

    TimeTableAdapter timeTableAdapter;
    PlaceAdapter placeAdapter;

    List<LocationInfo> timeTable = new ArrayList<>();
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

        observableTimeTableViewModel();
        observablePlaceViewModel();
        observableLocationViewModel();

        binding.locationApplyBtn.setOnClickListener(view -> {
            locationViewModel.postLocation(timeTable);
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

    private void observableLocationViewModel() {
        locationViewModel.getSuccessMessage().observe(this, successMessage -> {
            Intent intent = new Intent(getApplicationContext(), ApplySuccessActivity.class);
            startActivity(intent);
            finish();
        });

        locationViewModel.getData().observe(this, data -> {
            List<LocationInfo> location = new ArrayList<>();
            for (Student student : data.keySet()) {
                location = data.get(student);
            }

            for (int i = 0; i < timeTable.size(); i++) {
                for (LocationInfo info : location) {
                    if (timeTable.get(i).getTimetableIdx() == info.getTimetableIdx()) {
                        timeTable.remove(i);
                        timeTable.add(i, info);
                    }
                }
            }

            setPlaceRecyclerView();
            setTimeTableRecyclerView();
        });

        locationViewModel.getErrorMessage().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
    }

    private void setPlaceRecyclerView() {
        placeAdapter = new PlaceAdapter(this, placeList);
        binding.placeRecyclerView.setAdapter(placeAdapter);

        placeAdapter.getPlacePosition().observe(this, position -> {
            if (position == null) {
                if (timeTable.isEmpty() && location.isEmpty()) {
                    return;
                }
                LocationInfo locationInfo = timeTable.get(timePosition);
                locationInfo.setPlace(null);


                this.timeTable.remove(timePosition);
                this.timeTable.add(timePosition, locationInfo);
                this.location.remove(timePosition);
                this.location.add(timePosition, null);

                timeTableAdapter.notifyItemChanged(timePosition);
                return;
            }
            Time time = timeList.get(timePosition);
            Place place = placeList.get(position);

            LocationInfo locationInfo = timeTable.get(timePosition);
            locationInfo.setPlace(place);


            this.timeTable.remove(timePosition);
            this.timeTable.add(timePosition, locationInfo);

            this.location.remove(timePosition);
            this.location.add(timePosition, place);

            timeTableAdapter.notifyItemChanged(timePosition);
        });
    }

    private void setTimeTableRecyclerView() {
        timeTableAdapter = new TimeTableAdapter(this, timeTable, timeList);

        binding.timeTableRecyclerView.setAdapter(timeTableAdapter);
        binding.timeTableRecyclerView.setNestedScrollingEnabled(false);

        timeTableAdapter.getTimePosition().observe(this, position -> {
            if (position == null) return;

            timePosition = position;

            placeAdapter.notifyDataSetChanged();

            Place place = timeTable.get(position).getPlace();

            binding.selectPlaceTv.setText(timeList.get(position).getName());

            if (place == null) {
                placeAdapter.setPosition(null);
                return;
            }

            for (int j = 0; j < placeList.size(); j++) {
                if (placeList.get(j).getIdx().equals(place.getIdx())) {
                    placeAdapter.setPosition(j);
                    placeAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void observablePlaceViewModel() {
        placeViewModel.getData().observe(this, placeList -> {

            this.placeList.clear();
            this.placeList.addAll(placeList);
        });
    }

    private void observableTimeTableViewModel() {
        timeTableViewModel.getError().observe(this, error -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        timeTableViewModel.getData().observe(this, timeList -> {
            location.clear();

            int i = 0;
            for (Time time : timeList) {
                location.add(i++, new Place());
            }

            this.timeList.clear();
            this.timeList.addAll(timeList);

            timeTable.clear();
            for (Time time : timeList) {
                timeTable.add(new LocationInfo(time, null));
            }
        });
    }

    private void initViewModel() {
        timeTableViewModel = new TimeTableViewModel(this);
        placeViewModel = new PlaceViewModel(this);
        locationViewModel = new LocationViewModel(this);

        timeTableViewModel.getTimeTable();
        placeViewModel.getAllPlace();
        locationViewModel.getLocation();
    }
}
