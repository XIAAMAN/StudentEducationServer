package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.ExerciseScore;

import java.util.List;

public interface ExerciseScoreService {
    ExerciseScore getByUserIdExerciseIdAndCollectionId(String exerciseId, String userId, String collectionId);
    void saveExerciseScore(ExerciseScore exerciseScore);
    ExerciseScore getById(String id);
    List<ExerciseScore> getByExerciseId(String exerciseId);
    List<ExerciseScore> getByUserId(String userId);
    List<ExerciseScore> getAll();

}
