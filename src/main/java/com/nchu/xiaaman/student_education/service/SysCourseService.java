package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysCourseService {
    Page<SysCourse> getCourse(Pageable pageable);
    void saveCourse(SysCourse sysCourse);
    SysCourse getByCourseId(String courseId);
    Page<SysCourse> getCourse(String classNumber, Pageable pageable);
    Page<SysCourse> getTeacherCourse(String courseUserRealName, Pageable pageable);
    String getCourseIdByName(String courseName);
    List<SysCourse> getCourseId(String classNumber);
    SysCourse getBycourseNameandClass(String courseName, String courseClass);
}
