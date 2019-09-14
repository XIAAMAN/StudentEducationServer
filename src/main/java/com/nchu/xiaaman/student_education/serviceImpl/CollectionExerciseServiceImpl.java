package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.CollectionExerciseDao;
import com.nchu.xiaaman.student_education.domain.CollectionExercise;
import com.nchu.xiaaman.student_education.service.CollectionExerciseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollectionExerciseServiceImpl implements CollectionExerciseService {
    @Resource
    private CollectionExerciseDao collectionExerciseDao;
    @Override
    public void deleteByCollectionId(String collectionId) {
        collectionExerciseDao.deleteByCollectionId(collectionId);
    }

    @Override
    public void saveCollectionExercise(CollectionExercise collectionExercise) {
        collectionExerciseDao.save(collectionExercise);
    }

    @Override
    public CollectionExercise getByCollectionIdAndExerciseId(String collectionId, String exerciseId) {
        return collectionExerciseDao.getByCollectionIdAndExerciseId(collectionId, exerciseId);
    }

    @Override
    public List<String> getExerciseIdListByCollectionId(String collectionId) {
        return collectionExerciseDao.getExerciseIdListByCollectionId(collectionId);
    }

    @Override
    public void deleteByCollectionIdAndExerciseId(String collectionId, String exerciseId) {
        collectionExerciseDao.deleteByCollectionIdAndExerciseId(collectionId, exerciseId);
    }
}
