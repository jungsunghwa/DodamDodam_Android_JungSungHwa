package kr.hs.dgsw.smartschool.dodamdodam.Model.location;

import com.google.gson.annotations.SerializedName;

import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;

public class LocationInfo extends Location {

    private Integer idx;

    @SerializedName("student_idx")
    private Integer studentIdx;

    @SerializedName("is_checked")
    private Integer isChecked;

    @SerializedName("check_teacher_idx")
    private Integer checkTeacherIdx;

    private String date;

    public LocationInfo(Integer timetableIdx, Integer placeIdx) {
        super(timetableIdx, placeIdx);
        isChecked = null;
    }

    public LocationInfo(Time time, Place place) {
        super(time, place);
        isChecked = null;
    }

    public LocationInfo(LocationInfo locationInfo){
        super(locationInfo.getTimetableIdx(), locationInfo.getPlaceIdx());
        isChecked = locationInfo.isChecked;
    }

    public LocationInfo() {
        super();
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getStudentIdx() {
        return studentIdx;
    }

    public void setStudentIdx(Integer studentIdx) {
        this.studentIdx = studentIdx;
    }

    public Integer getChecked() {
        return isChecked;
    }

    public void setChecked(Integer checked) {
        isChecked =  checked;
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


}
