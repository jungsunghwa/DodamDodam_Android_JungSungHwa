package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.OffbaseItemBinding;

public class OffbaseViewHolder extends RecyclerView.ViewHolder {

    OffbaseItemBinding binding;

    public OffbaseViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        binding.btnDelete.setOnClickListener(v -> Toast.makeText(v.getContext(), "\uD83D\uDEA7", Toast.LENGTH_SHORT).show());
    }
}
