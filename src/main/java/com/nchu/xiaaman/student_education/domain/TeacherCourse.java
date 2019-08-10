package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "teacher_course")
public class TeacherCourse {
    @Id
    @Column(name = "teacher_course_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String teacherCourseId;

    @Column(name = "user_id")
    private String userId;      //教师id

    @Column(name = "course_id")
    private String courseId;

    public String getTeacherCourseId() {
        return teacherCourseId;
    }

    public void setTeacherCourseId(String teacherCourseId) {
        this.teacherCourseId = teacherCourseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
