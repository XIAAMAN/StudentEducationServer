package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.*;
import com.nchu.xiaaman.student_education.service.*;
import com.nchu.xiaaman.student_education.utils.TempCollectionExercise;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/sys_collection")
public class SysCollectionController {
    @Autowired
    private ExerciseScoreService exerciseScoreService;

    @Autowired
    private SysCourseService sysCourseService;

    @Autowired
    private SysCollectionService sysCollectionService;

    @Autowired
    private CollectionExerciseService collectionExerciseService;

    @Autowired
    private SysExerciseService sysExerciseService;

    @Autowired
    private CourseCollectionService courseCollectionService;

    @MyLog(value = "查询题目集")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/get")
    public String getCollectionList(@RequestParam(value = "page",defaultValue = "1") int page,
                             @RequestParam(value = "size",defaultValue = "10") int size,@RequestParam(value = "courseId") String courseId) {

        Pageable pageable= PageRequest.of(page-1, size);
        if(courseId.equals("null")) {
            return JSONObject.toJSONString(sysCollectionService.getCollection(pageable));
        }else {
            return JSONObject.toJSONString(sysCollectionService.getCollectionByCourseId(courseId, pageable));
        }


    }

    @MyLog(value = "增加题目集")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/add")
    public int addCollection(@RequestBody SysCollection sysCollection) {
        //先判断该题目集名称是否存在
        if(sysCollectionService.getByCollectionName(sysCollection.getCollectionName()) == null) {
            sysCollectionService.saveCollection(sysCollection);
            return 200;
        }else {
            //该题目名称已存在
            return 400;
        }

    }

    @MyLog(value = "修改题目集")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/update")
    public int updateCollection(@RequestBody SysCollection sysCollection) {
        SysCollection collection = sysCollectionService.getById(sysCollection.getCollectionId());
        collection.setCollectionName(sysCollection.getCollectionName());
        collection.setCollectionStartTime(sysCollection.getCollectionStartTime());
        collection.setCollectionEndTime(sysCollection.getCollectionEndTime());
        sysCollectionService.saveCollection(collection);
        return 200;
    }

    @MyLog(value = "删除题目集")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/delete")
    public int deleteCollection(@RequestParam("collectionId") String collectionId) {
        //删除题目集需要先删除题目集题目表中的数据，再删除题目集表中的数据
        collectionExerciseService.deleteByCollectionId(collectionId);
        sysCollectionService.deleteById(collectionId);
        return 200;
    }

    @MyLog(value = "题目集增加题目")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/addExercise")
    public int addExercise(@RequestBody TempCollectionExercise tempCollectionExercise) {
        CollectionExercise collectionExercise;
        //查找该题目集下所有的题目
        List<String> exerciseIdList = collectionExerciseService.getExerciseIdListByCollectionId(tempCollectionExercise.getCollectionId());
        //根据题目id查找所有题目
        List<SysExercise> sysExerciseList = new ArrayList<>();
        for(int i=0; i<exerciseIdList.size(); i++) {
            sysExerciseList.add(sysExerciseService.getById(exerciseIdList.get(i)));
        }
        //查找当前题目类型的题目id
        List<String> exerciseIdTypeList = new ArrayList<>();
        for(int i=0; i<sysExerciseList.size(); i++) {
            if(sysExerciseList.get(i).getExerciseType() == tempCollectionExercise.getExerciseType()) {
                exerciseIdTypeList.add(sysExerciseList.get(i).getExerciseId());
            }
        }
        //删除题目集中该类型的题目
        for(int i=0; i<exerciseIdTypeList.size(); i++) {
            collectionExerciseService.deleteByCollectionIdAndExerciseId(tempCollectionExercise.getCollectionId(), exerciseIdTypeList.get(i));
        }

        for(int i=0; i<tempCollectionExercise.getExerciseListValue().length; i++) {
            //判断该题目是否已经添加到题目集中
            String exerciseId = sysExerciseService.getExerciseIdByDescription(tempCollectionExercise.getExerciseListValue()[i]);
            collectionExercise = new CollectionExercise();
            collectionExercise.setCollectionId(tempCollectionExercise.getCollectionId());
            collectionExercise.setExerciseId(exerciseId);
            collectionExerciseService.saveCollectionExercise(collectionExercise);
        }
        return 200;
    }

    //查询题目集已有题目名称
    @RequestMapping(value = "/getProgramExerciseName")
    public String getCollectionExerciseNameList(@RequestParam("collectionId") String collectionId,
                                                @RequestParam("exerciseType") int exerciseType) {
        //根据题目集id查询该题目集下所有题目
        List<String> exerciseIdList = collectionExerciseService.getExerciseIdListByCollectionId(collectionId);
        List<String> exerciseNameList = new ArrayList<>();
        SysExercise sysExercise;
        for(int i=0; i<exerciseIdList.size(); i++) {
            sysExercise = sysExerciseService.getById(exerciseIdList.get(i));
            //添加当前题目类型
            if(sysExercise.getExerciseType() == exerciseType) {
                exerciseNameList.add(sysExercise.getExerciseDescription());
            }

        }

        return JSONObject.toJSONString(exerciseNameList);
    }

