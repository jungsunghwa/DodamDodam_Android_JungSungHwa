package kr.hs.dgsw.smartschool.dodamdodam.Model.offbase;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Pass {
    private int idx;
    @SerializedName("student_idx")
    private int studentIdx;
    @SerializedName("start_time")
    private Date startTime;
    @SerializedName("end_time")
    private Date endTime;
    @SerializedName("is_allow")
    private int isAllow;
    private String reason;
    @SerializedName("allow_teacher_idx")
    private int allowTeacherIdx;
    @SerializedName("allow_teacher_time")
    private Date allowTeacherTime;
    @SerializedName("created_at")
    private Date createdAt;

    public int getIdx() {
        return idx;
    }

    public int getStudentIdx() {
        return studentIdx;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getIsAllow() {
        return isAllow;
    }

    public String getReason() {
        return reason;
    }

    public int getAllowTeacherIdx() {
        return allowTeacherIdx;
    }

    public Date getAllowTeacherTime() {
        return allowTeacherTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
