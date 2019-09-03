package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Type;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Types;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BusApplyActivity;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.BusViewHolder;

public class BusAdapter extends RecyclerView.Adapter<BusViewHolder> {
    List<Type> busList = new ArrayList<>();
    List<Type> busMyApply = new ArrayList<>();
    private BusViewModel busViewModel;
    private BusApplyActivity busApplyActivity;
    boolean checkStatus = false;

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
        Type type = busList.get(position);

        holder.binding.checkItem.setText(type.getBusName());

        if (busMyApply.size() != 0) {
            Type myType = busMyApply.get(0);

            if (type.getIdx().equals(myType.getIdx())) {
                holder.binding.checkItem.setChecked(true);
                checkStatus = true;
            }
        }

        holder.binding.checkItem.setOnClickListener(v -> {
            if (!checkStatus) {
//                busViewModel.postBusApply();
            }
        });
    }

    public void setBusList(List<Type> typeList) {
        busList = typeList;
        notifyDataSetChanged();
    }

    public void setBusMyApply(List<Type> busMyApply) {
        this.busMyApply = busMyApply;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return busList.size();
    }
}
