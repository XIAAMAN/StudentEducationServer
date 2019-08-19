package com.nchu.xiaaman.student_education.utils;

import com.nchu.xiaaman.student_education.domain.SysExercise;

public class UnionData {
    private SysExercise exercise;
    private String collectionId;

    public SysExercise getExercise() {
        return exercise;
    }

    public void setExercise(SysExercise exercise) {
        this.exercise = exercise;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
