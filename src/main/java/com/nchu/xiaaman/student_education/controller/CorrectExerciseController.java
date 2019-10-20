package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.ExerciseScore;
import com.nchu.xiaaman.student_education.domain.SysExercise;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.*;
import com.nchu.xiaaman.student_education.utils.ExerciseDetailData;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/correct")
public class CorrectExerciseController {
    @Autowired
    private ExerciseScoreService exerciseScoreService;

    @Autowired
    private SysCourseService sysCourseService;

    @Autowired
    private CourseCollectionService courseCollectionService;

    @Autowired
    private SysCollectionService sysCollectionService;

    @Autowired
    private CollectionExerciseService collectionExerciseService;

    @Autowired
    private SysExerciseService sysExerciseService;

    @Autowired
    private ClassTeacherService classTeacherService;

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    private SysUserService sysUserService;
    @MyLog(value = "查询批改题目")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/getExercise")
    // 查询题库
    public String getSysExerciseList(@RequestParam(value = "page",defaultValue = "1") int page,
                                     @RequestParam(value = "size",defaultValue = "10") int size,
                                     @RequestParam(value = "classNumber") String classNumber,
                                     @RequestParam(value = "courseName") String courseName,
                                     @RequestParam(value = "collectionName") String collectionName) {
        //判断该班级是否有这门课程
        if(sysCourseService.getBycourseNameandClass(courseName, "%"+classNumber+"%") == null) {
            return "400";
        }
        //判断该课程是否有该题目集
        String collectionId = sysCollectionService.getByCollectionName(collectionName).getCollectionId();
        String courseId = sysCourseService.getCourseIdByName(courseName);
        if(courseCollectionService.getByCollectionIdAndCourseId(collectionId, courseId) == null) {
            return "400";
        }

        //该题目集下所有题目id
        List<String> exerciseIdList = collectionExerciseService.getExerciseIdListByCollectionId(collectionId);
        List<SysExercise> exerciseList = new ArrayList<>();
        SysExercise exercise;
        for(int i=0; i<exerciseIdList.size(); i++) {
            exercise = sysExerciseService.getById(exerciseIdList.get(i));
            if(exercise.getExerciseType() == 6) {
                exerciseList.add(exercise);
            }
        }
        return new CorrectExerciseUnion().union(exerciseList, page, size);
    }

    @MyLog(value = "批改题目详情")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/exerciseDetail")
    // 查询题库
    public String exerciseDetail(@RequestParam(value = "exerciseId") String exerciseId,
                                     @RequestParam(value = "classNumber") String classNumber,
                                     @RequestParam(value = "collectionName") String collectionName) {
        String collectionId = sysCollectionService.getByCollectionName(collectionName).getCollectionId();
        //获得该班级学生用户
        List<SysUser> userList = sysUserService.getUserListByClass(classNumber);
        String exerciseName = sysExerciseService.getById(exerciseId).getExerciseName();
        ExerciseScore exerciseScore;
        ExerciseDetailData exerciseDetailData;
        List<ExerciseDetailData> exerciseDetailDataList = new ArrayList<>();
        for(int i=0; i<userList.size(); i++) {
            exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseId, userList.get(i).getUserId(), collectionId);
            if(exerciseScore != null) {
                exerciseDetailData = new ExerciseDetailData();
                exerciseDetailData.setExerciseCode(exerciseScore.getExerciseCode());    //代码
                exerciseDetailData.setExerciseName(exerciseName);       //题目名称
                exerciseDetailData.setUserRealName(userList.get(i).getUserRealName());      //姓名
                exerciseDetailData.setUserNumber(userList.get(i).getUserNumber());  //学号
                exerciseDetailData.setUserId(userList.get(i).getUserId());
                exerciseDetailData.setCollectionId(collectionId);
                exerciseDetailData.setExerciseId(exerciseId);
                exerciseDetailData.setTimes(exerciseScore.getExerciseScoreManualGrade());
                exerciseDetailData.setExerciseScore(exerciseScore.getExerciseScoreAutoGrade());
                exerciseDetailDataList.add(exerciseDetailData);
            }
        }

        return JSONObject.toJSONString(exerciseDetailDataList);
    }

    //更改主观题分数
    @MyLog(value = "修改主观题分数")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/submit")
    public int submitScore(@RequestBody ExerciseDetailData exerciseDetailData) {
        ExerciseScore exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseDetailData.getExerciseId(),
                exerciseDetailData.getUserId(), exerciseDetailData.getCollectionId());
        exerciseScore.setExerciseScoreAutoGrade(exerciseDetailData.getExerciseScore());
        exerciseScore.setExerciseScoreManualGrade(exerciseDetailData.getTimes());
        exerciseScoreService.saveExerciseScore(exerciseScore);
        return 200;
    }



    class CorrectExerciseUnion{
        private List<SysExercise> content;
        private long totalElements;

        public List<SysExercise> getContent() {
            return content;
        }

        public void setContent(List<SysExercise> content) {
            this.content = content;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }

        public String union(List<SysExercise> scoreDataList, int page, int size) {
            int length = scoreDataList.size();
            CorrectExerciseUnion correctExerciseUnion = new CorrectExerciseUnion();
            correctExerciseUnion.setContent(scoreDataList);
            correctExerciseUnion.setTotalElements(length);
            return correctExerciseUnion.toString();
        }
    }

}
