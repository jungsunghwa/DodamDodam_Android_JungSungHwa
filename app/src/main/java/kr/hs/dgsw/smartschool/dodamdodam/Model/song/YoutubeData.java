package kr.hs.dgsw.smartschool.dodamdodam.Model.song;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public class YoutubeData implements Parcelable {

    private static final String[] qualities = {"default", "mqdefault", "hqdefault", "sddefault", "maxresdefault", "0", "1", "2", "3"};

    private String videoId;
    private String thumbnailUrl;
    private String videoTitle;
    private String channelTitle;

    public YoutubeData() {
    }

    public YoutubeData(String videoId, String thumbnailUrl, String videoTitle, String channelTitle) {
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
        this.videoTitle = videoTitle;
        this.channelTitle = channelTitle;
    }

    protected YoutubeData(Parcel in) {
        videoId = in.readString();
        thumbnailUrl = in.readString();
        videoTitle = in.readString();
        channelTitle = in.readString();
    }

    public static final Creator<YoutubeData> CREATOR = new Creator<YoutubeData>() {
        @Override
        public YoutubeData createFromParcel(Parcel in) {
            return new YoutubeData(in);
        }

        @Override
        public YoutubeData[] newArray(int size) {
            return new YoutubeData[size];
        }
    };

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getLowerThumbnailUrl() {
        String lowerQuality;
        String[] splits = thumbnailUrl.split("/");
        switch (splits[4]) {
            case "hqdefault":
            case "0":
                lowerQuality = "mqdefault";
                break;
            case "standard":
                lowerQuality = "hqdefault";
                break;
            case "maxresdefault":
                lowerQuality = "standard";
                break;
            default:
                lowerQuality = "default";
                break;
        }

        return "https://i.ytimg.com/vi/" + videoId + '/' + lowerQuality + ".jpg";
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoId);
        dest.writeString(thumbnailUrl);
        dest.writeString(videoTitle);
        dest.writeString(channelTitle);
    }
}
