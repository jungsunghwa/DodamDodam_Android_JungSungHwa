package kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Time;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.holder.TimeViewHolder;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeViewHolder> {

    private Map<Time, Place> timeTable;
    private List<Time> timeList;
    Context context;

    private final MutableLiveData<Integer> timePosition = new MutableLiveData<Integer>();

    public LiveData<Integer> getTimePosition() {
        return timePosition;
    }

    public TimeTableAdapter(Context context, Map<Time, Place> timeTable, List<Time> timeList) {
        this.context = context;
        this.timeTable = timeTable;
        this.timeList = timeList;
        timePosition.setValue(0);
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
            holder.binding.placeSelectBtn.setChecked(true);
            holder.binding.placeSelectBtn.setTextColor(Color.WHITE);
        } else if (timePosition.getValue() == position){
            holder.binding.placeSelectBtn.setText(time.getName());
            holder.binding.placeSelectBtn.setChecked(true);
            holder.binding.placeSelectBtn.setTextColor(Color.WHITE);
        } else {
            holder.binding.placeSelectBtn.setChecked(false);
            holder.binding.placeSelectBtn.setTextColor(ContextCompat.getColor(context, R.color.textColor));
            holder.binding.placeSelectBtn.setText(time.getName());
        }

        holder.binding.placeSelectBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                timePosition.setValue(position);
                buttonView.setTextColor(Color.WHITE);
                notifyDataSetChanged();
            }else {
                if (place != null){
                    holder.binding.placeSelectBtn.setChecked(true);
                    timePosition.setValue(position);
                    notifyDataSetChanged();
                    return;
                }
                buttonView.setTextColor(ContextCompat.getColor(context, R.color.textColor));
            }
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
