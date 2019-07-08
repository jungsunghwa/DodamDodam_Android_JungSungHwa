package kr.hs.dgsw.smartschool.dodamdodam.Model.timetable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTables {
    @SerializedName("time_tables")
    private List<Time> timeTables;

    public List<Time> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(List<Time> timeTables) {
        this.timeTables = timeTables;
    }
}
