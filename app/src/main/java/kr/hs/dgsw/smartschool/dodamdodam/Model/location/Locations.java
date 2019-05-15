package kr.hs.dgsw.smartschool.dodamdodam.Model.location;

import com.annimon.stream.Stream;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

import kr.hs.dgsw.b1nd.service.model.Student;
import kr.hs.dgsw.smartschool.dodamdodam.Model.LocationInfo;
import kr.hs.dgsw.smartschool.dodamdodam.Model.place.Place;
import kr.hs.dgsw.smartschool.dodamdodam.Model.timetable.Time;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LocationRequest;

public class Locations {

    @SerializedName("idx")
    private int studentIdx;

    @SerializedName("class_idx")
    private int classIdx;

    private ArrayList<Location> locations;

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

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public Locations(int studentIdx, int classIdx, ArrayList<Location> locations) {
        this.studentIdx = studentIdx;
        this.classIdx = classIdx;
        this.locations = locations;
    }
}
