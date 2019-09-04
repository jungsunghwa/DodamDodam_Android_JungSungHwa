package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Type;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Types;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.BusApplyActivity;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.BusRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.BusViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.BusViewHolder;

public class BusAdapter extends RecyclerView.Adapter<BusViewHolder> {

    private final MutableLiveData<Integer> busPosition = new MutableLiveData<>();
    private final MutableLiveData<Integer> postBus = new MutableLiveData<>();
    private final MutableLiveData<BusRequest> putBus = new MutableLiveData<>();
    private final MutableLiveData<Integer> deleteBus = new MutableLiveData<>();

    Integer beforePosition;
    boolean stautus = false;

    List<Type> busList = new ArrayList<>();
    List<Type> busMyApply = new ArrayList<>();

    private BusViewModel busViewModel;
    private BusApplyActivity busApplyActivity;


    public MutableLiveData<Integer> getPostBus() {
        return postBus;
    }

    public MutableLiveData<Integer> getBusPosition() {
        return busPosition;
    }

    public MutableLiveData<BusRequest> getPutBus() {
        return putBus;
    }

    public MutableLiveData<Integer> getDeleteBus() {
        return deleteBus;
    }

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
        Type mType = new Type();
        Type type = busList.get(position);

        holder.binding.checkItem.setOnCheckedChangeListener(null);
        holder.binding.checkItem.setText(type.getBusName());

        if (busMyApply.size() != 0) {
            mType = busMyApply.get(0);

            if (type.getIdx().equals(mType.getIdx())) {
                beforePosition = position;
                holder.binding.checkItem.setChecked(true);
                stautus = true;
            }
        }

        if (busPosition.getValue() != null && busPosition.getValue() == position && stautus) {
            holder.binding.checkItem.setChecked(true);
        } else if (busPosition.getValue() == null) {
            holder.binding.checkItem.setChecked(false);
        } else if (beforePosition != null && beforePosition == position) {
            holder.binding.checkItem.setChecked(false);
        }

        Type finalMType = mType;
        holder.binding.checkItem.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                if (busPosition.getValue() != null) {
                    beforePosition = busPosition.getValue();
                    busPosition.setValue(position);
                    notifyItemChanged(beforePosition);
                    notifyItemChanged(position);
                    putBus.setValue(new BusRequest(type.getIdx().toString(), finalMType.getIdx().toString()));
                } else {
                    busPosition.setValue(position);
                    beforePosition = position;
                    postBus.setValue(type.getIdx());
                }
            } else {
                busPosition.setValue(null);
                beforePosition = null;
                deleteBus.setValue(type.getIdx());
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
