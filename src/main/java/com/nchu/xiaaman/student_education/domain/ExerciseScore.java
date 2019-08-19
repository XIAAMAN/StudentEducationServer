package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//题目成绩表，学生实验答题记录会存在这里
@Entity
@Table(name = "exercise_score")
public class ExerciseScore {

    @Id
    @Column(name = "exercise_score_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String exerciseScoreId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "exercise_id")
    private String exerciseId;

    @Column(name = "collection_id")
    private String collectionId;

    @Column(name = "exercise_score_auto_grade")
    private float exerciseScoreAutoGrade;       //自动批改成绩

    @Column(name = "exercise_score_manual_grade")
    private float exerciseScoreManualGrade;     //手动批改成绩

    @Column(name = "exercise_score_times")
    private int exerciseScoreTimes;      //提交次数

    @Column(name = "exercise_code")
    private String exerciseCode;           //提交代码

    @Column(name = "user_name")
    private String userName;

    public String getExerciseScoreId() {
        return exerciseScoreId;
    }

    public void setExerciseScoreId(String exerciseScoreId) {
        this.exerciseScoreId = exerciseScoreId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public float getExerciseScoreAutoGrade() {
        return exerciseScoreAutoGrade;
    }

    public void setExerciseScoreAutoGrade(float exerciseScoreAutoGrade) {
        this.exerciseScoreAutoGrade = exerciseScoreAutoGrade;
    }

    public float getExerciseScoreManualGrade() {
        return exerciseScoreManualGrade;
    }

    public void setExerciseScoreManualGrade(float exerciseScoreManualGrade) {
        this.exerciseScoreManualGrade = exerciseScoreManualGrade;
    }

    public int getExerciseScoreTimes() {
        return exerciseScoreTimes;
    }

    public void setExerciseScoreTimes(int exerciseScoreTimes) {
        this.exerciseScoreTimes = exerciseScoreTimes;
    }

    public String getExerciseCode() {
        return exerciseCode;
    }

    public void setExerciseCode(String exerciseCode) {
        this.exerciseCode = exerciseCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
