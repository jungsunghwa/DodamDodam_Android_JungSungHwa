package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder;

import android.graphics.Typeface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.SongItemBinding;

public class SongViewHolder extends RecyclerView.ViewHolder {

    public SongItemBinding binding;

    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        binding.videoTitleText.setSelected(true);
        binding.videoTitleText.setTypeface(ResourcesCompat.getFont(itemView.getContext(), R.font.nanum_square_regular), Typeface.BOLD);
        binding.channelTitleText.setTypeface(ResourcesCompat.getFont(itemView.getContext(), R.font.nanum_square_round));
    }
}
