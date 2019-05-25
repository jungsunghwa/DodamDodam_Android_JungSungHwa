package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.PlaceViewHolder;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
    List<Place> placeList;
    Integer beforePosition;
    Context context;

    private final MutableLiveData<Integer> placePosition = new MutableLiveData<>();

    public LiveData<Integer> getPlacePosition() {
        return placePosition;
    }
    public void setPosition(Integer position) {
        placePosition.setValue(position);
    }

    public PlaceAdapter(Context context, List<Place> placeList) {
        this.context = context;
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {

        Place place = placeList.get(position);

        holder.binding.placeCheckBox.setOnCheckedChangeListener(null);
        holder.binding.placeCheckBox.setTextColor(ContextCompat.getColor(context, R.color.textColor));

        holder.binding.placeCheckBox.setText(place.getName());
        holder.binding.placeCheckBox.setChecked(false);

        if (placePosition.getValue() != null && placePosition.getValue() == position){
            holder.binding.placeCheckBox.setChecked(true);
            holder.binding.placeCheckBox.setTextColor(Color.WHITE);
        }else if(placePosition.getValue() == null ){
            holder.binding.placeCheckBox.setChecked(false);
            holder.binding.placeCheckBox.setTextColor(ContextCompat.getColor(context, R.color.textColor));
        }else if (beforePosition != null && beforePosition == position) {
            holder.binding.placeCheckBox.setChecked(false);
            holder.binding.placeCheckBox.setTextColor(ContextCompat.getColor(context, R.color.textColor));
        }

        holder.binding.placeCheckBox.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked){
                if (placePosition.getValue() != null ) {
                    beforePosition = placePosition.getValue();
                    placePosition.setValue(position);
                    view.setTextColor(Color.WHITE);
                    notifyItemChanged(beforePosition);
                    notifyItemChanged(position);
                }else {
                    placePosition.setValue(position);
                    beforePosition = position;
                    view.setTextColor(Color.WHITE);
                }
            }else {
                placePosition.setValue(null);
                beforePosition = null;
//                notifyItemChanged(beforePosition);
                view.setTextColor(ContextCompat.getColor(context, R.color.textColor));
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}