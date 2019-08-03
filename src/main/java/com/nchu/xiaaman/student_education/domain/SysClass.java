package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sys_class")
public class SysClass {
    @Id
    @Column(name = "class_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略

    private String classId;

    @Column(name = "class_number")
    private String classNumber;        //班级

    @Column(name = "class_people")
    private int classPeople;        //班级人数

    @Column(name = "class_grade")
    private int classGrade;         //年级

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public int getClassPeople() {
        return classPeople;
    }

    public void setClassPeople(int classPeople) {
        this.classPeople = classPeople;
    }

    public int getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(int classGrade) {
        this.classGrade = classGrade;
    }
}
