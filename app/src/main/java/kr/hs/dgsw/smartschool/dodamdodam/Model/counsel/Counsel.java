package kr.hs.dgsw.smartschool.dodamdodam.Model.counsel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Counsel {
    private Integer idx;

    @SerializedName("student_idx")
    private Integer studentIdx;

    private String reason;

    @SerializedName("request_date")
    private Date requestDate;

    @SerializedName("meeting_date")
    private Date meetingDate;

    @SerializedName("manage_teacher_idx")
    private Integer manageTeacherIdx;

    @SerializedName("is_check")
    private Integer isCheck;

    @SerializedName("mange_teacher_id")
    private String mangeTeacherId;

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public Integer getManageTeacherIdx() {
        return manageTeacherIdx;
    }

    public void setManageTeacherIdx(Integer manageTeacherIdx) {
        this.manageTeacherIdx = manageTeacherIdx;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public String getMangeTeacherId() {
        return mangeTeacherId;
    }

    public void setMangeTeacherId(String mangeTeacherId) {
        this.mangeTeacherId = mangeTeacherId;
    }

}
