package com.nchu.xiaaman.student_education.controller;

import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.SysExercise;
import com.nchu.xiaaman.student_education.service.SysExerciseService;
import com.nchu.xiaaman.student_education.utils.ExerciseUnion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/sys_exercise")
public class SysExerciseController {
    @Autowired
    private SysExerciseService sysExerciseService;


    @MyLog(value = "查询题库")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/get")
    // 查询题库
    public String getSysExerciseList(@RequestParam(value = "page",defaultValue = "1") int page,
                                     @RequestParam(value = "size",defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<SysExercise> exercisePageList = sysExerciseService.getAll(pageable);
        // 对数据进行封装修改，不能对jpa查出的数据直接修改，否则数据库的数据也会跟着修改
        return new ExerciseUnion().union(exercisePageList.getContent(), exercisePageList.getTotalElements());
    }

    @MyLog(value = "删除题目")
    @RequestMapping(value = "/delete")
    public int deleteExercise(@RequestParam(value = "exerciseId") String exerciseId) {
        sysExerciseService.deleteExerciseById(exerciseId);
        return 200;
    }

    @MyLog(value = "修改题目")
    @RequestMapping(value = "/modify")
    public int modifyExercise(@RequestBody SysExercise sysExercise) {
        sysExerciseService.saveOrModifyExercise(sysExercise);
        return 200;
    }

    @MyLog(value = "增加题目")
    @RequestMapping(value = "/add")
    public int addExercise(@RequestBody SysExercise sysExercise) {
//        sysExerciseService.saveOrModifyExercise(sysExercise);
        System.out.println(sysExercise.toString());
        return 200;
    }


}
