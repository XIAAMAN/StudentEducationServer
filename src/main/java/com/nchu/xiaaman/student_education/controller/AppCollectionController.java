package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.domain.*;
import com.nchu.xiaaman.student_education.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class AppCollectionController {
    @Autowired
    private SysUserService userService;

    @Autowired
    private UserRoleService userRoleService;

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
    private SysUserService sysUserService;

    @Autowired
    private ExerciseScoreService exerciseScoreService;

    //根据用户名查询题目集，管理员返回所有题目集
    @RequestMapping(value = "/appGetCollection")
    public void getCollection(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        String message= "";
        req.setCharacterEncoding("UTF-8");
        rep.setContentType("text/html;charset=utf-8");
        String userName = req.getParameter("userName");
        SysUser user = userService.getUserByUserName(userName);
        int rankValue = userRoleService.getMaxRoleRank(user.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //根据课程id查询已经结束的题目集id
        List<String> collectionId;
        List<SysCollection> collectionList = new ArrayList<>();
        SysCollection sysCollection;
        //学生用户
        if (rankValue == 6) {
            List<SysCourse> courseList = sysCourseService.getCourseId('%' + user.getUserClass() + '%');
            for (int i = 0; i < courseList.size(); i++) {
                collectionId = courseCollectionService.getCollectionIdListByCourseId(courseList.get(i).getCourseId());
                for (int j = 0; j < collectionId.size(); j++) {
                    //添加题目集
                    sysCollection = sysCollectionService.getById(collectionId.get(j));
                    collectionList.add(sysCollection);
                }
            }
        } else {        //教师或管理员返回所有题目集
            List<String> collectionNameList = sysCollectionService.getCollectionName();
            for(int i=0; i<collectionNameList.size(); i++) {
                collectionList.add(sysCollectionService.getByCollectionName(collectionNameList.get(i)));
            }
        }

        //对题目集按照时间进行降序

        Collections.sort(collectionList, new Comparator<SysCollection>() {
            @Override
            public int compare(SysCollection o1, SysCollection o2) {
                if(o1.getCollectionStartTime().compareTo(o2.getCollectionStartTime())>0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        message = JSONObject.toJSONString(collectionList);
        rep.getWriter().append(message).flush();
        rep.getWriter().close();
    }

    //根据题目集id查询改题目集下所有的题目
    @RequestMapping(value = "/appGetExercise")
    public void getExerciseListByCollectionId(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        req.setCharacterEncoding("UTF-8");
        rep.setContentType("text/html;charset=utf-8");
        String collectionId = req.getParameter("collectionId");
        String userName = req.getParameter("userName");
        SysUser user = sysUserService.getUserByUserName(userName);
        ExerciseScore exerciseScore;
        List<String> exerciseIdList = collectionExerciseService.getExerciseIdListByCollectionId(collectionId);
        List<SysExercise> exerciseList = new ArrayList<>();
        List<SysExercise> exerciseListCopy = new ArrayList<>();
        for(int i=0; i<exerciseIdList.size(); i++) {
            exerciseList.add(sysExerciseService.getById(exerciseIdList.get(i)));
        }

        for(int i=0; i<exerciseList.size(); i++) {
            SysExercise exercise = new SysExercise();
            exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseList.get(i).getExerciseId(), user.getUserId(), collectionId);
            BeanUtils.copyProperties(exerciseList.get(i), exercise);
            //将填空题的空数放在下面字段里
            if(exercise.getExerciseType() == 4) {
                exercise.setExerciseClassifyCause(exercise.getExerciseCode().split(";xiaaman;").length);
            }
            if(exerciseScore != null) {
                exercise.setExerciseCode(exerciseScore.getExerciseCode());
            } else {
                exercise.setExerciseCode("");
            }


            exerciseListCopy.add(exercise);
        }

        rep.getWriter().append(exerciseListCopy.toString()).flush();
        rep.getWriter().close();
    }
}

