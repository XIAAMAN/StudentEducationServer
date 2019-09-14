package com.nchu.xiaaman.student_education.serviceImpl;
import com.nchu.xiaaman.student_education.dao.SysExerciseDao;
import com.nchu.xiaaman.student_education.domain.SysExercise;
import com.nchu.xiaaman.student_education.service.SysExerciseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysExerciseServiceImpl implements SysExerciseService {
    @Resource
    private SysExerciseDao sysExerciseDao;
    @Override
    public Page<SysExercise> getAll(Pageable pageable) {
        return sysExerciseDao.getAll(pageable);
    }

    //删除题目，更改题目的可见性
    @Override
    public void deleteExerciseById(String exerciseId) {
        sysExerciseDao.deleteExerciseById(exerciseId);
    }

    //增加或修改题目
    @Override
    public void saveExercise(SysExercise sysExercise) {
        sysExerciseDao.save(sysExercise);
    }

    @Override
    public SysExercise getSysExerciseByName(String exerciseName) {
        return sysExerciseDao.getSysExerciseByName(exerciseName);
    }

    @Override
    public Page<SysExercise> getSysExerciseByNameAndLabel(String exerciseName, String exerciseLabel, Pageable pageable) {
        return sysExerciseDao.getSysExerciseByNameAndLabel(exerciseName, exerciseLabel, pageable);
    }

    @Override
    public Page<SysExercise> getCheckExercise(Pageable pageable) {
        return sysExerciseDao.getCheckExercise(pageable);
    }

    @Override
    public void modifyByIdAndCheckName(String checkName, String exerciseId) {
        sysExerciseDao.modifyByIdAndCheckName(checkName, exerciseId);
    }

    @Override
    public void modifyState(String checkName, String exerciseId) {
        sysExerciseDao.modifyState(checkName, exerciseId);
    }

    @Override
    public SysExercise getById(String exerciseId) {
        return sysExerciseDao.getOne(exerciseId);
    }

    @Override
    public Page<SysExercise> getExerciseFree(Pageable pageable) {
        return sysExerciseDao.getExerciseFree(pageable);
    }

    @Override
    public List<String> getExerciseNameList() {
        return sysExerciseDao.getExerciseNameList();
    }

    @Override
    public String getExerciseIdByName(String exerciseName) {
        return sysExerciseDao.getExerciseIdByName(exerciseName);
    }

    @Override
    public List<String> getExerciseNameList(int exerciseType) {
        return sysExerciseDao.getExerciseNameList(exerciseType);
    }
}
