package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SongCheckRequest {
    @SerializedName("video_idx")
    private List<Integer> videosIdx;
    @SerializedName("check_date")
    private Date checkDate;

    public SongCheckRequest(List<Integer> videosIdx, Date checkDate) {
        this.videosIdx = videosIdx;
        this.checkDate = checkDate;
    }

    public List<Integer> getVideosIdx() {
        return videosIdx;
    }

    public Date getCheckDate() {
        return checkDate;
    }
}
