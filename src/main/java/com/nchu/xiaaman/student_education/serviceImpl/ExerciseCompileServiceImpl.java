package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.ExerciseCompileDao;
import com.nchu.xiaaman.student_education.domain.ExerciseCompile;
import com.nchu.xiaaman.student_education.service.ExerciseCompileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExerciseCompileServiceImpl implements ExerciseCompileService {
    @Resource
    private ExerciseCompileDao exerciseCompileDao;
    @Override
    public void saveExerciseCompile(ExerciseCompile exerciseCompile) {
        exerciseCompileDao.save(exerciseCompile);
    }
}
