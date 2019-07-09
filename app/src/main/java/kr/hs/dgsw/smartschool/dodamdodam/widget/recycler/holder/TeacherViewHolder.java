package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.databinding.CounselApplyItemBinding;

public class TeacherViewHolder extends RecyclerView.ViewHolder {

    public CounselApplyItemBinding binding;

    public TeacherViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
