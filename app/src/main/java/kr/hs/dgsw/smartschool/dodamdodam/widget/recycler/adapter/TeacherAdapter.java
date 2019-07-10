package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.activity.CounselApplyActivity;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.TeacherViewHolder;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherViewHolder> {
    List<Teacher> teacherList;

    public TeacherAdapter(CounselApplyActivity counselApplyActivity, List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.counsel_apply_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);

        holder.binding.teacherName.setText(teacher.getName());
        holder.binding.teacherPart.setText(teacher.getDepartmentsLogs()[0].getPosition());
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }
}
