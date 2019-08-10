package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.ClassTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassTeacherDao extends JpaRepository<ClassTeacher, String> {
    //根据班级id查询教师id
    @Query(value = "select user_id from class_teacher where class_id = ?1", nativeQuery = true)
    String getUserIdByClassId(String classId);
}
