package kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.holder.PlaceViewHolder;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.holder.TimeViewHolder;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {
    ArrayList<Place> placeList = new ArrayList<>();

    private final MutableLiveData<Integer> placePosition = new MutableLiveData<>();

    public LiveData<Integer> getPosition() {
        return placePosition;
    }

    public PlaceAdapter(Context context, ArrayList<Place> placeList) {
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlaceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {
        holder.binding.placeNameTv.setText(placeList.get(position).getName());
        holder.binding.placeNameTv.setOnClickListener(view -> {
            placePosition.setValue(position);
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }
}
