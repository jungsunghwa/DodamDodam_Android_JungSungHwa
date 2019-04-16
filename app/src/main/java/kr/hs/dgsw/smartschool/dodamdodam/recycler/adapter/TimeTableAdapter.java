package kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import kr.hs.dgsw.smartschool.dodamdodam.model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.holder.TimeViewHolder;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeViewHolder> {

    private Map<Time, Place> timeTable;
    private List<Time> timeList;

    private final MutableLiveData<Integer> timePosition = new MutableLiveData<>();

    public LiveData<Integer> getPosition() {
        return timePosition;
    }

    public TimeTableAdapter(Context context, Map<Time, Place> timeTable, List<Time> timeList) {
        this.timeTable = timeTable;
        this.timeList = timeList;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.time_table_item, parent, false));
    }

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

        holder.binding.placeSelectBtn.setOnClickListener(view -> timePosition.setValue(position));
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
