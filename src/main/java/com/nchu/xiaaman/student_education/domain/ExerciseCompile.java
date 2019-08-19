package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

//题目成绩表，学生实验答题记录会存在这里
@Entity
@Table(name = "exercise_compile")
public class ExerciseCompile {
    @Id
    @Column(name = "exercise_compile_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String exerciseCompileId;

    @Column(name = "exercise_id")
    private String exerciseId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "exercise_compile_content")
    private String exerciseCompileContent;      //编译信息

    @Column(name = "exercise_compile_time")
    private String exerciseCompileTime;

    @Column(name = "exercise_compile_error_label")
    private String exerciseCompileErrorLabel;

    @Column(name = "exercise_code")
    private String exerciseCode;

    public String getExerciseCode() {
        return exerciseCode;
    }

    public void setExerciseCode(String exerciseCode) {
        this.exerciseCode = exerciseCode;
    }

    public String getExerciseCompileId() {
        return exerciseCompileId;
    }

    public void setExerciseCompileId(String exerciseCompileId) {
        this.exerciseCompileId = exerciseCompileId;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExerciseCompileContent() {
        return exerciseCompileContent;
    }

    public void setExerciseCompileContent(String exerciseCompileContent) {
        this.exerciseCompileContent = exerciseCompileContent;
    }

    public String getExerciseCompileTime() {
        return exerciseCompileTime;
    }

    public void setExerciseCompileTime(String exerciseCompileTime) {
        this.exerciseCompileTime = exerciseCompileTime;
    }

    public String getExerciseCompileErrorLabel() {
        return exerciseCompileErrorLabel;
    }

    public void setExerciseCompileErrorLabel(String exerciseCompileErrorLabel) {
        this.exerciseCompileErrorLabel = exerciseCompileErrorLabel;
    }
}
