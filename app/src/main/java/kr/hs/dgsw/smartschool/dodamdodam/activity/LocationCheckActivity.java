package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SpinnerAdapter;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.ListType;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationCheckActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.PlaceViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.StudentViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TimeTableViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter.LocationListAdapter;


public class LocationCheckActivity extends BaseActivity<LocationCheckActivityBinding> {

    ListType listType = ListType.CLASS;

    List<Place> placeList = new ArrayList<>();
    List<ClassInfo> classInfos = new ArrayList<>();
    List<Time> timeList = new ArrayList<>();

    PlaceViewModel placeViewModel;
    StudentViewModel studentViewModel;
    TimeTableViewModel timeTableViewModel;
    LocationViewModel locationViewModel;

    LocationListAdapter locationListAdapter;

    Map<Student, List<LocationInfo>> location;

    Object selectItem;
    Time selectedTime;

    ArrayAdapter<String> adapter;

    private boolean placeInitializedView = false;
    private boolean timeInitializedView = false;
    private boolean typeOfSelected = false;

    @Override
    protected int layoutId() {
        return R.layout.location_check_activity;
    }

    @Override
    protected void onCreateTablet(@Nullable Bundle savedInstanceState) {
        super.onCreateTablet(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        placeViewModel = ViewModelProviders.of(this).get(PlaceViewModel.class);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        timeTableViewModel = ViewModelProviders.of(this).get(TimeTableViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        location = new HashMap<>();

        placeViewModel.getAllPlace();
        studentViewModel.getClasses();
        timeTableViewModel.getTimeTable();
        locationViewModel.getLocation();

        locationViewModel.getData().observe(this, location -> {
            this.location = location;

            locationListAdapter = new LocationListAdapter(this, location, listType, selectItem, selectedTime);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            binding.locationList.setLayoutManager(layoutManager);
            binding.locationList.setAdapter(locationListAdapter);

            locationListAdapter.getCheckSelectLocationIdx().observe(this, idx -> {
                if (idx != null)
                    locationViewModel.checkLocation(idx);
            });

            locationListAdapter.getUnCheckSelectLocationIdx().observe(this, idx -> {
                if (idx != null)
                    locationViewModel.unCheckLocation(idx);
            });
        });

        studentViewModel.getData().observe(this, classInfoList -> {
            classInfos = classInfoList;

//            binding.classSpinner.setItems(classInfos);
            adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item ,classInfos);
            binding.classSpinner.setAdapter(adapter);
            selectItem = classInfos.get(0);
        });

        timeTableViewModel.getData().observe(this, times -> {
            timeList = times;

            adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item ,timeList);
            binding.timeSpinner.setAdapter(adapter);
            selectedTime = timeList.get(0);
        });

        placeViewModel.getData().observe(this, data -> placeList = data);

        binding.toggle.setOnCheckedChangeListener((view, checkedId) -> {
            selectedTime = timeList.get(0);
            selectItem = placeList.get(0);
            binding.timeSpinner.setSelection(0);
            binding.classSpinner.setSelection(0);

            if (!checkedId) {
                listType = ListType.CLASS;
                selectItem = classInfos.get(0);
                binding.toggle.setText("학반별");
                typeOfSelected = false;
            } else {
                listType = ListType.PLACE;
                selectItem = placeList.get(0);
                binding.toggle.setText("장소별");
                typeOfSelected = true;
            }

            classSelected(0);
        });

//        binding.classSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<Object>)
//                (view, position, id, item) -> {
//                    selectItem = item;
//                    locationViewModel.getLocation();
//                    showSelectOptionSnackbar();
//                });


        binding.classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!placeInitializedView) {
                    placeInitializedView = true;
                }
                else {
                    if (!typeOfSelected) {
                        selectItem = classInfos.get(position);
                    }
                    else {
                        selectItem = placeList.get(position);
                    }
                    locationViewModel.getLocation();
                    classSelected(position);
                    placeInitializedView = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        binding.timeSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<Time>)
//                (view, position, id, item) -> {
//                    selectedTime = item;
//                    locationViewModel.getLocation();
//                    showSelectOptionSnackbar();
//                });


        binding.timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!timeInitializedView) {
                    timeInitializedView = true;
                }
                else {
                    selectedTime = timeList.get(position);
                    timeSelected(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.locationListRefreshBtn.setOnClickListener(view -> locationViewModel.getLocation());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void timeSelected(int position) {
//        adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item , timeList);
//        binding.timeSpinner.setAdapter(adapter);
        selectedTime = timeList.get(position);
        binding.timeSpinner.setSelection(position);
        showSelectOptionSnackbar();
        setListAdapter();
    }

    private void classSelected(int position) {
        if (listType == ListType.CLASS) {
            adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item , classInfos);
            binding.classSpinner.setAdapter(adapter);
            selectItem = classInfos.get(position);
        } else {
            adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item , placeList);
            binding.classSpinner.setAdapter(adapter);
            selectItem = placeList.get(position);
        }
        binding.classSpinner.setSelection(position);
        showSelectOptionSnackbar();
        setListAdapter();
    }

    private void setListAdapter() {
        locationListAdapter.setListType(listType);

        locationListAdapter.setSelectItem(selectItem);
        locationListAdapter.setSelectTime(selectedTime);
        binding.locationList.setAdapter(locationListAdapter);
    }

    private void showSelectOptionSnackbar() {
        Snackbar.make(binding.locationList, selectedTime.toString() + "에 " + selectItem.toString() + "의 학생을 조회합니다", Snackbar.LENGTH_LONG).show();
    }
}

