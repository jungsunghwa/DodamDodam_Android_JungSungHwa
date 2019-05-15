package kr.hs.dgsw.smartschool.dodamdodam.Model;

import java.util.ArrayList;
import java.util.List;

import kr.hs.dgsw.b1nd.service.model.ClassInfo;

public class ClassInfoList extends ArrayList<ClassInfo> {
    public ClassInfoList(ArrayList<ClassInfo> classInfoList) {
        super(classInfoList);
    }

    public ClassInfoList() {

    }

    public List<Integer> getGradeList(){
        List gradeList = new ArrayList();

        for (ClassInfo classInfo : this) {
            gradeList.add(classInfo.getGrade());
        }

        return  gradeList;
    }

    public List<Integer> getClassList(){
        List classList = new ArrayList();

        for (ClassInfo classInfo : this) {
            classList.add(classInfo.getRoom());
        }

        return  classList;
    }
}
