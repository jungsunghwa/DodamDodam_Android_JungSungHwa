package kr.hs.dgsw.smartschool.dodamdodam.Model;

import com.google.gson.annotations.SerializedName;

public class LocationInfo extends Location {

    @SerializedName("time_table_idx")
    private Integer timetableIdx;

    @SerializedName("place_idx")
    private Integer placeIdx;

    public void setTimetableIdx(Integer timetableIdx) {
        this.timetableIdx = timetableIdx;
    }

    public void setPlaceIdx(Integer placeIdx) {
        this.placeIdx = placeIdx;
    }

    public LocationInfo(Integer timetableIdx, Integer placeIdx) {
        super(timetableIdx, placeIdx);
        this.timetableIdx = timetableIdx;
        this.placeIdx = placeIdx;
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
