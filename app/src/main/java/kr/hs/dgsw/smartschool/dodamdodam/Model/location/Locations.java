package kr.hs.dgsw.smartschool.dodamdodam.Model.location;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Locations {

    @SerializedName("idx")
    private int studentIdx;

    @SerializedName("class_idx")
    private int classIdx;

    private ArrayList<LocationInfo> locations;

    public int getStudentIdx() {
        return studentIdx;
    }

    public void setStudentIdx(int studentIdx) {
        this.studentIdx = studentIdx;
    }

    public int getClassIdx() {
        return classIdx;
    }

    public void setClassIdx(int classIdx) {
        this.classIdx = classIdx;
    }

    public ArrayList<LocationInfo> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<LocationInfo> locations) {
        this.locations = locations;
    }

    public Locations(int studentIdx, int classIdx, ArrayList<LocationInfo> locations) {
        this.studentIdx = studentIdx;
        this.classIdx = classIdx;
        this.locations = locations;
    }
}
