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

    public int getAllowParentIdx() {
        return allowParentIdx;
    }

    public Date getAllowParentTime() {
        return allowParentTime;
    }

    public Date getArrivedTime() {
        return arrivedTime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(allowParentIdx);
        dest.writeSerializable(allowParentTime);
        dest.writeSerializable(arrivedTime);
    }
}
