package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import kr.hs.dgsw.smartschool.dodamdodam.activity.LocationCheckActivity;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationCheckItemBinding;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationItemLayoutBinding;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LocationViewModel;

public class LocationViewHolder extends RecyclerView.ViewHolder {

    public LocationCheckItemBinding binding;

    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        Objects.requireNonNull(binding);
        binding.locationName.setSelected(true);
    }
}