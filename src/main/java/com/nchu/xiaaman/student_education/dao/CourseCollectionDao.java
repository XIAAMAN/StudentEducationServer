package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.CourseCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CourseCollectionDao extends JpaRepository<CourseCollection, String> {
    //根据课程id查询题目集id
    @Query(value = "select collection_id from course_collection where course_id = ?1", nativeQuery = true)
    List<String> getCollectionIdListByCourseId(String courseId);

    //根据课程名称删除里面所有的题目集
    @Transactional
    @Modifying
    @Query(value = "delete from course_collection where course_id = ?1", nativeQuery = true)
    void deleteCollectionListByCourseId(String courseId);

}
