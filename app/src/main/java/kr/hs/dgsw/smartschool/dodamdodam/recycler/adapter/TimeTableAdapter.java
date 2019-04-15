package kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.holder.TimeViewHolder;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeViewHolder> {

    private Map<Time, Place> timeTable;
    private ArrayList<Time> timeList;

    private final MutableLiveData<Integer> timePosition = new MutableLiveData<>();

    public LiveData<Integer> getPosition() {
        return timePosition;
    }

    @SuppressLint("CheckResult")
    public TimeTableAdapter(Context context, Map<Time, Place> timeTable, ArrayList<Time> timeList) {
        this.timeTable = timeTable;
        this.timeList = timeList;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.time_table_item, parent, false));
    }

    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {

        Time time = this.timeList.get(position);
        Place place = this.timeTable.get(time);

        if (place != null) {
            holder.binding.placeSelectBtn.setText(place.getName());
        }else {
            holder.binding.placeSelectBtn.setText(time.getName());
        }

        holder.binding.startTimeTv.setText(time.getStartTime());
        holder.binding.endTimeTv.setText(time.getEndTime());

        holder.binding.placeSelectBtn.setOnClickListener(view -> {
            timePosition.setValue(position);
        });
    }

    @Override
    public long getItemId(int position) {
        return timeList.get(position).getIdx();
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }
}
