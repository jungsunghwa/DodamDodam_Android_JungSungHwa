package kr.hs.dgsw.smartschool.dodamdodam.Model.bus;

public class Type {
    private Integer idx;
    private String name;
    private Integer date;
    private Integer people_limit;
    private String arrive_time;
    private Integer passengers_count;

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getPeople_limit() {
        return people_limit;
    }

    public void setPeople_limit(Integer people_limit) {
        this.people_limit = people_limit;
    }

    public String getArrive_time() {
        return arrive_time;
    }

    public void setArrive_time(String arrive_time) {
        this.arrive_time = arrive_time;
    }

    public Integer getPassengers_count() {
        return passengers_count;
    }

    public void setPassengers_count(Integer passengers_count) {
        this.passengers_count = passengers_count;
    }
}
