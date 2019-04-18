package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler;

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
import kr.hs.dgsw.smartschool.dodamdodam.Model.YoutubeData;

public class SongSearchAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ERROR = -1;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_EMPTY = 1;
    private static final int TYPE_HINT = 2;

    private final RequestManager glide;
    private List<YoutubeData> youtubeDataList;
    private OnItemClickListener<YoutubeData> onItemClickListener;
    private boolean isSearch;
    private boolean hasQuotaError;

    public SongSearchAdapter(Context context, OnItemClickListener<YoutubeData> onItemClickListener) {
        glide = Glide.with(context);
        youtubeDataList = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (youtubeDataList.isEmpty()) {
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
                viewHolder = new SongSearchViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SongSearchViewHolder) {
            final YoutubeData data = youtubeDataList.get(position);

            SongSearchViewHolder searchViewHolder = (SongSearchViewHolder) holder;

            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(data);
            });

            glide.load(data.getThumbnailUrl()).into(searchViewHolder.binding.thumbnailImage);
            searchViewHolder.binding.videoTitleText.setText(data.getVideoTitle());
            searchViewHolder.binding.channelTitleText.setText(data.getChannelTitle());
        }
    }

    public List<YoutubeData> getYoutubeDataList() {
        return youtubeDataList;
    }

    public void setYoutubeDataList(List<YoutubeData> youtubeDataList) {
        this.youtubeDataList = youtubeDataList;
    }

    @Override
    public int getItemCount() {
        if (youtubeDataList == null) return 0;
        if (youtubeDataList.isEmpty()) return 1;
        return youtubeDataList.size();
    }

    public void setHasQuotaError(boolean hasQuotaError) {
        this.hasQuotaError = hasQuotaError;
        notifyDataSetChanged();
    }

    public void setSearch(boolean search) {
        isSearch = search;
    }
}
