package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "exercise_label")
// 题目标签类
public class ExerciseLabel {
    @Id
    @Column(name = "exercise_label_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String exerciseLabelId;

    @Column(name = "exercise_label_name")
    private String exerciseLabelName;

    @Column(name = "exercise_label_name_value")
    private String exerciseLabelNameValue;

    @Column(name = "exercise_label_language")
    private String exerciseLabelLanguage;

    @Column(name = "exercise_label_description")
    private String exerciseLabelDescription;

    public String getExerciseLabelId() {
        return exerciseLabelId;
    }

    public void setExerciseLabelId(String exerciseLabelId) {
        this.exerciseLabelId = exerciseLabelId;
    }

    public String getExerciseLabelName() {
        return exerciseLabelName;
    }

    public void setExerciseLabelName(String exerciseLabelName) {
        this.exerciseLabelName = exerciseLabelName;
    }

    public String getExerciseLabelNameValue() {
        return exerciseLabelNameValue;
    }

    public void setExerciseLabelNameValue(String exerciseLabelNameValue) {
        this.exerciseLabelNameValue = exerciseLabelNameValue;
    }

    public String getExerciseLabelLanguage() {
        return exerciseLabelLanguage;
    }

    public void setExerciseLabelLanguage(String exerciseLabelLanguage) {
        this.exerciseLabelLanguage = exerciseLabelLanguage;
    }

    public String getExerciseLabelDescription() {
        return exerciseLabelDescription;
    }

    public void setExerciseLabelDescription(String exerciseLabelDescription) {
        this.exerciseLabelDescription = exerciseLabelDescription;
    }
}
