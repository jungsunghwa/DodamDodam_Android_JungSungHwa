package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.model.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.ClassInfoList;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseGetDataType;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationCheckActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.PlaceViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.StudentViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.TimeTableViewModel;


public class LocationCheckActivity extends BaseActivity<LocationCheckActivityBinding> {

    ListType listType;

    ArrayList<Place> placeList = new ArrayList<>();
    ArrayList<ClassInfo> classInfos = new ArrayList<>();
    ArrayList<Time> timeList = new ArrayList<>();

    PlaceViewModel placeViewModel;
    StudentViewModel studentViewModel;
    TimeTableViewModel timeTableViewModel;
    LocationViewModel locationViewModel;

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

        placeViewModel.getAllPlace();
        studentViewModel.getClasses();
        timeTableViewModel.getTimeTable();
        locationViewModel.getLocation();

        timeTableViewModel.getData().observe(this, times -> {
            timeList = (ArrayList<Time>) times;

            binding.timeSpinner.setItems(timeList);

            selectedTime = timeList.get(0);
        });

        studentViewModel.getClassInfos().observe(this, classInfoList->{
            classInfos = classInfoList;

            binding.classSpinner.setItems(classInfos);
            selectItem = classInfos.get(0);
        });

        placeViewModel.getData().observe(this, data -> {
            placeList = (ArrayList<Place>) data;
        });

        binding.toggle.setOnCheckedChangeListener((view, checkedId) -> {
            switch (checkedId) {
                case R.id.list_type_class:
                    binding.listTypeClass.setTextColor(Color.WHITE);
                    binding.listTypePlace.setTextColor(Color.BLACK);
                    binding.classSpinner.setItems(classInfos);
                    listType = ListType.CLASS;
                    selectItem = classInfos.get(binding.classSpinner.getSelectedIndex());
                    showSelectOptionSnackbar(view);
                    break;

                case R.id.list_type_place:
                    binding.listTypeClass.setTextColor(Color.BLACK);
                    binding.listTypePlace.setTextColor(Color.WHITE);
                    binding.classSpinner.setItems(placeList);
                    selectItem = placeList.get(binding.classSpinner.getSelectedIndex());
                    showSelectOptionSnackbar(view);
                    listType = ListType.PLACE;
                    break;
            }
        });

        binding.classSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<Object>)
                (view, position, id, item) ->{
                    selectItem = item;
                    showSelectOptionSnackbar(view);
                });

        binding.timeSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<Time>)
                (view, position, id, item) ->{
                    selectedTime = item;
                    showSelectOptionSnackbar(view);
                });

    }

    private void showSelectOptionSnackbar(View v) {
        Snackbar.make(v, selectedTime+"에 "+ selectItem+"의 학생을 조회합니다", Snackbar.LENGTH_LONG).show();
    }

}

enum ListType {
    PLACE,
    CLASS
}
