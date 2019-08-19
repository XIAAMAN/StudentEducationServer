package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//题目练习表
@Entity
@Table(name = "exercise_practice")
public class ExercisePractice {
    @Id
    @Column(name = "exercise_practice_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String exercisePracticeId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "exercise_id")
    private String exerciseId;

    @Column(name = "exercise_code")
    private String exerciseCode;        //提交题目代码

    @Column(name = "exercise_practice_time")
    private String exercisePracticeTime;

    @Column(name = "exercise_practice_number")
    private int exercisePracticeNumber;     //提交次数

    public String getExercisePracticeId() {
        return exercisePracticeId;
    }

    public void setExercisePracticeId(String exercisePracticeId) {
        this.exercisePracticeId = exercisePracticeId;
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

    public String getExerciseCode() {
        return exerciseCode;
    }

    public void setExerciseCode(String exerciseCode) {
        this.exerciseCode = exerciseCode;
    }

    public String getExercisePracticeTime() {
        return exercisePracticeTime;
    }

    public void setExercisePracticeTime(String exercisePracticeTime) {
        this.exercisePracticeTime = exercisePracticeTime;
    }

    public int getExercisePracticeNumber() {
        return exercisePracticeNumber;
    }

    public void setExercisePracticeNumber(int exercisePracticeNumber) {
        this.exercisePracticeNumber = exercisePracticeNumber;
    }
}
