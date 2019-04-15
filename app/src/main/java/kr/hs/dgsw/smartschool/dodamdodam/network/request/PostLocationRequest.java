package kr.hs.dgsw.smartschool.dodamdodam.network.request;

import java.util.ArrayList;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Place;

public class PostLocationRequest {
    int first;
    int second;
    int third;
    int fourth;

    public PostLocationRequest(ArrayList<Place> placeList){
        first = placeList.get(0).getIdx();
        second = placeList.get(1).getIdx();
        third = placeList.get(2).getIdx();
        fourth = placeList.get(3).getIdx();
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getThird() {
        return third;
    }

    public void setThird(int third) {
        this.third = third;
    }

    public int getFourth() {
        return fourth;
    }

    public void setFourth(int fourth) {
        this.fourth = fourth;
    }
}
