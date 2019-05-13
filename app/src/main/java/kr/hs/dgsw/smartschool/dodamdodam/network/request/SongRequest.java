package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SongRequest {
    @SerializedName("video_url")
    private String videoUrl;
    @SerializedName("submit_date")
    private Date submitDate;

    public SongRequest(String videoUrl) {
        this.videoUrl = videoUrl;
        this.submitDate = new Date();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Date getSubmitDate() {
        return submitDate;
    }
}
