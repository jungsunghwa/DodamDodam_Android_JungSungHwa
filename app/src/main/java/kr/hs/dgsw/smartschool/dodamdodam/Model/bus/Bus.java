package kr.hs.dgsw.smartschool.dodamdodam.Model.bus;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

public class Bus {
    private Integer idx;

    @SerializedName("bus_name")
    private String busName;

    private String description;

    @SerializedName("people_limit")
    private Integer peopleLimit;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("time_required")
    private String timeRequired;

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(Integer peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(String timeRequired) {
        this.timeRequired = timeRequired;
    }
}
