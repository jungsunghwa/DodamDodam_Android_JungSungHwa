package kr.hs.dgsw.smartschool.dodamdodam.Model.offbase;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Leave extends OffbaseItem {
    @SerializedName("allow_parent_idx")
    private int allowParentIdx;
    @SerializedName("allow_parent_time")
    private Date allowParentTime;
    @SerializedName("arrived_time")
    private Date arrivedTime;

    public Leave(Parcel in) {
        super(in);
        allowParentIdx = in.readInt();
        allowParentTime = (Date) in.readSerializable();
        arrivedTime = (Date) in.readSerializable();
    }

    public Leave(int idx, int studentIdx, Date startTime, Date endTime, int isAllow, String reason, int allowTeacherIdx, Date allowTeacherTime, Date createdAt, int allowParentIdx, Date allowParentTime, Date arrivedTime) {
        super(idx, studentIdx, startTime, endTime, isAllow, reason, allowTeacherIdx, allowTeacherTime, createdAt);
        this.allowParentIdx = allowParentIdx;
        this.allowParentTime = allowParentTime;
        this.arrivedTime = arrivedTime;
    }

    public int getAllowParentIdx() {
        return allowParentIdx;
    }

    public Date getAllowParentTime() {
        return allowParentTime;
    }

    public Date getArrivedTime() {
        return arrivedTime;
    }

    public static final Creator<Leave> CREATOR = new Creator<Leave>() {
        @Override
        public Leave createFromParcel(Parcel in) {
            return new Leave(in);
        }

        @Override
        public Leave[] newArray(int size) {
            return new Leave[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(allowParentIdx);
        dest.writeSerializable(allowParentTime);
        dest.writeSerializable(arrivedTime);
    }
}
