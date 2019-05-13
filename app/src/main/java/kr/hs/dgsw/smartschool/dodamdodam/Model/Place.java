package kr.hs.dgsw.smartschool.dodamdodam.Model;

import androidx.annotation.NonNull;

public class Place {
    private Integer idx;
    private String name;

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

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
