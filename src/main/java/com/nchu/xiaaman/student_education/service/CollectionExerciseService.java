package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.CollectionExercise;

import java.util.List;

public interface CollectionExerciseService {
    void deleteByCollectionId(String collectionId);
    void saveCollectionExercise(CollectionExercise collectionExercise);
    CollectionExercise getByCollectionIdAndExerciseId(String collectionId, String exerciseId);
    List<String> getExerciseIdListByCollectionId(String collectionId);
    void deleteByCollectionIdAndExerciseId(String collectionId, String exerciseId);
}
