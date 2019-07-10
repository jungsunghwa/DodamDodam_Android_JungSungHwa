package kr.hs.dgsw.smartschool.dodamdodam.Model.song;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Video implements Parcelable {
    public static final int VIDEO_ALLOW = 1;
    public static final int VIDEO_NONE = 0;
    public static final int VIDEO_DISALLOW = -1;

    private int idx;
    @SerializedName("apply_member_id")
    private String applyMemberId;
    private String thumbnail;
    @SerializedName("video_title")
    private String videoTitle;
    @SerializedName("video_id")
    private String videoId;
    @SerializedName("video_url")
    private String videoUrl;
    @SerializedName("channel_title")
    private String channelTitle;
    @AllowType
    private int isAllow;
    @SerializedName("allow_teacher_idx")
    private int allowTeacherIdx;
    @SerializedName("submit_date")
    private Date submitDate;
    @SerializedName("check_date")
    private Date checkDate;


    protected Video(Parcel in) {
        idx = in.readInt();
        applyMemberId = in.readString();
        thumbnail = in.readString();
        videoTitle = in.readString();
        videoId = in.readString();
        videoUrl = in.readString();
        channelTitle = in.readString();
        isAllow = in.readInt();
        allowTeacherIdx = in.readInt();
        submitDate = (Date) in.readSerializable();
        checkDate = (Date) in.readSerializable();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public int getIdx() {
        return idx;
    }

    public String getApplyMemberId() {
        return applyMemberId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public int getIsAllow() {
        return isAllow;
    }

    public int getAllowTeacherIdx() {
        return allowTeacherIdx;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idx);
        dest.writeString(applyMemberId);
        dest.writeString(thumbnail);
        dest.writeString(videoTitle);
        dest.writeString(videoId);
        dest.writeString(videoUrl);
        dest.writeString(channelTitle);
        dest.writeInt(isAllow);
        dest.writeInt(allowTeacherIdx);
        dest.writeSerializable(submitDate);
        dest.writeSerializable(checkDate);
    }

    @IntDef(value = {VIDEO_ALLOW, VIDEO_NONE, VIDEO_DISALLOW})
    private @interface AllowType {
    }
}
