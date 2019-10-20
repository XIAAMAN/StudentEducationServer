package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.ExerciseScoreDao;
import com.nchu.xiaaman.student_education.domain.ExerciseScore;
import com.nchu.xiaaman.student_education.service.ExerciseScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<ExerciseScore> getByExerciseId(String exerciseId) {
        return exerciseScoreDao.getByExerciseId(exerciseId);
    }

    @Override
    public List<ExerciseScore> getByUserId(String userId) {
        return exerciseScoreDao.getByUserId(userId);
    }

    @Override
    public List<ExerciseScore> getAll() {
        return exerciseScoreDao.findAll();
    }
}
