package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.Bus;
import kr.hs.dgsw.smartschool.dodamdodam.Model.bus.BusResponse;
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
    boolean sizeStautus = false;

    List<BusResponse> busList = new ArrayList<>();
    List<Bus> busMyApply = new ArrayList<>();

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

    public BusAdapter(List<BusResponse> busList, BusViewModel busViewModel, BusApplyActivity busApplyActivity) {
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
        Bus mBus = new Bus();
        Bus bus = busList.get(0).getBues().get(0);

        holder.binding.checkItem.setOnCheckedChangeListener(null);
        holder.binding.checkItem.setText(bus.getBusName());

        if (busMyApply.size() != 0) {
            mBus = busMyApply.get(0);

            if (bus.getIdx().equals(mBus.getIdx())) {
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

        Bus finalMBus = mBus;
        holder.binding.checkItem.setOnCheckedChangeListener((view, isChecked) -> {
            if (isChecked) {
                if (busPosition.getValue() != null) {
                    beforePosition = busPosition.getValue();
                    busPosition.setValue(position);
                    notifyItemChanged(beforePosition);
                    notifyItemChanged(position);
                    putBus.setValue(new BusRequest(bus.getIdx().toString(), finalMBus.getIdx().toString()));
                } else {
                    busPosition.setValue(position);
                    beforePosition = position;
                    postBus.setValue(bus.getIdx());
                }
            } else {
                busPosition.setValue(null);
                beforePosition = null;
                deleteBus.setValue(bus.getIdx());
            }
        });
    }

    public void setBusList(List<BusResponse> busList) {
        this.busList = busList;
        notifyDataSetChanged();
    }

    public void setBusMyApply(List<Bus> busMyApply) {
        this.busMyApply = busMyApply;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (!sizeStautus) {
            sizeStautus = true;
            return busList.size();
        }
        else return busList.get(0).getBues().size();
    }
}
