package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysExercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysExerciseService {
    Page<SysExercise> getAll(Pageable pageable);
    void deleteExerciseById(String exerciseId);
    void saveExercise(SysExercise sysExercise);
    SysExercise getSysExerciseByName(String exerciseName);
    Page<SysExercise> getSysExerciseByNameAndLabel(String exerciseName, String exerciseLabel, Pageable pageable);
    Page<SysExercise> getCheckExercise(Pageable pageable);
    void modifyByIdAndCheckName(String checkName, String exerciseId);
    void modifyState(String checkName, String exerciseId);
    SysExercise getById(String exerciseId);
    Page<SysExercise> getExerciseFree(Pageable pageable);
    List<String> getExerciseNameList();
    String getExerciseIdByName(String exerciseName);
    List<String> getExerciseNameList(int exerciseType);
    String getExerciseIdByDescription(String exerciseDescription);
}
