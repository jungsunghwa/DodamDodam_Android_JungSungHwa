package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import com.google.gson.annotations.SerializedName;

public class BusRequest {
    @SerializedName("bus_idx")
    private String busIdx;

    @SerializedName("origin_bus_idx")
    private String originBusIdx;

    public BusRequest(String busIdx, String originBusIdx) {
        this.busIdx = busIdx;
        this.originBusIdx = originBusIdx;
    }

    public String getBusIdx() {
        return busIdx;
    }

    public void setBusIdx(String busIdx) {
        this.busIdx = busIdx;
    }

    public String getOriginBusIdx() {
        return originBusIdx;
    }

    public void setOriginBusIdx(String originBusIdx) {
        this.originBusIdx = originBusIdx;
    }
}
