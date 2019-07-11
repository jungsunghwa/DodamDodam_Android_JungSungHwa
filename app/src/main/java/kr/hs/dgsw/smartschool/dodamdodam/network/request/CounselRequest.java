package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CounselRequest {

    private String reason;

    @SerializedName("manage_teacher_id")
    private String manageTeacherId;

    public String getReason(String reason) {
        return reason;
    }

    public String getManageTeacherId(String manageTeacherId) {
        return manageTeacherId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setManageTeacherId(String manageTeacherId) {
        this.manageTeacherId = manageTeacherId;
    }
}
