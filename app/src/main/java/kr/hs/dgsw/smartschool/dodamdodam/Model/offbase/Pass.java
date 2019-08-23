package kr.hs.dgsw.smartschool.dodamdodam.Model.offbase;

import android.os.Parcel;

import java.util.Date;

public class Pass extends OffbaseItem {

    public Pass(Parcel in) {
        super(in);
    }

    public Pass(int idx, int studentIdx, Date startTime, Date endTime, int isAllow, String reason, int allowTeacherIdx, Date allowTeacherTime, Date createdAt) {
        super(idx, studentIdx, startTime, endTime, isAllow, reason, allowTeacherIdx, allowTeacherTime, createdAt);
    }

    public static final Creator<Pass> CREATOR = new Creator<Pass>() {
        @Override
        public Pass createFromParcel(Parcel in) {
            return new Pass(in);
        }

        @Override
        public Pass[] newArray(int size) {
            return new Pass[size];
        }
    };
}
