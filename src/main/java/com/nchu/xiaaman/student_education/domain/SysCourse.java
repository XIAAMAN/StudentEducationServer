package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sys_course")
public class SysCourse {
    @Id
    @Column(name = "course_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_type")       //课程类型，1表示课程，2表示比赛，3表示其他，默认为1
    private int courseType;

    @Column(name = "course_class")      //所创课程面向的班级，班级间用空格分开
    private String courseClass;

    @Column(name = "course_create_time")
    private String courseCreateTime;

    @Column(name = "course_create_user_name")
    private String courseCreateUserName;

    @Column(name = "course_language")
    private String courseLanguage;

    @Column(name = "course_user_real_name")
    private String courseUserRealName;      //授课教师姓名

    @Column(name = "course_status")     //表示课程的状态，默认为1，为0时不显示该课程
    private int courseStatus;
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(String courseClass) {
        this.courseClass = courseClass;
    }

    public String getCourseCreateTime() {
        return courseCreateTime;
    }

    public void setCourseCreateTime(String courseCreateTime) {
        this.courseCreateTime = courseCreateTime;
    }

    public String getCourseCreateUserName() {
        return courseCreateUserName;
    }

    public void setCourseCreateUserName(String courseCreateUserName) {
        this.courseCreateUserName = courseCreateUserName;
    }

    public String getCourseUserRealName() {
        return courseUserRealName;
    }

    public void setCourseUserRealName(String courseUserRealName) {
        this.courseUserRealName = courseUserRealName;
    }

    public String getCourseLanguage() {
        return courseLanguage;
    }

    public void setCourseLanguage(String courseLanguage) {
        this.courseLanguage = courseLanguage;
    }

    public int getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(int courseStatus) {
        this.courseStatus = courseStatus;
    }
}
