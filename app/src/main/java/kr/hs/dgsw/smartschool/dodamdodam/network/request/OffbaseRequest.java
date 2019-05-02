package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class OffbaseRequest {
    @SerializedName("start_time")
    private Date startTime;
    @SerializedName("end_time")
    private Date endTime;
    private String reason;

    public OffbaseRequest(Date startTime, Date endTime, String reason) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reason = reason;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getReason() {
        return reason;
    }
}
