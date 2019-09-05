package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.ListType;
import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.member.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.LocationViewHolder;

public class LocationListAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private final MutableLiveData<Integer> checkSelectLocationIdx = new MutableLiveData<>();
    private final MutableLiveData<Integer> unCheckSelectLocationIdx = new MutableLiveData<>();

    Map<Student, List<LocationInfo>> location;
    Map<Student, List<LocationInfo>> bindingValue;
    Object selectItem;
    Time selectTime;
    ListType listType;
    Context context;
    private DatabaseHelper helper;

    private List<Student> students;



    public LocationListAdapter(Context context, Map<Student, List<LocationInfo>> location,
                               ListType listType, Object selectItem, Time selectTime) {
        this.context = context;
        this.location = location;
        this.bindingValue = new HashMap<>();
        this.listType = listType;
        this.selectItem = selectItem;
        this.selectTime = selectTime;
        setBindingValue();
        helper = DatabaseHelper.getInstance(context);
    }

    public MutableLiveData<Integer> getCheckSelectLocationIdx() {
        return checkSelectLocationIdx;
    }

    public MutableLiveData<Integer> getUnCheckSelectLocationIdx() {
        return unCheckSelectLocationIdx;
    }

    public void setListType(ListType listType) {
        this.listType = listType;
    }

    public void setSelectItem(Object selectItem) {
        this.selectItem = selectItem;
        setBindingValue();
    }

    public void setSelectTime(Time selectTime) {
        this.selectTime = selectTime;
        setBindingValue();
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LocationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.location_check_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
//        holder.binding.checkLocationCb.setVisibility(View.VISIBLE);
//        holder.binding.checkLocationTv.setVisibility(View.GONE);
        Integer index = 0;

        Student student = students.get(position);
        Place place = null;
        String classInfo;

        for (int i = 0; i < bindingValue.get(student).size(); i++) {
            if (bindingValue.get(student).get(i).getTimetableIdx() == selectTime.getIdx()) {
                place = bindingValue.get(student).get(i).getPlace();
            }
        }

        classInfo = setclassInfo(student);

        holder.binding.locationName.setText(classInfo);

        if (student.getProfileImage() != null) {
            Log.d("ProfileImg", student.getProfileImage());
            Glide.with(context).load(student.getProfileImage()).into(holder.binding.locationImageview);
        }

        if (place == null) holder.binding.locationInfo.setText("장소 - 교실");
        else holder.binding.locationInfo.setText("장소 - " + place.getName());

//        if (listType == ListType.PLACE) {
//            holder.binding.studentClassTv.setVisibility(View.VISIBLE);
//        } else if (listType == ListType.CLASS) {
//            holder.binding.studentClassTv.setVisibility(View.GONE);
//        }

        LocationInfo locationInfo = new LocationInfo();

        for (LocationInfo info : bindingValue.get(students.get(position+index))) {
            if (info.getTimetableIdx() == selectTime.getIdx()) {
                locationInfo = info;
            }
        }
        LocationInfo finalLocationInfo = locationInfo;
        Log.d("LocationInfo", finalLocationInfo.getIdx()+"");

        if (locationInfo.getChecked() != null) {
            holder.binding.locationCheckBox.setChecked(locationInfo.getChecked() == 1);
        }

        if (finalLocationInfo.getIdx() == null) {
            holder.binding.locationCheckBox.setClickable(false);
        } else {
//            holder.binding.locationName.setText(student.getClassInfo().getGrade() + "학년 " + student.getClassInfo().getRoom() + "반 " + student.getNumber() + "번 " + student.getName());
            holder.binding.locationName.setText(classInfo);
        }

        holder.binding.locationItemRoot.setOnClickListener((view) -> {
            if (holder.binding.locationCheckBox.isChecked()) {
                holder.binding.locationCheckBox.setChecked(false);
                unCheckSelectLocationIdx.setValue(finalLocationInfo.getIdx());
            } else {
                holder.binding.locationCheckBox.setChecked(true);
                checkSelectLocationIdx.setValue(finalLocationInfo.getIdx());
            }
        });

    }

    @Override
    public int getItemCount() {
        return bindingValue.size();
    }

    private void setBindingValue() {
        bindingValue.clear();

        if (selectItem == null || selectTime == null) return;

        if (listType == ListType.CLASS) {
            int idx = ((ClassInfo) selectItem).getIdx();

            for (Student student : location.keySet()) {
                if (student.getClassIdx() == idx)
                    bindingValue.put(student, Objects.requireNonNull(location.get(student)));
            }
        }
        if (listType == ListType.PLACE) {
            int idx = ((Place) selectItem).getIdx();

            for (Student student : location.keySet()) {
                List<LocationInfo> locationInfos = location.get(student);

                for (LocationInfo locationInfo : locationInfos) {
                    if (locationInfo.getPlace() == null) continue;
                    if (locationInfo.getTimetableIdx() == selectTime.getIdx() &&
                            locationInfo.getPlaceIdx() == idx) {
                        bindingValue.put(student, Objects.requireNonNull(location.get(student)));
                    }
                }
            }
        }

        Log.e("bindingValue", bindingValue.toString());
        this.students = new ArrayList<>(bindingValue.keySet());

        this.students = Stream.of(students).sortBy(kr.hs.dgsw.b1nd.service.model.Student::getNumber).toList();

        notifyDataSetChanged();
    }

    private String setclassInfo(Student student) {
        String classInfo;
        
        if (student.getNumber() < 10) {
            classInfo = student.getClassInfo().getGrade() + "" + student.getClassInfo().getRoom() + "0" + student.getNumber() + " " + student.getName();
        } else {
            classInfo = student.getClassInfo().getGrade() + "" + student.getClassInfo().getRoom() + "" + student.getNumber() + " " + student.getName();
        }
        
        return classInfo;
    }

}
