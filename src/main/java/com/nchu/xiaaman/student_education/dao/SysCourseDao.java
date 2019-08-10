package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysCourseDao extends JpaRepository<SysCourse, String> {
    //分页查询
    @Query(value = "select * from sys_course where course_status = 1", nativeQuery = true)
    Page<SysCourse> getCourse(Pageable pageable);

    //学生查询课程
    @Query(value = "select * from sys_course where course_status = 1 and course_class like ?1", nativeQuery = true)
    Page<SysCourse> getCourse(String ClassNumber, Pageable pageable);

    //查询教师所授课程
    @Query(value = "select * from sys_course where course_status = 1 and course_user_real_name like ?1", nativeQuery = true)
    Page<SysCourse> getTeacherCourse(String courseUserRealName, Pageable pageable);

    //根据课程名查询课程Id
    @Query(value = "select course_id from sys_course where course_name = ?1 and course_status=1", nativeQuery = true)
    String getCourseIdByName(String courseName);

}
