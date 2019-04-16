package kr.hs.dgsw.smartschool.dodamdodam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TimeTables{
    @SerializedName("time_tables")
    List<Time> timeTables;

    public List<Time> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(List<Time> timeTables) {
        this.timeTables = timeTables;
    }
}