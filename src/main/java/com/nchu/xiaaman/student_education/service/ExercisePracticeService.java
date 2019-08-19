package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.ExercisePractice;

public interface ExercisePracticeService {
    ExercisePractice getByUserIdAndExerciseId(String exerciseId, String userId);
    void saveExercisePractice(ExercisePractice exercisePractice);
    ExercisePractice getById(String id);

}
