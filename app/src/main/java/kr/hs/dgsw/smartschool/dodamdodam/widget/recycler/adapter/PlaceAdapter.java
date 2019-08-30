package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.Model.location.LocationInfo;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.PlaceViewHolder;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
    List<Place> placeList;
    List<LocationInfo> locations;
    Integer beforePosition;
    Context context;

    private final MutableLiveData<Integer> placePosition = new MutableLiveData<>();
    private final MutableLiveData<Integer> putLocation = new MutableLiveData<>();
    private final MutableLiveData<Integer> deleteLocation = new MutableLiveData<>();

    public LiveData<Integer> getPlacePosition() {
        return placePosition;
    }
    public void setPosition(Integer position) {
        placePosition.setValue(position);
    }

    public MutableLiveData<Integer> getPutLocation(){
        return putLocation;
    }

    public MutableLiveData<Integer> getDeleteLocation() {
        return deleteLocation;
    }

    public PlaceAdapter(Context context, List<Place> placeList, LocationViewModel locationViewModel) {
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

        if (placePosition.getValue() != null && placePosition.getValue() == position) {
            holder.binding.placeCheckBox.setChecked(true);
            holder.binding.placeCheckBox.setTextColor(Color.WHITE);
        } else if (placePosition.getValue() == null) {
            holder.binding.placeCheckBox.setChecked(false);
            holder.binding.placeCheckBox.setTextColor(ContextCompat.getColor(context, R.color.textColor));
        } else if (beforePosition != null && beforePosition == position) {
            holder.binding.placeCheckBox.setChecked(false);
            holder.binding.placeCheckBox.setTextColor(ContextCompat.getColor(context, R.color.textColor));
        }

        holder.binding.placeCheckBox.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                if (placePosition.getValue() != null) {
                    beforePosition = placePosition.getValue();
                    placePosition.setValue(position);
                    view.setTextColor(Color.WHITE);
                    notifyItemChanged(beforePosition);
                    notifyItemChanged(position);
                    putLocation.setValue(place.getIdx());
                }else {
                    placePosition.setValue(position);
                    beforePosition = position;
                    view.setTextColor(Color.WHITE);
                    putLocation.setValue(place.getIdx());
                }
            } else {
                placePosition.setValue(null);
                beforePosition = null;
                view.setTextColor(ContextCompat.getColor(context, R.color.textColor));
                deleteLocation.setValue(place.getIdx());
            }
        });
        holder.binding.placeCheckBox.setOnLongClickListener(view -> {
            view.setSelected(true);
            new Handler().postDelayed(() -> view.setSelected(false), 3000);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
