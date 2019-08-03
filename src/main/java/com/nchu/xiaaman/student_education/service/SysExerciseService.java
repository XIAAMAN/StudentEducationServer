package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysExercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysExerciseService {
    Page<SysExercise> getAll(Pageable pageable);
    void deleteExerciseById(String exerciseId);

    //增加或修改题目
    void saveOrModifyExercise(SysExercise sysExercise);
    SysExercise getSysExerciseByName(String exerciseName);
    Page<SysExercise> getSysExerciseByNameAndLabel(String exerciseName, String exerciseLabel, Pageable pageable);

}
