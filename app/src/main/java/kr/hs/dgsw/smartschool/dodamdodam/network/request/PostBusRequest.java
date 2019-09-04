package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.google.gson.annotations.SerializedName;

public class PostBusRequest {
    @SerializedName("bus_idx")
    private String busIdx;

    public PostBusRequest(String busIdx) {
        this.busIdx = busIdx;
    }

    public String getBusIdx() {
        return busIdx;
    }

    public void setBusIdx(String busIdx) {
        this.busIdx = busIdx;
    }
}
