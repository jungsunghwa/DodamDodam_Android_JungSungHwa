package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongItemBinding;

public class SongSearchViewHolder extends RecyclerView.ViewHolder {

    public SongItemBinding binding;

    public SongSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        binding.videoTitleText.setSelected(true);
    }
}
