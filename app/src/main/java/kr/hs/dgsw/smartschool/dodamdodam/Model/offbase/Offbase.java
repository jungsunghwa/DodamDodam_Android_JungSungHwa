package kr.hs.dgsw.smartschool.dodamdodam.Model.offbase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Offbase {
    private List<Leave> leaves;
    private List<Pass> passes;

    public List<Leave> getLeaves() {
        return leaves;
    }

    public List<Pass> getPasses() {
        return passes;
    }

    public List<OffbaseItem> getAll() {
        List<OffbaseItem> list = new ArrayList<>();
        list.addAll(leaves);
        list.addAll(passes);
        Collections.sort(list, (o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
        return list;
    }
}
