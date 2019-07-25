package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.service.ExerciseLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/exercise_label")
public class ExerciseLabelController {
    @Autowired
    private ExerciseLabelService exerciseLabelService;
    @RequestMapping(value = "/get")
    //查询所有题目标签
    public String getExerciseLabelList() {
        return JSONObject.toJSONString(exerciseLabelService.getAll());
    }
}