    //根据题目集id查询所有题目
    @RequestMapping(value = "/getExerciseList")
    public String getExerciseList(@RequestParam("collectionId") String collectionId, HttpSession session) {
        List<SysExercise> exerciseList = new ArrayList<>();
        List<SysExercise> exerciseListCopy = new ArrayList<>();
        SysUser user = (SysUser)session.getAttribute("user");
        ExerciseScore exerciseScore;
        //获得所有题目id
        List<String> exerciseId = collectionExerciseService.getExerciseIdListByCollectionId(collectionId);
        for(int i=0; i<exerciseId.size(); i++) {
            exerciseList.add(sysExerciseService.getById(exerciseId.get(i)));
        }
        for(int i=0; i<exerciseList.size(); i++) {
            SysExercise exercise = new SysExercise();
            exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseList.get(i).getExerciseId(), user.getUserId(), collectionId);
            BeanUtils.copyProperties(exerciseList.get(i), exercise);
            if(exerciseScore != null) {
                exercise.setExerciseCode(exerciseScore.getExerciseCode());
            } else {
                exercise.setExerciseCode("");
            }
            exerciseListCopy.add(exercise);
        }
        return exerciseListCopy.toString();
    }
    //查询所有可用题目集名称
    @RequestMapping(value = "/getCollectionName")
    public String getCollectionNameList() {
        return JSONObject.toJSONString(sysCollectionService.getCollectionName());
    }

    //判断题目集是否已经使用，如果已经使用则不可以删除
    @RequestMapping(value = "/judgeCollection")
    public int judgeCollectionIsUsed(@RequestParam("collectionId") String collectionId) {
        List<CourseCollection> courseCollections = courseCollectionService.getByCollectionId(collectionId);
        if(courseCollections != null && courseCollections.size()>0) {
            return 400;
        } else {
            return 200;     //表示该题目集未被添加到课程中，可以删除
        }
    }

    @RequestMapping(value = "/getACollectionName")
    public String getNameByCollectionId(@RequestParam("collectionId") String collectionId){
        String collectionName = sysCollectionService.getById(collectionId).getCollectionName();
        return JSONObject.toJSONString(collectionName);
    }

    @RequestMapping(value = "/getReport")
    public String getAllReport(HttpSession session) {
        List<String> collectionId, exerciseIdList;
        SysExercise exercise;
        List<SysExercise> exerciseTempList = new ArrayList<>();
        List<SysExercise> exerciseList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(new Date());
        List<SysCollection> collectionList = new ArrayList<>();
        SysUser user = (SysUser) session.getAttribute("user");
        int rankValue =(int) session.getAttribute("rankValue");
        if(rankValue == 10) {
            collectionList = sysCollectionService.getAllCollection();
        }else {
            List<SysCourse> courseList = sysCourseService.getCourseId('%' + user.getUserClass() + '%');
            for(int i=0; i<courseList.size(); i++) {
                collectionId = courseCollectionService.getCollectionIdListByCourseId(courseList.get(i).getCourseId());
                for(int j=0; j<collectionId.size(); j++) {
                    collectionList.add(sysCollectionService.getById(collectionId.get(j)));
                }
            }
        }


        //题目集已结束
        for(int i=0; i<collectionList.size(); i++) {
            if(collectionList.get(i).getCollectionEndTime().compareTo(dateTime)<0) {
                exerciseIdList = collectionExerciseService.getExerciseIdListByCollectionId(collectionList.get(i).getCollectionId());
                if(exerciseIdList != null && exerciseIdList.size()>0) {
                    for(int j=0; j<exerciseIdList.size(); j++) {
                        exercise = new SysExercise();
                        BeanUtils.copyProperties(sysExerciseService.getById(exerciseIdList.get(j)), exercise);
                        exercise.setExerciseWarning(collectionList.get(i).getCollectionId());
                        exercise.setExerciseInputExample(collectionList.get(i).getCollectionName());
                        exerciseTempList.add(exercise);
                    }
                }
            }
        }


        for(int i=0; i<exerciseTempList.size(); i++) {
            if(exerciseTempList.get(i).getExerciseType() == 6 && exerciseTempList.get(i).getExerciseName().contains("实验报告")
                    && exerciseTempList.get(i).getExerciseDescription().length() < 20) {
                exerciseList.add(exerciseTempList.get(i));
            }
        }
        return exerciseList.toString();
    }
}
