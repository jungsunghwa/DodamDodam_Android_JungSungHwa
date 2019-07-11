package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.TeacherViewHolder;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherViewHolder> {

    private RequestManager glide;
    List<Teacher> teacherList;
    private int selectedPosition = -1;
    private View selectedView = null;
    private OnItemSelectedListener<Teacher> onItemClickListener;

    public TeacherAdapter(Context context, List<Teacher> teacherList, OnItemSelectedListener<Teacher> onItemClickListener) {
        glide = Glide.with(context.getApplicationContext());
        this.teacherList = teacherList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.counsel_apply_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);

        TeacherViewHolder teacherViewHolder = (TeacherViewHolder) holder;

        ((TeacherViewHolder)holder).binding.counselCardView.setChecked(position == selectedPosition);
        holder.itemView.setOnClickListener(v -> {
            MaterialCardView cardView = teacherViewHolder.binding.counselCardView;

            if (selectedView != null && !holder.itemView.equals(selectedView)) {
                ((MaterialCardView) selectedView).setChecked(false);
            }

            cardView.setChecked(!cardView.isChecked());

            selectedView = cardView.isChecked() ? holder.itemView : null;
            selectedPosition = cardView.isChecked() ? teacherViewHolder.getAdapterPosition() : -1;

            if (onItemClickListener != null)
                onItemClickListener.onItemSelected(teacher, cardView.isChecked());

        });

        glide.load(teacher.getProfileImage()).into(holder.binding.teacherImageview);
        holder.binding.teacherName.setText(teacher.getName());
        holder.binding.teacherPart.setText(teacher.getDepartmentsLogs()[0].getPosition());
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

}
