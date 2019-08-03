package com.nchu.xiaaman.student_education.serviceImpl;
import com.nchu.xiaaman.student_education.dao.SysExerciseDao;
import com.nchu.xiaaman.student_education.domain.SysExercise;
import com.nchu.xiaaman.student_education.service.SysExerciseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public void saveOrModifyExercise(SysExercise sysExercise) {
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
}
