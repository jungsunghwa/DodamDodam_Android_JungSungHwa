package kr.hs.dgsw.smartschool.dodamdodam.Model.location;

import com.google.gson.annotations.SerializedName;

public class Location {

    private Integer idx;

    @SerializedName("student_idx")
    private Integer studenIdx;

    @SerializedName("time_table_idx")
    private Integer timetableIdx;

    @SerializedName("place_idx")
    private Integer placeIdx;

    @SerializedName("is_checked")
    private int isChecked;

    @SerializedName("check_teacher_idx")
    private Integer checkTeacherIdx;

    private String date;

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getStudenIdx() {
        return studenIdx;
    }

    public void setStudenIdx(Integer studenIdx) {
        this.studenIdx = studenIdx;
    }

    public void setTimetableIdx(Integer timetableIdx) {
        this.timetableIdx = timetableIdx;
    }

    public void setPlaceIdx(Integer placeIdx) {
        this.placeIdx = placeIdx;
    }

    public int getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked ? 1 : 0;
    }

    public Integer getCheckTeacherIdx() {
        return checkTeacherIdx;
    }

    public void setCheckTeacherIdx(Integer checkTeacherIdx) {
        this.checkTeacherIdx = checkTeacherIdx;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Location(Integer timetableIdx, Integer placeIdx) {
        this.timetableIdx = timetableIdx;
        this.placeIdx = placeIdx;
        this.isChecked = 0;
    }

    public Integer getPlaceIdx() {
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
