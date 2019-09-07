package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

    LocationListAdapter locationListAdapter = new LocationListAdapter(null, null, null, null, null);

    Map<Student, List<LocationInfo>> location;

    Object selectItem;
    Time selectedTime;

    ArrayAdapter<String> adapter;

    private boolean placeInitializedView = false;
    private boolean timeInitializedView = false;
    private boolean typeOfSelected = false;
    private ProgressBar progressBar;

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
        setLoadingBar();

        placeViewModel.getAllPlace();
        studentViewModel.getClasses();
        timeTableViewModel.getTimeTable();
        locationViewModel.getLocation();
        observeViewModel();
        setListener();
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
        locationViewModel.getLocation();
        selectedTime = timeList.get(position);
        binding.timeSpinner.setSelectedIndex(position);
        if (classInfos.size() != 0) {
            showSelectOptionSnackbar();
        }
        setListAdapter();
    }

    private void classSelected(int position) {
        locationViewModel.getLocation();
        if (listType == ListType.CLASS) {
            adapter = new ArrayAdapter(this, R.layout.location_check_spinner_item , classInfos);
            adapter.setDropDownViewResource(R.layout.location_check_spinner_item);
            binding.classSpinner.setAdapter(adapter);
            selectItem = classInfos.get(position);
        } else {
            adapter = new ArrayAdapter(this, R.layout.location_check_spinner_item , placeList);
            adapter.setDropDownViewResource(R.layout.location_check_spinner_item);
            binding.classSpinner.setAdapter(adapter);
            selectItem = placeList.get(position);
        }
        binding.classSpinner.setSelectedIndex(position);
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

    private void observeViewModel() {
        locationViewModel.getData().observe(this, location -> {
            this.location = location;

            locationListAdapter = new LocationListAdapter(this, location, listType, selectItem, selectedTime);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            binding.locationList.setLayoutManager(layoutManager);
            binding.locationList.setAdapter(locationListAdapter);

            locationListAdapter.getCheckSelectLocationIdx().observe(this, idx -> {
                if (idx != null){
                    locationViewModel.checkLocation(idx);
                }
            });

            locationListAdapter.getUnCheckSelectLocationIdx().observe(this, idx -> {
                if (idx != null) {
                    locationViewModel.unCheckLocation(idx);
                }
            });
        });

        studentViewModel.getClassInfos().observe(this, classInfoList -> {
            classInfos = classInfoList;

            adapter = new ArrayAdapter(this,R.layout.location_check_spinner_item ,classInfos);
            adapter.setDropDownViewResource(R.layout.location_check_spinner_item);
            binding.classSpinner.setAdapter(adapter);
            selectItem = classInfos.get(0);
        });

        studentViewModel.getErrorMessage().observe(this, message -> Log.d("ErrorMessage", message));

        timeTableViewModel.getData().observe(this, times -> {
            timeList = times;

            adapter = new ArrayAdapter(this,R.layout.location_check_spinner_item ,timeList);
            adapter.setDropDownViewResource(R.layout.location_check_spinner_item);
            binding.timeSpinner.setAdapter(adapter);
            selectedTime = timeList.get(0);
            setTimeSpinnerIndex();
        });

        placeViewModel.getData().observe(this, data -> placeList = data);

        locationViewModel.getCheckStatus().observe(this, status -> {
            if (status) {
                Snackbar.make(binding.locationList, "학생 체크 완료", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(binding.locationList, "학생 체크 오류, 네트워크를 확인해주세요", Snackbar.LENGTH_SHORT).show();
            }
        });

        locationViewModel.getUnCheckStatus().observe(this, status -> {
            if (status) {
                Snackbar.make(binding.locationList,"학생 체크 취소", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(binding.locationList,"학생 체크 취소 오류, 네트워크를 확인해주세요", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setListener() {
        binding.toggle.setOnCheckedChangeListener((view, checkedId) -> {
            selectedTime = timeList.get(0);
            selectItem = placeList.get(0);
            binding.timeSpinner.setSelectedIndex(0);
            binding.classSpinner.setSelectedIndex(0);

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
            setTimeSpinnerIndex();
        });

        binding.classSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            if (!typeOfSelected) {
                selectItem = classInfos.get(position);
            }
            else {
                selectItem = placeList.get(position);
            }
            locationViewModel.getLocation();
            classSelected(position);
            placeInitializedView = false;
        });

        binding.timeSpinner.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            selectedTime = timeList.get(position);
            timeSelected(position);
        });

        binding.locationListRefreshBtn.setOnClickListener(view -> {
            locationViewModel.getLocation();
            showSelectOptionSnackbar();
        });
    }

    private void setTimeSpinnerIndex() {
        for (int i = timeList.size() - 1; i >= 0; i--) {
            try {
                long nowTime = System.currentTimeMillis();
                Date nowDate = new Date(nowTime);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String nowString = simpleDateFormat.format(nowDate);
                Date currentTime = simpleDateFormat.parse(nowString);
                Date listTime = simpleDateFormat.parse(timeList.get(i).getStartTime());

                if (currentTime.after(listTime)) {
                    binding.timeSpinner.setSelectedIndex(i);
                    timeSelected(i);
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLoadingBar() {
//        progressBar = (ProgressBar) binding.ivFrameLoading;
//        Wave wave = new Wave();
//        progressBar.setIndeterminateDrawable(wave);
    }
}
