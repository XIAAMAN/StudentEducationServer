package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysExercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SysExerciseDao extends JpaRepository<SysExercise, String> {
    //查询题库
    @Query(value = "select * from sys_exercise where exercise_status = 1", nativeQuery = true)
    Page<SysExercise> getAll(Pageable pageable);

    //删除题目,设置为不可见
    @Transactional
    @Modifying
    @Query(value = "update sys_exercise set exercise_status = 0 where exercise_id =?1", nativeQuery = true)
    void deleteExerciseById(String exerciseId);
}
