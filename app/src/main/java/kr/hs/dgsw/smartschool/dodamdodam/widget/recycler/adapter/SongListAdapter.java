package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.annimon.stream.Optional;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.song.Video;
import kr.hs.dgsw.smartschool.dodamdodam.Model.song.VideoYoutubeData;
import kr.hs.dgsw.smartschool.dodamdodam.Model.song.YoutubeData;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.SongViewHolder;

public class SongListAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private RequestManager glide;
    private List<VideoYoutubeData> list;

    private OnItemClickListener<VideoYoutubeData> clickListener;

    private String quality;

    public SongListAdapter(Context context, OnItemClickListener<VideoYoutubeData> clickListener) {
        glide = Glide.with(context.getApplicationContext());
        this.clickListener = clickListener;
        this.quality = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_thumbnail), "default");
        if (quality != null && quality.equals("frame"))
            quality = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_frame), "1");
    }

    public SongListAdapter(Context context, List<Video> list, OnItemClickListener<VideoYoutubeData> clickListener) {
        glide = Glide.with(context.getApplicationContext());
        this.clickListener = clickListener;
        this.quality = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_thumbnail), "default");
        if (quality != null && quality.equals("frame"))
            quality = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.pref_key_frame), "1");
        this.list = convertToYoutube(list);
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        final VideoYoutubeData data = list.get(position);

        glide.load(data.getThumbnailUrl()).into(holder.binding.imageThumbnail);
        holder.binding.textVideoTitle.setText(data.getVideoTitle());
        holder.binding.textChannelTitle.setText(data.getChannelTitle());
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null)
                clickListener.onItemClick(data);
        });
    }

    @Override
    public int getItemCount() {
        return Optional.ofNullable(list).map(List::size).orElse(0);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        glide = null;
    }

    public void setList(List<Video> list) {
        this.list = convertToYoutube(list);
        notifyDataSetChanged();
    }

    private List<VideoYoutubeData> convertToYoutube(List<Video> videos) {
        List<VideoYoutubeData> list = new ArrayList<>();
        for (Video video : videos) {
            list.add(new VideoYoutubeData(video, quality));
        }

        return list;
    }
}
