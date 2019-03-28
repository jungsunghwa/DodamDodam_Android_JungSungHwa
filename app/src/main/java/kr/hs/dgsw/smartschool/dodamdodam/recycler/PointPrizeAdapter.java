package kr.hs.dgsw.smartschool.dodamdodam.recycler;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class PointPrizeAdapter extends RecyclerView.Adapter<PointViewHolder> {

    private String[] points;

    public PointPrizeAdapter(Resources resources) {
        points = resources.getStringArray(R.array.point_prize_list);
    }

    @NonNull
    @Override
    public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PointViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
        String[] point = points[position].split("\\|");
        holder.textIndex.setText(String.format(Locale.KOREA, "%d", position + 1));
        holder.textContent.setText(point[0]);
        holder.textPoint.setText(point[1]);
        if (point.length <= 3) {
            holder.textNote.setVisibility(View.GONE);
        } else {
            holder.textNote.setVisibility(View.VISIBLE);
            holder.textNote.setText(point[2]);
        }
    }

    @Override
    public int getItemCount() {
        return points.length;
    }
}
