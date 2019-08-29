package kr.hs.dgsw.smartschool.dodamdodam.Model.song;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;
import androidx.annotation.StringRes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import kr.hs.dgsw.smartschool.dodamdodam.R;

public class Video implements Parcelable {
//    public enum Status implements Parcelable {
//        VIDEO_ALLOW(1),
//        VIDEO_NONE(0),
//        VIDEO_DISALLOW(-1);
//
//        private int status;
//
//        Status(int status) {
//            this.status = status;
//        }
//
//        public static final Creator<Status> CREATOR = new Creator<Status>() {
//            @Override
//            public Status createFromParcel(Parcel in) {
//                switch (in.readInt()) {
//                    case 1:
//                        return VIDEO_ALLOW;
//                    case 0:
//                        return VIDEO_NONE;
//                    case -1:
//                        return VIDEO_DISALLOW;
//                }
//                return null;
//            }
//
//            @Override
//            public Status[] newArray(int size) {
//                return new Status[size];
//            }
//        };
//
//        @StringRes
//        public int toStringRes() {
//            switch (status) {
//                case 1:
//                    return R.string.text_status_ok;
//                case 0:
//                    return R.string.text_status_wait;
//                case -1:
//                    return R.string.text_status_refuse;
//                default:
//                    return -1;
//            }
//        }
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeInt(status);
//        }
//    }

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
    @SerializedName("is_allow")
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
}
