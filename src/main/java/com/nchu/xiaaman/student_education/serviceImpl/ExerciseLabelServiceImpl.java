package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.ExerciseLabelDao;
import com.nchu.xiaaman.student_education.domain.ExerciseLabel;
import com.nchu.xiaaman.student_education.service.ExerciseLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExerciseLabelServiceImpl implements ExerciseLabelService {
    @Resource
    private ExerciseLabelDao exerciseLabelDao;
    @Override
    public List<ExerciseLabel> getAll() {
        return exerciseLabelDao.findAll();
    }
}
