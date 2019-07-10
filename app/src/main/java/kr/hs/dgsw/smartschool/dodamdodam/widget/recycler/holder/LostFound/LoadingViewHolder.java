package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.LostFound;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.LostfoundLoadingBinding;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    LostfoundLoadingBinding binding;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
