package kr.hs.dgsw.smartschool.dodamdodam.Model.bus;

public class Bus {
    private Integer idx;
    private Integer type;
    private Integer student_idx;

    public Bus() {
    }

    public Bus(Integer type) {
        this.type = type;
    }

    public Bus(Integer idx, Integer type, Integer student_idx) {
        this.idx = idx;
        this.type = type;
        this.student_idx = student_idx;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStudent_idx() {
        return student_idx;
    }

    public void setStudent_idx(Integer student_idx) {
        this.student_idx = student_idx;
    }
}
