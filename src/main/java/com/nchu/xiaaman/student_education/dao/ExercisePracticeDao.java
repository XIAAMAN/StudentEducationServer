package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.ExercisePractice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExercisePracticeDao extends JpaRepository<ExercisePractice, String> {
    //根据用户id和题目id查询
    @Query(value = "select * from exercise_practice where exercise_id = ?1 and user_id = ?2", nativeQuery = true)
    ExercisePractice getByUserIdAndExerciseId(String exerciseId, String userId);
}
