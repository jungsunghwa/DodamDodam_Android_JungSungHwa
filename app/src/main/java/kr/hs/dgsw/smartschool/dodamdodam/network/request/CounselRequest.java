package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CounselRequest {

    private String reason;

    @SerializedName("request_date")
    private Date requestDate;

    @SerializedName("manage_teacher_id")
    private Date manageTeacherId;

    public CounselRequest(String reason, Date requestDate, Date manageTeacherId) {
        this.reason = reason;
        this.requestDate = requestDate;
        this.manageTeacherId = manageTeacherId;
    }

    public String getReason() {
        return reason;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Date getManageTeacherId() {
        return manageTeacherId;
    }
}
