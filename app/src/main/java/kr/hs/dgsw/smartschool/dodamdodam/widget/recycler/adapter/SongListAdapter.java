package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    private final RequestManager glide;
    private List<YoutubeData> list;

    public SongListAdapter(Context context, List<Video> list) {
        glide = Glide.with(context);
        this.list = convertToYoutube(list);
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        final YoutubeData data = list.get(position);

        glide.load(data.getThumbnailUrl()).into(holder.binding.thumbnailImage);
        holder.binding.videoTitleText.setText(data.getVideoTitle());
        holder.binding.channelTitleText.setText(data.getChannelTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private List<YoutubeData> convertToYoutube(List<Video> videos) {
        List<YoutubeData> list = new ArrayList<>();
        for (Video video : videos) {
            list.add(new VideoYoutubeData(video));
        }

        return list;
    }
}
