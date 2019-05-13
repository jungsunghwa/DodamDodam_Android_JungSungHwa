package kr.hs.dgsw.smartschool.dodamdodam.Model.song;

import java.util.Locale;

public class YoutubeData {

    private String videoId;
    private String thumbnailUrl;
    private String videoTitle;
    private String channelTitle;

    public YoutubeData(String videoId, String videoTitle, String channelTitle) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.channelTitle = channelTitle;
    }

    public YoutubeData(String videoId, String thumbnailUrl, String videoTitle, String channelTitle) {
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
        this.videoTitle = videoTitle;
        this.channelTitle = channelTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getVideoUrl() {
        return String.format(Locale.getDefault(), "https://www.youtube.com/watch?v=%s", videoId);
    }
}
