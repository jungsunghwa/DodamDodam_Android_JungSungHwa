package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.OffbaseItemBinding;

public class OffbaseViewHolder extends RecyclerView.ViewHolder {

    public OffbaseItemBinding binding;

    public OffbaseViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
