package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.ClassTeacher;

public interface ClassTeacherService {
    void saveClassTeacher(ClassTeacher classTeacher);
    String getUserIdByClassId(String classId);
}
