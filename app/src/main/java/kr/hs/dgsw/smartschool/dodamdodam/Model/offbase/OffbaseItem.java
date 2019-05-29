package kr.hs.dgsw.smartschool.dodamdodam.Model.offbase;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class OffbaseItem implements Parcelable {
    private int idx;
    @SerializedName("student_idx")
    private int studentIdx;
    @SerializedName("start_time")
    private Date startTime;
    @SerializedName("end_time")
    private Date endTime;
    @SerializedName("is_allow")
    private int isAllow;
    private String reason;
    @SerializedName("allow_teacher_idx")
    private int allowTeacherIdx;
    @SerializedName("allow_teacher_time")
    private Date allowTeacherTime;
    @SerializedName("created_at")
    private Date createdAt;

    public OffbaseItem() {
    }

    protected OffbaseItem(Parcel in) {
        idx = in.readInt();
        studentIdx = in.readInt();
        isAllow = in.readInt();
        reason = in.readString();
        allowTeacherIdx = in.readInt();
        startTime = (Date) in.readSerializable();
        endTime = (Date) in.readSerializable();
        allowTeacherTime = (Date) in.readSerializable();
        createdAt = (Date) in.readSerializable();
    }

    public static final Creator<OffbaseItem> CREATOR = new Creator<OffbaseItem>() {
        @Override
        public OffbaseItem createFromParcel(Parcel in) {
            return new OffbaseItem(in);
        }

        @Override
        public OffbaseItem[] newArray(int size) {
            return new OffbaseItem[size];
        }
    };

    public int getIdx() {
        return idx;
    }

    public int getStudentIdx() {
        return studentIdx;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getIsAllow() {
        return isAllow;
    }

    public String getReason() {
        return reason;
    }

    public int getAllowTeacherIdx() {
        return allowTeacherIdx;
    }

    public Date getAllowTeacherTime() {
        return allowTeacherTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idx);
        dest.writeInt(studentIdx);
        dest.writeInt(isAllow);
        dest.writeString(reason);
        dest.writeInt(allowTeacherIdx);
        dest.writeSerializable(startTime);
        dest.writeSerializable(endTime);
        dest.writeSerializable(allowTeacherTime);
        dest.writeSerializable(createdAt);
    }
}
