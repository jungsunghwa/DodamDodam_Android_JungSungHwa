package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.CounselViewHolder;

public class CounselAdapter extends RecyclerView.Adapter<CounselViewHolder> {
    private List<Teacher> teacherList;
    private Context context;

    public CounselAdapter(Context context, List<Teacher> teacherList) {
        this.context = context;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public CounselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CounselViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.counsel_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CounselViewHolder holder, int position) {
        Teacher teachers = teacherList.get(position);

        holder.binding.teacherNameText.setText(teachers.getName());
        holder.binding.partNameText.setText(teachers.getDepartmentsLogs().length);
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }
}
