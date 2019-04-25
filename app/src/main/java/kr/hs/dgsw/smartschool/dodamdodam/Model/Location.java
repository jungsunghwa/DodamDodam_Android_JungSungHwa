package kr.hs.dgsw.smartschool.dodamdodam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {

    private Integer idx;

    @SerializedName("student_idx")
    private Integer studenIdx;

    @SerializedName("time_table_idx")
    private Integer timetableIdx;

    @SerializedName("place_idx")
    private Integer placeIdx;

    @SerializedName("is_checked")
    private Boolean isChecked;

    @SerializedName("check_teacher_idx")
    private Integer checkTeacherIdx;

    private String date;

    public Location(int timetableIdx, int placeIdx) {
        this.timetableIdx = timetableIdx;
        this.placeIdx = placeIdx;
    }

    public int getPlaceIdx() {
        return placeIdx;
    }

    public void setPlaceIdx(int placeIdx) {
        this.placeIdx = placeIdx;
    }

    public int getTimetableIdx() {
        return timetableIdx;
    }

    public void setTimetableIdx(int timetableIdx) {
        this.timetableIdx = timetableIdx;
    }
}
