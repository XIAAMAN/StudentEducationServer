package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.CollectionExercise;
import com.nchu.xiaaman.student_education.domain.SysCollection;
import com.nchu.xiaaman.student_education.domain.SysExercise;
import com.nchu.xiaaman.student_education.service.CollectionExerciseService;
import com.nchu.xiaaman.student_education.service.SysCollectionService;
import com.nchu.xiaaman.student_education.service.SysExerciseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/sys_collection")
public class SysCollectionController {
    @Autowired
    private SysCollectionService sysCollectionService;

    @Autowired
    private CollectionExerciseService collectionExerciseService;

    @Autowired
    private SysExerciseService sysExerciseService;

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
    public int addExercise(@RequestParam("collectionId") String collectionId, @RequestParam("exerciseList") String[] exerciseList) {
        CollectionExercise collectionExercise;
        //先删除该题目集下的题目
        collectionExerciseService.deleteByCollectionId(collectionId);
        for(int i=0; i<exerciseList.length; i++) {
            //判断该题目是否已经添加到题目集中
            String exerciseId = sysExerciseService.getExerciseIdByName(exerciseList[i]);
            collectionExercise = new CollectionExercise();
            collectionExercise.setCollectionId(collectionId);
            collectionExercise.setExerciseId(exerciseId);
            collectionExerciseService.saveCollectionExercise(collectionExercise);

        }
        return 200;
    }

    //查询题目集已有题目名称
    @RequestMapping(value = "/getExerciseName")
    public String getCollectionExerciseNameList(@RequestParam("collectionId") String collectionId) {
        //根据题目集id查询该题目集下所有题目
        List<String> exerciseIdList = collectionExerciseService.getExerciseIdListByCollectionId(collectionId);
        List<String> exerciseNameList = new ArrayList<>();
        for(int i=0; i<exerciseIdList.size(); i++) {
            exerciseNameList.add(sysExerciseService.getById(exerciseIdList.get(i)).getExerciseName());
        }

        return JSONObject.toJSONString(exerciseNameList);
    }

    //根据题目集id查询所有题目
    @RequestMapping(value = "/getExerciseList")
    public String getExerciseList(@RequestParam("collectionId") String collectionId) {
        List<SysExercise> exerciseList = new ArrayList<>();
        List<SysExercise> exerciseListCopy = new ArrayList<>();
        //获得所有题目id
        List<String> exerciseId = collectionExerciseService.getExerciseIdListByCollectionId(collectionId);
        for(int i=0; i<exerciseId.size(); i++) {
            exerciseList.add(sysExerciseService.getById(exerciseId.get(i)));
        }
        for(int i=0; i<exerciseList.size(); i++) {
            SysExercise exercise = new SysExercise();
            BeanUtils.copyProperties(exerciseList.get(i), exercise);
            exercise.setExerciseCode("");
            exerciseListCopy.add(exercise);
        }
        return exerciseListCopy.toString();
    }
    //查询所有可用题目集名称
    @RequestMapping(value = "/getCollectionName")
    public String getCollectionNameList() {
        return JSONObject.toJSONString(sysCollectionService.getCollectionName());
    }

}
