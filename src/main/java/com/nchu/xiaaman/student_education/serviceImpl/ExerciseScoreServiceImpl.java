package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.ExerciseScoreDao;
import com.nchu.xiaaman.student_education.domain.ExerciseScore;
import com.nchu.xiaaman.student_education.service.ExerciseScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExerciseScoreServiceImpl implements ExerciseScoreService {
    @Resource
    private ExerciseScoreDao exerciseScoreDao;
    @Override
    public ExerciseScore getByUserIdExerciseIdAndCollectionId(String exerciseId, String userId, String collectionId) {
        return exerciseScoreDao.getByUserIdExerciseIdAndCollectionId(exerciseId, userId, collectionId);
    }

    @Override
    public void saveExerciseScore(ExerciseScore exerciseScore) {
        exerciseScoreDao.save(exerciseScore);
    }

    @Override
    public ExerciseScore getById(String id) {
        return exerciseScoreDao.getOne(id);
    }
}
