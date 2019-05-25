package kr.hs.dgsw.smartschool.dodamdodam.Model.location;

import com.google.gson.annotations.SerializedName;

import kr.hs.dgsw.smartschool.dodamdodam.Model.location.Location;
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
        isChecked = 0;
    }

    public LocationInfo(Time time, Place place) {
        super(time, place);
        isChecked = 0;
    }

    public LocationInfo(LocationInfo locationInfo){
        super(locationInfo.getTimetableIdx(), locationInfo.getPlaceIdx());
        isChecked = 0;
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

    public Integer getStudenIdx() {
        return studentIdx;
    }

    public void setStudenIdx(Integer studenIdx) {
        this.studentIdx = studenIdx;
    }


    public Integer getChecked() {
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


}
