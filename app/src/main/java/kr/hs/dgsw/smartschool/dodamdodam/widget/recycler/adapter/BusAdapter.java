package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Type;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BusApplyActivity;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.BusViewHolder;

public class BusAdapter extends RecyclerView.Adapter<BusViewHolder> {
    List<Type> busList;
    private BusViewModel busViewModel;
    private BusApplyActivity busApplyActivity;

    public BusAdapter(List<Type> busList, BusViewModel busViewModel, BusApplyActivity busApplyActivity) {
        this.busList = busList;
        this.busViewModel = busViewModel;
        this.busApplyActivity = busApplyActivity;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BusViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return busList.size();
    }
}
