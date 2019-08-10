package com.nchu.xiaaman.student_education.controller;
import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.SysExercise;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.SysExerciseService;
import com.nchu.xiaaman.student_education.service.UserRoleService;
import com.nchu.xiaaman.student_education.utils.ExerciseUnion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/sys_exercise")
public class SysExerciseController {
    @Autowired
    private SysExerciseService sysExerciseService;
    @Autowired
    private UserRoleService userRoleService;


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

    @MyLog(value = "动态查询题库")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/dynamicGet")
    public String getSysExerciseListByNameAndLabel(@RequestParam(value = "page",defaultValue = "1") int page,
                                                  @RequestParam(value = "size",defaultValue = "10") int size,
                                                  @RequestParam(value = "exerciseName") String exerciseName,
                                                  @RequestParam(value = "exerciseLabel") String exerciseLabel){
        Pageable pageable= PageRequest.of(page-1, size);
        Page<SysExercise> exercisePageList = sysExerciseService.getSysExerciseByNameAndLabel("%"+exerciseName+"%", "%"+exerciseLabel+"%", pageable);
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
        SysExercise newExercise = sysExerciseService.getById(sysExercise.getExerciseId());
        newExercise.setExerciseName(sysExercise.getExerciseName());
        newExercise.setExerciseDescription(sysExercise.getExerciseDescription());
        newExercise.setExerciseInputExample(sysExercise.getExerciseInputExample());
        newExercise.setExerciseOutputExample(sysExercise.getExerciseOutputExample());
        newExercise.setExerciseWarning(sysExercise.getExerciseWarning());
        newExercise.setExerciseLabel(sysExercise.getExerciseLabel());
        sysExerciseService.saveExercise(newExercise);
        return 200;
    }

    @MyLog(value = "增加题目")
    @RequestMapping(value = "/add")
    public int addExercise(@RequestBody SysExercise sysExercise) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        sysExercise.setExerciseState(0);
        sysExercise.setExerciseTime(time);
        //设置一些默认值
        sysExercise.setExerciseFree(1);
        sysExercise.setExerciseLanguage("C");
        sysExercise.setExerciseStatus(1);
        sysExercise.setExerciseType(1);
        sysExercise.setExerciseScore(5);
        sysExercise.setExerciseDifficultValue("适中");
        sysExerciseService.saveExercise(sysExercise);
        return 200;
    }

    @RequestMapping(value = "/judgeExerciseName")
    public int judgeExerciseName(@RequestParam("exerciseName") String exerciseName) {
        SysExercise exercise = sysExerciseService.getSysExerciseByName(exerciseName);
        if(exercise==null) {
            return 200;
        } else {
            return 400;
        }
    }

    @MyLog(value = "查询待审核题目")
    @RequestMapping(value = "/check")
    public String getCheckExercise(@RequestParam(value = "page",defaultValue = "1") int page,
                                   @RequestParam(value = "size",defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<SysExercise> exercisePageList = sysExerciseService.getCheckExercise(pageable);
        // 对数据进行封装修改，不能对jpa查出的数据直接修改，否则数据库的数据也会跟着修改
        return new ExerciseUnion().union(exercisePageList.getContent(), exercisePageList.getTotalElements());
    }

    @MyLog(value = "通过题目审核")
    @RequestMapping(value = "/pass")
    public int passExercise(@RequestParam("exerciseId") String exerciseId, HttpSession session){
        SysUser user = (SysUser) session.getAttribute("user");
        sysExerciseService.modifyState(user.getUserName(), exerciseId);
        return 200;
    }

    @MyLog(value = "拒绝题目审核")
    @RequestMapping(value = "/refuse")
    public int refuseExercise(@RequestParam("exerciseId") String exerciseId, HttpSession session){
        SysUser user = (SysUser) session.getAttribute("user");
        sysExerciseService.modifyByIdAndCheckName(user.getUserName(), exerciseId);
        return 200;
    }

    @MyLog(value = "题目练习详情")
    @RequestMapping(value = "/practiceDetails")
    public String practiceExerciseDetails(@RequestParam("exerciseId") String exerciseId){
        return sysExerciseService.getById(exerciseId).toString();
    }

    @MyLog(value = "题目练习")
    @RequestMapping(value = "/practice")
    public String practiceExercise(@RequestParam(value = "page",defaultValue = "1") int page,
                                   @RequestParam(value = "size",defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<SysExercise> exercisePageList = sysExerciseService.getExerciseFree(pageable);
        // 对数据进行封装修改，不能对jpa查出的数据直接修改，否则数据库的数据也会跟着修改
        return new ExerciseUnion().union(exercisePageList.getContent(), exercisePageList.getTotalElements());
    }

    //查询所有可用通过审核的题目名称
    @RequestMapping(value = "/getName")
    public String getExerciseNameList() {
        return JSONObject.toJSONString(sysExerciseService.getExerciseNameList());
    }

}
