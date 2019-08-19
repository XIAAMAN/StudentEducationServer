package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.ExerciseScore;

public interface ExerciseScoreService {
    ExerciseScore getByUserIdExerciseIdAndCollectionId(String exerciseId, String userId, String collectionId);
    void saveExerciseScore(ExerciseScore exerciseScore);
    ExerciseScore getById(String id);
}
