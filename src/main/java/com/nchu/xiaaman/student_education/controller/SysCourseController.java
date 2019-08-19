package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.*;
import com.nchu.xiaaman.student_education.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/sys_course")
public class SysCourseController {
    @Autowired
    private SysCourseService sysCourseService;

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private ClassTeacherService classTeacherService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CourseCollectionService courseCollectionService;

    @Autowired
    private SysCollectionService sysCollectionService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @MyLog(value = "查询课程")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/get")
    public String getLogList(@RequestParam(value = "page",defaultValue = "1") int page,
                             @RequestParam(value = "size",defaultValue = "10") int size,  HttpSession session) {

        Pageable pageable= PageRequest.of(page-1, size);
        SysUser user = (SysUser) session.getAttribute("user");
        int rankValue = (int) session.getAttribute("rankValue");
        //需要判断查询用户身份，教师只能查到自己所授课程，学生只能查到自己所学课程

        if( rankValue < 8) {
            return JSONObject.toJSONString(sysCourseService.getCourse('%'+ user.getUserClass()+'%', pageable));
        } else if ( rankValue==8 || rankValue==9 ) {
            return JSONObject.toJSONString(sysCourseService.getTeacherCourse('%'+user.getUserRealName()+'%', pageable));
        } else {
            return JSONObject.toJSONString(sysCourseService.getCourse(pageable));
        }
    }

    @MyLog(value = "删除课程")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/delete")
    public int getLogList(@RequestParam(value = "courseId") String courseId) {
        SysCourse sysCourse = sysCourseService.getByCourseId(courseId);
        sysCourse.setCourseStatus(0);
        sysCourseService.saveCourse(sysCourse);
        //删除该课程下所有的题目集
        courseCollectionService.deleteCollectionListByCourseId(courseId);
        //删除教师课程表
        teacherCourseService.deleteByCourseId(courseId);
        return 200;
    }

    //获得所有班级名称
    @RequestMapping(value = "/getClassName")
    public String getClassNameList() {
        return JSONObject.toJSONString(sysClassService.getClassNameList());
    }

    //添加课程
    @RequestMapping(value = "/add")
    public int addCourse(@RequestBody SysCourse sysCourse) {
        //判断该课程名称是否已经存在
        if(sysCourseService.getCourseIdByName(sysCourse.getCourseName()) != null) {
            return 400;
        }else {
            String[] classNumber = sysCourse.getCourseClass().split(" ");
            sysCourse.setCourseUserRealName("");
            //设置授课教师姓名
            for(int i=0; i<classNumber.length; i++) {
                String userId =  classTeacherService.getUserIdByClassId(sysClassService.getClassIdByNumber(classNumber[i]));
                sysCourse.setCourseUserRealName(sysCourse.getCourseUserRealName() + sysUserService.getUserById(userId).getUserRealName() + " ");
            }
            //去掉最后一个多余空格
            sysCourse.setCourseUserRealName(sysCourse.getCourseUserRealName().substring(0, sysCourse.getCourseUserRealName().length()-1));
            //根据班级名称查询班级id
            sysCourse.setCourseStatus(1);
            sysCourse.setCourseType(1);
            sysCourse.setCourseLanguage("C");
            sysCourseService.saveCourse(sysCourse);

            //设置教师课程表
            TeacherCourse teacherCourse;
            String courseId = sysCourseService.getCourseIdByName(sysCourse.getCourseName());
            for(int i=0; i<classNumber.length; i++) {
                String userId =  classTeacherService.getUserIdByClassId(sysClassService.getClassIdByNumber(classNumber[i]));
                teacherCourse = new TeacherCourse();
                teacherCourse.setUserId(userId);
                teacherCourse.setCourseId(courseId);
                teacherCourseService.saveTeacherCourse(teacherCourse);
            }
            return 200;
        }

    }

    //添加题目集
    @MyLog(value = "课程添加题目集")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/addCollection")
    public int addCollectionList(@RequestParam("courseId") String courseId, @RequestParam("collectionNameList") String[] collectionNameList) {
        CourseCollection courseCollection;
        //先删除该课程下的题目集
        courseCollectionService.deleteCollectionListByCourseId(courseId);
        //先查询id再添加
        for(int i=0; i<collectionNameList.length; i++) {
            String collectionId = sysCollectionService.getByCollectionName(collectionNameList[i]).getCollectionId();
            courseCollection = new CourseCollection();
            courseCollection.setCollectionId(collectionId);
            courseCollection.setCourseId(courseId);
            courseCollectionService.saveCourseCollection(courseCollection);
        }
        return 200;
    }

    //查询课程已有题目集名称
    @RequestMapping(value = "/getCollectionName")
    public String getCollectionExerciseNameList(@RequestParam("courseId") String courseId) {
        //根据课程id查询所有题目集id
        List<String> collectionIdList = courseCollectionService.getCollectionIdListByCourseId(courseId);
        List<String> collectionNameList = new ArrayList<>();
        for(int i=0; i<collectionIdList.size(); i++) {
            collectionNameList.add(sysCollectionService.getNameById(collectionIdList.get(i)));
        }
        return JSONObject.toJSONString(collectionNameList);
    }

}
