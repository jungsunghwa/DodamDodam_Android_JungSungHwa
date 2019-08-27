package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.LostFound.ItemViewHolder;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.MyinfoOffBaseViewHolder;

public class MyinfoOffBaseAdapter<T extends OffbaseItem> extends RecyclerView.Adapter<MyinfoOffBaseViewHolder> {

    private Context context;
    private List<T> offbaseItems;

    public MyinfoOffBaseAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public MyinfoOffBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyinfoOffBaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.myinfo_offbase_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyinfoOffBaseViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm", Locale.getDefault());

        String date = dateFormat.format(offbaseItems.get(position).getStartTime());
        String time = timeFormat.format(offbaseItems.get(position).getStartTime());
        String end = format.format(offbaseItems.get(position).getEndTime());

        holder.binding.date.setText(date);
        holder.binding.time.setText(time);
        holder.binding.endDate.setText("~" + end);

        if (offbaseItems.get(position) instanceof Leave) {
            holder.binding.textView.setText("외박");
        }
        else if (offbaseItems.get(position) instanceof Pass) {
            holder.binding.textView.setText("외출");
        }

        switch (offbaseItems.get(position).getIsAllow()) {
            case -1:
                holder.binding.statusMessage.setText(R.string.text_status_refuse);
                Glide.with(context).load(R.drawable.ic_offbase_refuse).into(holder.binding.statusIcon);
                holder.binding.statusMessage.setTextColor(context.getResources().getColor(R.color.colorOffbaseCancel));
                break;
            case 0:
                holder.binding.statusMessage.setText(R.string.text_status_wait);
                Glide.with(context).load(R.drawable.ic_offbase_unknown).into(holder.binding.statusIcon);
                holder.binding.statusMessage.setTextColor(context.getResources().getColor(R.color.colorBlack));
                break;
            case 1:
                holder.binding.statusMessage.setText(R.string.text_status_ok);
                Glide.with(context).load(R.drawable.ic_offbase_ok).into(holder.binding.statusIcon);
                holder.binding.statusMessage.setTextColor(context.getResources().getColor(R.color.colorOffbaseOK));
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getItemCount() {
        return offbaseItems.size();
    }

    public void setList(List<T> offbaseItems) {
        this.offbaseItems = offbaseItems;
        notifyDataSetChanged();
    }
}
