package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.LocationItemLayoutBinding;

public class LocationViewHolder extends RecyclerView.ViewHolder {

    public LocationItemLayoutBinding binding;

    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        binding.studentClassTv.setSelected(true);
    }
}