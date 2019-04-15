package kr.hs.dgsw.smartschool.dodamdodam.recycler.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.PlaceItemBinding;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.TimeTableItemBinding;

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    public PlaceItemBinding binding;

    public PlaceViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
