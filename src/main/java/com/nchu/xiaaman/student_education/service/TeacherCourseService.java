package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.TeacherCourse;

import java.util.List;

public interface TeacherCourseService {
    List<String> getCourseIdListByUserId(String userId);
    void saveTeacherCourse(TeacherCourse teacherCourse);
    void deleteByCourseId(String courseId);
}
