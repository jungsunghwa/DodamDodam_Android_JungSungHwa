package kr.hs.dgsw.smartschool.dodamdodam.Model.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;

public class Location implements Serializable {

    private transient Time time;

    private transient Place place;

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

    public Location() {
    }

    public Location(Integer timetableIdx, Integer placeIdx) {
        this.timetableIdx = timetableIdx;
        this.placeIdx = placeIdx;
    }

    public Location(Time time, Place place) {
        this.time = time;
        this.timetableIdx = time.getIdx();
        this.place = place;
        if (place != null) this.placeIdx = place.getIdx();
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

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
