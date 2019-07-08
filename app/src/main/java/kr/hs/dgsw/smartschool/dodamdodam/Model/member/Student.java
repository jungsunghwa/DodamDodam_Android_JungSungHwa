package kr.hs.dgsw.smartschool.dodamdodam.Model.member;

import kr.hs.dgsw.b1nd.service.model.ClassInfo;
import kr.hs.dgsw.b1nd.service.model.Member;

public class Student extends kr.hs.dgsw.b1nd.service.model.Student {
    ClassInfo classInfo;

    public Student() {

    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public Student(Member member) {
        super(member);
    }

    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
}
