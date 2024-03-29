package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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

    private boolean isCheckToday = false;

    private Context context;

    List<Bus> busList = new ArrayList<>();
    List<Bus> busMyApply = new ArrayList<>();


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

    public BusAdapter() {
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BusViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {
        Bus bus = busList.get(position);

        holder.binding.checkItem.setOnCheckedChangeListener(null);
        holder.binding.checkItem.setText(bus.getBusName());

        if (busMyApply.size() != 0) {
            for (Bus myBus: busMyApply) {
                if (bus.getIdx().equals(myBus.getIdx())) {
                    holder.binding.checkItem.setChecked(true);
                    break;
                }
            }
        }

        holder.binding.checkItem.setOnCheckedChangeListener(new OnSingleCheckChangeListener() {
            @Override
            public void onSingleClick(CompoundButton view, boolean isChecked) {
                if (isChecked) {
                    if (isCheckToday) {
                        Toast.makeText(context, "하루에 2개 이상 버스를 신청 할 수 없습니다.",Toast.LENGTH_SHORT).show();
                        holder.binding.checkItem.setChecked(false);
                    } else {
                        postBus.setValue(bus.getIdx());
                    }
                    isCheckToday = true;
                } else {
                    deleteBus.setValue(bus.getIdx());
                    isCheckToday = false;
                }
            }

            @Override
            public Context context() {
                return context;
            }
        });
    }

    public void setBusList(List<Bus> busList) {
        this.busList = busList;
        isCheckToday = false;
        notifyDataSetChanged();
    }

    public void setBusMyApply(List<Bus> busMyApply) {
        this.busMyApply = busMyApply;
        isCheckToday = false;
        for (Bus bus: busList) {
            for (Bus myBus: busMyApply) {
                if (bus.getIdx().equals(myBus.getIdx())) {
                    isCheckToday = true;
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return  busList.size();
    }
}
