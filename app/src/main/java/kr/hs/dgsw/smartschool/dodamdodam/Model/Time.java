package kr.hs.dgsw.smartschool.dodamdodam.Model;

import com.google.gson.annotations.SerializedName;

public class Time {
    int idx;
    String name;
    int type;
    @SerializedName("start_time")
    String startTime;
    @SerializedName("end_time")
    String endTime;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
