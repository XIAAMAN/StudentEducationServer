package com.nchu.xiaaman.student_education.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sys_exercise")
public class SysExercise {
    @Id
    @Column(name = "exercise_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String exerciseId;

    @Column(name = "exercise_upload_user_name")
    private String exerciseUploadUserName;          //上传题目人

    @Column(name = "exercise_check_user_name")
    private String exerciseCheckUserName;          //上传审查人

    @Column(name = "exercise_name")
    private String exerciseName;        //题目名称

    @Column(name = "exercise_time")
    private String exerciseTime;        //题目上传时间

    @Column(name = "exercise_description")
    private String exerciseDescription;     //题目描述

    @Column(name = "exercise_input_example")
    private String exerciseInputExample;        //输入样例

    @Column(name = "exercise_output_example")
    private String exerciseOutputExample;       //输出样例

    @Column(name = "exercise_error_example")
    private String exerciseErrorExample;       //输出样例

    @Column(name = "exercise_warning")
    private String exerciseWarning;     //提示（警告）信息

    @Column(name = "exercise_code")
    private String exerciseCode;        //题目参考代码

    @Column(name = "exercise_score")
    private float exerciseScore;        //题目分值,默认为5.0


    @Column(name = "exercise_file_name")
    private String exerciseFileName;

    @Column(name = "exercise_file_url")
    private String exerciseFileUrl;

    @Column(name = "exercise_state")
    private int exerciseState;          //0表示未审核，1表示审核通过，默认为1

    @Column(name = "exercise_classify_cause")
    private int exerciseClassifyCause;      //题目分类依据，1表示按照题目标签，2表示按照章节，3表示其他，默认为1

    @Column(name = "exercise_label")
    private String exerciseLabel;           //题目标签，多个标签用空格隔开

    @Column(name = "exercise_difficult_value")
    private float exerciseDifficultValue;      //难度系数，值为1-5，默认为3.0

    @Column(name = "exercise_type")
    private int exerciseType;               //1表示编程题，2表示选择题，3表示判断题、4表示填空题，默认为1

    @Column(name = "exercise_status")
    private int exerciseStatus;             //0表示不可见，1表示可见，默认为1.当删除时，才将状态设置为0

    @Column(name = "exercise_language")
    private String exerciseLanguage;

    @Column(name = "exercise_free")
    private int exerciseFree;       //是否开放给学生，1表示是，0表示否，默认为1

    public int getExerciseFree() {
        return exerciseFree;
    }

    public void setExerciseFree(int exerciseFree) {
        this.exerciseFree = exerciseFree;
    }

    public String getExerciseLanguage() {
        return exerciseLanguage;
    }

    public void setExerciseLanguage(String exerciseLanguage) {
        this.exerciseLanguage = exerciseLanguage;
    }

    public String getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(String exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseUploadUserName() {
        return exerciseUploadUserName;
    }

    public void setExerciseUploadUserName(String exerciseUploadUserName) {
        this.exerciseUploadUserName = exerciseUploadUserName;
    }

    public String getExerciseCheckUserName() {
        return exerciseCheckUserName;
    }

    public void setExerciseCheckUserName(String exerciseCheckUserName) {
        this.exerciseCheckUserName = exerciseCheckUserName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseTime() {
        return exerciseTime;
    }

    public void setExerciseTime(String exerciseTime) {
        this.exerciseTime = exerciseTime;
    }

    public String getExerciseDescription() {
        return exerciseDescription;
    }

    public void setExerciseDescription(String exerciseDescription) {
        this.exerciseDescription = exerciseDescription;
    }

    public String getExerciseInputExample() {
        return exerciseInputExample;
    }

    public void setExerciseInputExample(String exerciseInputExample) {
        this.exerciseInputExample = exerciseInputExample;
    }

    public String getExerciseOutputExample() {
        return exerciseOutputExample;
    }

    public void setExerciseOutputExample(String exerciseOutputExample) {
        this.exerciseOutputExample = exerciseOutputExample;
    }

    public String getExerciseWarning() {
        return exerciseWarning;
    }

    public void setExerciseWarning(String exerciseWarning) {
        this.exerciseWarning = exerciseWarning;
    }

    public String getExerciseCode() {
        return exerciseCode;
    }

    public void setExerciseCode(String exerciseCode) {
        this.exerciseCode = exerciseCode;
    }

    public float getExerciseScore() {
        return exerciseScore;
    }

    public void setExerciseScore(float exerciseScore) {
        this.exerciseScore = exerciseScore;
    }

    public String getExerciseFileName() {
        return exerciseFileName;
    }

    public void setExerciseFileName(String exerciseFileName) {
        this.exerciseFileName = exerciseFileName;
    }

    public String getExerciseFileUrl() {
        return exerciseFileUrl;
    }

    public void setExerciseFileUrl(String exerciseFileUrl) {
        this.exerciseFileUrl = exerciseFileUrl;
    }

    public int getExerciseState() {
        return exerciseState;
    }

    public void setExerciseState(int exerciseState) {
        this.exerciseState = exerciseState;
    }

    public int getExerciseClassifyCause() {
        return exerciseClassifyCause;
    }

    public void setExerciseClassifyCause(int exerciseClassifyCause) {
        this.exerciseClassifyCause = exerciseClassifyCause;
    }

    public String getExerciseLabel() {
        return exerciseLabel;
    }

    public void setExerciseLabel(String exerciseLabel) {
        this.exerciseLabel = exerciseLabel;
    }

    public float getExerciseDifficultValue() {
        return exerciseDifficultValue;
    }

    public void setExerciseDifficultValue(float exerciseDifficultValue) {
        this.exerciseDifficultValue = exerciseDifficultValue;
    }

    public int getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(int exerciseType) {
        this.exerciseType = exerciseType;
    }

    public int getExerciseStatus() {
        return exerciseStatus;
    }

    public void setExerciseStatus(int exerciseStatus) {
        this.exerciseStatus = exerciseStatus;
    }

    public String getExerciseErrorExample() {
        return exerciseErrorExample;
    }

    public void setExerciseErrorExample(String exerciseErrorExample) {
        this.exerciseErrorExample = exerciseErrorExample;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
