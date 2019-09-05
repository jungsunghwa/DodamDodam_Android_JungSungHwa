package kr.hs.dgsw.smartschool.dodamdodam.Model.bus;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusResponse {
    private String date;

    @SerializedName("bus")
    private List<Bus> bues;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Bus> getBues() {
        return bues;
    }

    public void setBues(List<Bus> bues) {
        this.bues = bues;
    }
}
