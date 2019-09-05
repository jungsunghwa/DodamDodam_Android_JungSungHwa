package kr.hs.dgsw.smartschool.dodamdodam.Model.rule;

public class Rule {
    private Integer Idx;
    private Integer type;
    private Integer sub_type;
    private Integer point;

    private String content;
    private String remarks;

    public Integer getIdx() {
        return Idx;
    }

    public void setIdx(Integer idx) {
        Idx = idx;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSub_type() {
        return sub_type;
    }

    public void setSub_type(Integer sub_type) {
        this.sub_type = sub_type;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
