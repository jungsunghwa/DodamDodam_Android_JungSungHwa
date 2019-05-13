package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.Model.song.YoutubeData;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.SongViewHolder;

public class SongSearchAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ERROR = -1;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_EMPTY = 1;
    private static final int TYPE_HINT = 2;

    private final RequestManager glide;
    private List<YoutubeData> list;
    private OnItemClickListener<YoutubeData> onItemClickListener;
    private boolean isSearch;
    private boolean hasQuotaError;

    public SongSearchAdapter(Context context, OnItemClickListener<YoutubeData> onItemClickListener) {
        glide = Glide.with(context);
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
                viewHolder = new RecyclerView.ViewHolder(view) {};
                break;
            case TYPE_ERROR:
                view = inflater.inflate(R.layout.quota_error_song_item, parent, false);
                viewHolder = new RecyclerView.ViewHolder(view) {};
                break;
            case TYPE_HINT:
                view = inflater.inflate(R.layout.hint_song_item, parent, false);
                viewHolder = new RecyclerView.ViewHolder(view) {};
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

            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(data);
            });

            glide.load(data.getThumbnailUrl()).into(searchViewHolder.binding.thumbnailImage);
            searchViewHolder.binding.videoTitleText.setText(data.getVideoTitle());
            searchViewHolder.binding.channelTitleText.setText(data.getChannelTitle());
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
}
