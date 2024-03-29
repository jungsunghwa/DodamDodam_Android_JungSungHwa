package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import kr.hs.dgsw.b1nd.service.model.Teacher;
import kr.hs.dgsw.smartschool.dodamdodam.Model.counsel.Counsel;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.CounselActivity;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.CounselViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.CounselViewHolder;

public class CounselAdapter extends RecyclerView.Adapter<CounselViewHolder> {
    List<Counsel> counselList;
    private DatabaseHelper databaseHelper;
    CounselViewModel counselViewModel;
    private CounselActivity counselActivity;

    private final SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd H:mm ", Locale.getDefault());

    public CounselAdapter(CounselViewModel counselViewModel, CounselActivity counselActivity, List<Counsel> counselList) {
        this.counselList = counselList;
        this.counselViewModel = counselViewModel;
        databaseHelper = DatabaseHelper.getInstance(counselActivity);
        this.counselActivity = counselActivity;
    }

    @NonNull
    @Override
    public CounselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CounselViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.counsel_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CounselViewHolder holder, int position) {
        Counsel counsel = counselList.get(position);
        Teacher teacher = (Teacher) databaseHelper.getTeacherByIdx(counsel.getManageTeacherIdx());
        String name = teacher.getName();

        holder.binding.counselTitle.setText(name);
        holder.binding.counselName.setText("사유 : " + counsel.getReason());
        holder.binding.counselUploadTime.setText("신청일: " + dateFormatDate.format(counsel.getRequestDate()));
        holder.binding.deleteBtn.setOnClickListener(v ->
                new AlertDialog.Builder(v.getContext())
                        .setIcon(R.drawable.ic_warning)
                        .setTitle("주의")
                        .setMessage("삭제하시겠습니다?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            counselViewModel.deleteCounsel(counsel.getIdx());
                            counselList.remove(counsel);
                            notifyItemRemoved(holder.getAdapterPosition());
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .create()
                        .show());
        if (teacher.getProfileImage() != null) {
            Glide.with(counselActivity).load(teacher.getProfileImage()).into(holder.binding.counselImageview);
        }
    }

    public void setList(List<Counsel> counselList) {
        this.counselList = counselList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return counselList.size();
    }

}
