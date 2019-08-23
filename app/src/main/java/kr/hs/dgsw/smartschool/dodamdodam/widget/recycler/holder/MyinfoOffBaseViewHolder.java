package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.MyinfoOffbaseItemBinding;

public class MyinfoOffBaseViewHolder extends RecyclerView.ViewHolder {

    public MyinfoOffbaseItemBinding binding;

    public MyinfoOffBaseViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
