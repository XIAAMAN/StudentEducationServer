package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.ExerciseScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExerciseScoreDao extends JpaRepository<ExerciseScore, String> {
    //根据用户id和题目id、题目集id查询
    @Query(value = "select * from exercise_score where exercise_id = ?1 and user_id = ?2 and collection_id = ?3", nativeQuery = true)
    ExerciseScore getByUserIdExerciseIdAndCollectionId(String exerciseId, String userId, String collectionId);
}
