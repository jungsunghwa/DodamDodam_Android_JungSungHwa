package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.song.YoutubeData;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.SongViewHolder;

public class SongSearchAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ERROR = -1;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_EMPTY = 1;
    private static final int TYPE_HINT = 2;

    private static final String BUNDLE_KEY_SELECTED_POS = "selected_pos";

    private RequestManager glide;
    private List<YoutubeData> list;
    private int selectedPosition = -1;
    private View selectedView = null;
    private OnItemSelectedListener<YoutubeData> onItemClickListener;
    private boolean isSearch;
    private boolean hasQuotaError;

    public SongSearchAdapter(Context context, OnItemSelectedListener<YoutubeData> onItemClickListener) {
        glide = Glide.with(context.getApplicationContext());
        list = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.isEmpty()) {
            if (hasQuotaError)
                return TYPE_ERROR;
            else if (isSearch)
                return TYPE_EMPTY;
            else
                return TYPE_HINT;
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_EMPTY:
                view = inflater.inflate(R.layout.empty_song_item, parent, false);
                viewHolder = new RecyclerView.ViewHolder(view) {
                };
                break;
            case TYPE_ERROR:
                view = inflater.inflate(R.layout.quota_error_song_item, parent, false);
                viewHolder = new RecyclerView.ViewHolder(view) {
                };
                break;
            case TYPE_HINT:
                view = inflater.inflate(R.layout.hint_song_item, parent, false);
                viewHolder = new RecyclerView.ViewHolder(view) {
                };
                break;
            default:
                view = inflater.inflate(R.layout.song_item, parent, false);
                viewHolder = new SongViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SongViewHolder) {
            final YoutubeData data = list.get(position);

            SongViewHolder searchViewHolder = (SongViewHolder) holder;

            ((SongViewHolder) holder).binding.cardView.setChecked(position == selectedPosition);
            holder.itemView.setOnClickListener(v -> {
                MaterialCardView cardView = searchViewHolder.binding.cardView;

                if (selectedView != null && !holder.itemView.equals(selectedView)) {
                    ((MaterialCardView) selectedView).setChecked(false);
                }

                cardView.setChecked(!cardView.isChecked());

                selectedView = cardView.isChecked() ? holder.itemView : null;
                selectedPosition = cardView.isChecked() ? searchViewHolder.getAdapterPosition() : -1;

                if (onItemClickListener != null)
                    onItemClickListener.onItemSelected(data, cardView.isChecked());

            });

            glide.load(data.getThumbnailUrl()).error(glide.load(data.getLowerThumbnailUrl())).into(searchViewHolder.binding.imageThumbnail);
            searchViewHolder.binding.textVideoTitle.setText(data.getVideoTitle());
            searchViewHolder.binding.textChannelTitle.setText(data.getChannelTitle());
        }
    }

    public List<YoutubeData> getList() {
        return list;
    }

    public void setList(List<YoutubeData> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        if (list.isEmpty()) return 1;
        return list.size();
    }

    public void setHasQuotaError(boolean hasQuotaError) {
        this.hasQuotaError = hasQuotaError;
        notifyDataSetChanged();
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            selectedPosition = savedInstanceState.getInt(BUNDLE_KEY_SELECTED_POS);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_KEY_SELECTED_POS, selectedPosition);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        glide = null;
    }
}
