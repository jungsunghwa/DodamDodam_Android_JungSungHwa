package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.LostFound;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.LostfoundItemBinding;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public LostfoundItemBinding binding;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
