package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysExercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysExerciseDao extends JpaRepository<SysExercise, String> {
    //查询题库
    @Query(value = "select * from sys_exercise where exercise_status = 1", nativeQuery = true)
    Page<SysExercise> getAll(Pageable pageable);
}
