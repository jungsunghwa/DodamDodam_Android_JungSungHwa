package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Stream;

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
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.LocationViewHolder;

public class LocationListAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private final MutableLiveData<Integer> selectLocationIdx = new MutableLiveData<>();
    Map<Student, List<LocationInfo>> location;
    Map<Student, List<LocationInfo>> bindingValue;
    Object selectItem;
    Time selectTime;
    ListType listType;
    Context context;
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
    }

    public LiveData<Integer> getSelectLocationIdx() {
        return selectLocationIdx;
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
        return new LocationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.binding.checkLocationCb.setVisibility(View.VISIBLE);
        holder.binding.checkLocationTv.setVisibility(View.GONE);

        Student student = students.get(position);
        Place place = null;

        for (int i = 0; i < bindingValue.get(student).size(); i++) {
            if (bindingValue.get(student).get(i).getTimetableIdx() == selectTime.getIdx()) {
                place = bindingValue.get(student).get(i).getPlace();
            }
        }

        holder.binding.studentNumberTv.setText(String.valueOf(student.getNumber()));
        holder.binding.studentNameTv.setText(student.getName());

        if (place == null) holder.binding.locationNameTv.setText("교실");
        else holder.binding.locationNameTv.setText(place.getName());

        if (listType == ListType.PLACE) {
            holder.binding.studentClassTv.setVisibility(View.VISIBLE);
        } else if (listType == ListType.CLASS) {
            holder.binding.studentClassTv.setVisibility(View.GONE);
        }

        LocationInfo locationInfo = new LocationInfo();

        for (LocationInfo info : bindingValue.get(students.get(position))) {
            if (info.getTimetableIdx() == selectTime.getIdx()) {
                locationInfo = info;
            }
        }
        LocationInfo finalLocationInfo = locationInfo;

        if (locationInfo.getChecked() != null)
            holder.binding.checkLocationCb.setChecked(locationInfo.getChecked() == 1);
        else holder.binding.checkLocationCb.setChecked(false);

        holder.binding.checkLocationCb.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                selectLocationIdx.setValue(finalLocationInfo.getIdx());
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
}
