package kr.hs.dgsw.smartschool.dodamdodam.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {

    Integer first;
    Integer second;
    Integer third;
    Integer fourth;

    public Location(List<Place> placeList){
        if ( placeList.get(0).getIdx() != null)
            setFirst(placeList.get(0).getIdx());
        if ( placeList.get(1).getIdx() != null)
            second = placeList.get(1).getIdx();
        if ( placeList.get(2).getIdx() != null)
            third = placeList.get(2).getIdx();
        if ( placeList.get(3).getIdx() != null)
            fourth = placeList.get(3).getIdx();
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getThird() {
        return third;
    }

    public void setThird(Integer third) {
        this.third = third;
    }

    public Integer getFourth() {
        return fourth;
    }

    public void setFourth(Integer fourth) {
        this.fourth = fourth;
    }

    @Override
    public String toString() {
        return "Location{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                ", fourth=" + fourth +
                '}';
    }
}
