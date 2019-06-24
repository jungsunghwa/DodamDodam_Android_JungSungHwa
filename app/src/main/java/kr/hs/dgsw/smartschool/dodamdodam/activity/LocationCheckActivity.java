package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
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

    @Override
    protected int layoutId() {
        return R.layout.location_check_activity;
    }

    @Override
    protected void onCreateTablet(@Nullable Bundle savedInstanceState) {
        super.onCreateTablet(savedInstanceState);

        placeViewModel = new PlaceViewModel(this);
        studentViewModel = new StudentViewModel(this);
        timeTableViewModel = new TimeTableViewModel(this);
        locationViewModel = new LocationViewModel(this);
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

            locationListAdapter.getSelectLocationIdx().observe(this, idx -> {
                if (idx != null)
                    locationViewModel.checkLocation(idx);
            });
        });

        studentViewModel.getClassInfos().observe(this, classInfoList -> {
            classInfos = classInfoList;

            binding.classSpinner.setItems(classInfos);
            selectItem = classInfos.get(0);
        });

        timeTableViewModel.getData().observe(this, times -> {
            timeList = (ArrayList<Time>) times;

            binding.timeSpinner.setItems(timeList);

            selectedTime = timeList.get(0);
        });


        placeViewModel.getData().observe(this, data -> {
            placeList = data;
        });

        binding.toggle.setOnCheckedChangeListener((view, checkedId) -> {
            selectedTime = timeList.get(0);
            binding.timeSpinner.setSelectedIndex(0);
            binding.classSpinner.setSelectedIndex(0);
            switch (checkedId) {
                case R.id.list_type_class:
                    listType = ListType.CLASS;
                    selectItem = classInfos.get(0);
                    break;

                case R.id.list_type_place:
                    listType = ListType.PLACE;
                    selectItem = placeList.get(0);
                    break;
            }
            showSelectOptionSnackbar(view);
        });

        binding.classSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<Object>)
                (view, position, id, item) -> {
                    selectItem = item;
                    showSelectOptionSnackbar(view);
                });

        binding.timeSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<Time>)
                (view, position, id, item) -> {
                    selectedTime = item;
                    showSelectOptionSnackbar(view);
                });

        binding.locationListRefreshBtn.setOnClickListener(view -> {
            locationViewModel.getLocation();
        });
    }

    private void showSelectOptionSnackbar(View v) {
        a();
        Snackbar.make(v, selectedTime + "에 " + selectItem + "의 학생을 조회합니다", Snackbar.LENGTH_LONG).show();
    }

    private void a() {
        if (listType == ListType.CLASS) {
            binding.listTypeClass.setTextColor(Color.WHITE);
            binding.listTypePlace.setTextColor(Color.BLACK);
            binding.listHeaderLayout.studentClassTv.setVisibility(View.GONE);
            binding.classSpinner.setItems(classInfos);
            selectItem = classInfos.get(binding.classSpinner.getSelectedIndex());
        } else {
            binding.listTypeClass.setTextColor(Color.BLACK);
            binding.listTypePlace.setTextColor(Color.WHITE);
            binding.listHeaderLayout.studentClassTv.setVisibility(View.VISIBLE);
            binding.classSpinner.setItems(placeList);
            selectItem = placeList.get(binding.classSpinner.getSelectedIndex());
        }

        locationListAdapter.setListType(listType);
        locationListAdapter.setSelectItem(selectItem);
        locationListAdapter.setSelectTime(selectedTime);
    }

}

