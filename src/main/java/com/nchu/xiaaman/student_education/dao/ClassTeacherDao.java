package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.ClassTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassTeacherDao extends JpaRepository<ClassTeacher, String> {
}
