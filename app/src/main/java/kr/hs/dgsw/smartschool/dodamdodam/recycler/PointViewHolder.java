package kr.hs.dgsw.smartschool.dodamdodam.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class PointViewHolder extends RecyclerView.ViewHolder {

    TextView textIndex, textContent, textPoint, textNote;

    public PointViewHolder(@NonNull View itemView) {
        super(itemView);
        textIndex = itemView.findViewById(R.id.textIndex);
        textContent = itemView.findViewById(R.id.textContent);
        textPoint = itemView.findViewById(R.id.textPoint);
        textNote = itemView.findViewById(R.id.textNote);
    }
}
