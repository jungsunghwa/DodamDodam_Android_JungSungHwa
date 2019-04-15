package kr.hs.dgsw.smartschool.dodamdodam.recycler.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.PointItemBinding;

public class PointViewHolder extends RecyclerView.ViewHolder {

    public PointItemBinding binding;

    public PointViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
