package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "collection_exercise")
public class CollectionExercise {
    @Id
    @Column(name = "collection_exercise_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String collectionExerciseId;

    @Column(name = "collection_id")
    private String collectionId;

    @Column(name = "exercise_id")
    private String exerciseId;

    public String getCollectionExerciseId() {
        return collectionExerciseId;
    }

    public void setCollectionExerciseId(String collectionExerciseId) {
        this.collectionExerciseId = collectionExerciseId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }
}
