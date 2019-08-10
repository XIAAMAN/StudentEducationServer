package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TeacherCourseDao extends JpaRepository<TeacherCourse, String> {
    //根据教师id查询课程id
    @Query(value = "select course_id from teacher_course where user_id = ?1", nativeQuery = true)
    List<String> getCourseIdListByUserId(String userId);
    //根据课程id删除
    @Transactional
    @Modifying
    @Query(value = "delete from teacher_course where course_id = ?1", nativeQuery = true)
    void deleteByCourseId(String courseId);
}
