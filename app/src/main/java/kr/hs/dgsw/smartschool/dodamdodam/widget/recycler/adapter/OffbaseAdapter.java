package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Optional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leave;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Leaves;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Offbase;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.OffbaseItem;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Pass;
import kr.hs.dgsw.smartschool.dodamdodam.Model.offbase.Passes;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.OffbaseViewModel;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.OffbaseViewHolder;

public class OffbaseAdapter extends RecyclerView.Adapter<OffbaseViewHolder> {

    private final SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final SimpleDateFormat dateFormatTime = new SimpleDateFormat("H:mm", Locale.getDefault());
    private List<OffbaseItem> offbaseItems;
    private OffbaseViewModel viewModel;
    private Passes passes;
    private Leaves leaves;

    private OnItemClickListener<OffbaseItem> clickListener;

    public OffbaseAdapter(OffbaseViewModel viewModel, OnItemClickListener<OffbaseItem> clickListener) {
        this.viewModel = viewModel;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public OffbaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OffbaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offbase_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OffbaseViewHolder holder, int position) {
        OffbaseItem item = offbaseItems.get(position);
        if (item instanceof Leave) {
            Leave leave = (Leave) item;
            holder.binding.textType.setText(R.string.text_offbase_leave);
            holder.binding.textLabelDate.setText(R.string.text_offbase_leave_date);
            holder.binding.textLabelDateEnd.setText(R.string.text_offbase_leave_date_end);
            holder.binding.textLabelTime.setText(R.string.text_offbase_leave_time);
            holder.binding.textLabelTimeEnd.setText(R.string.text_offbase_time_end);

            holder.binding.textDate.setText(dateFormatDate.format(leave.getStartTime()));
            holder.binding.textTime.setText(dateFormatTime.format(leave.getStartTime()));
            holder.binding.textDateEnd.setText(dateFormatDate.format(leave.getEndTime()));
            holder.binding.textTimeEnd.setText(dateFormatTime.format(leave.getEndTime()));

            holder.binding.btnDelete.setOnClickListener(v ->
                    new AlertDialog.Builder(v.getContext())
                            .setIcon(R.drawable.ic_warning)
                            .setTitle(R.string.warning)
                            .setMessage(R.string.warn_delete_sure)
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                viewModel.deleteLeave(item.getIdx());
                                offbaseItems.remove(item);
                                notifyItemRemoved(holder.getAdapterPosition());
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .create()
                            .show());
        } else if (item instanceof Pass) {
            Pass pass = (Pass) item;
            holder.binding.textType.setText(R.string.text_offbase_pass);
            holder.binding.textLabelDate.setText(R.string.text_offbase_pass_date);
            holder.binding.textLabelDateEnd.setText(null);
            holder.binding.textLabelTime.setText(R.string.text_offbase_pass_time);
            holder.binding.textLabelTimeEnd.setText(null);

            holder.binding.textDate.setText(dateFormatDate.format(pass.getStartTime()));
            holder.binding.textTime.setText(String.format(Locale.getDefault(), "%s - %s", dateFormatTime.format(pass.getStartTime()), dateFormatTime.format(pass.getEndTime())));
            holder.binding.textDateEnd.setText(null);
            holder.binding.textTimeEnd.setText(null);

            holder.binding.btnDelete.setOnClickListener(v ->
                    new AlertDialog.Builder(v.getContext())
                            .setIcon(R.drawable.ic_warning)
                            .setTitle(R.string.warning)
                            .setMessage(R.string.warn_delete_sure)
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                viewModel.deletePass(item.getIdx());
                                offbaseItems.remove(item);
                                notifyItemRemoved(holder.getAdapterPosition());
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .create()
                            .show());
        }

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onItemClick(item);
        });

        @DrawableRes
        int imageRes;
        @StringRes
        int stringRes;

        switch (item.getIsAllow()) {
            case -1:
                stringRes = R.string.text_status_refuse;
                imageRes = R.drawable.ic_offbase_refuse;
                break;
            case 0:
                stringRes = R.string.text_status_wait;
                imageRes = R.drawable.ic_offbase_unknown;
                break;
            case 1:
                stringRes = R.string.text_status_ok;
                imageRes = R.drawable.ic_offbase_ok;
                break;
            default:
                throw new IllegalArgumentException();
        }
        holder.binding.imageStatus.setImageResource(imageRes);
        holder.binding.textStatus.setText(stringRes);
    }

    public void setOffbaseItems(Offbase offbase) {
        this.offbaseItems = offbase.getAll();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return Optional.ofNullable(offbaseItems).map(List::size).orElse(0);
    }

    public boolean isEmpty() {
        return offbaseItems == null || offbaseItems.size() == 0;
    }
}
