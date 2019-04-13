package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class PointDemeritAdapter extends RecyclerView.Adapter<PointViewHolder> {

    private String[] points;

    public PointDemeritAdapter(Resources resources) {
        //TODO
        points = resources.getStringArray(R.array.point_demerit_list);
    }

    @NonNull
    @Override
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PointViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
        String[] point = points[position].split("\\|");
        holder.binding.textIndex.setText(String.format(Locale.getDefault(), "%d", position + 1));
        holder.binding.textContent.setText(point[0]);
        holder.binding.textPoint.setText(point[1]);
        if (point.length < 3) {
            holder.binding.textNote.setVisibility(View.GONE);
        } else {
            holder.binding.textNote.setVisibility(View.VISIBLE);
            holder.binding.textNote.setText(point[2]);
        }
    }

    @Override
    public int getItemCount() {
        return points.length;
    }
}
