package com.nchu.xiaaman.student_education.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.domain.SysExercise;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.List;

public class ExerciseUnion {
    private List<SysExercise> content;
    private long totalElements;

    public String union(List<SysExercise> content, long totalElements) {
        ExerciseUnion exerciseUnion = new ExerciseUnion();
        // 必需这样赋值，然后修改数据，数据库的数据就不会改变
        String list = JSONObject.toJSONString(content);
        exerciseUnion.content = JSON.parseArray(list, SysExercise.class);
        exerciseUnion.totalElements = totalElements;
        for (int i=0; i<exerciseUnion.content.size(); i++) {
            exerciseUnion.content.get(i).setExerciseCode("");
        }
        return exerciseUnion.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
