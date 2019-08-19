package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.ExercisePracticeDao;
import com.nchu.xiaaman.student_education.domain.ExercisePractice;
import com.nchu.xiaaman.student_education.service.ExercisePracticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExercisePracticeServiceImpl implements ExercisePracticeService {
    @Resource
    private ExercisePracticeDao exercisePracticeDao;
    @Override
    public ExercisePractice getByUserIdAndExerciseId(String exerciseId, String userId) {
        return exercisePracticeDao.getByUserIdAndExerciseId(exerciseId, userId);
    }

    @Override
    public void saveExercisePractice(ExercisePractice exercisePractice) {
        exercisePracticeDao.save(exercisePractice);
    }

    @Override
    public ExercisePractice getById(String id) {
        return exercisePracticeDao.getOne(id);
    }
}
