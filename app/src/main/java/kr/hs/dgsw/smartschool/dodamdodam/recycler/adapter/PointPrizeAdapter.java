package kr.hs.dgsw.smartschool.dodamdodam.recycler.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.recycler.holder.PointViewHolder;

public class PointPrizeAdapter extends RecyclerView.Adapter<PointViewHolder> {

    private String[] points;

    public PointPrizeAdapter(Resources resources) {
        points = resources.getStringArray(R.array.point_prize_list);
    }

    @NonNull
    @Override
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PointViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
        String[] point = points[position].split("\\|");
        holder.binding.textIndex.setText(String.format(Locale.KOREA, "%d", position + 1));
        holder.binding.textContent.setText(point[0]);
        holder.binding.textPoint.setText(point[1]);
        if (point.length <= 3) {
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
