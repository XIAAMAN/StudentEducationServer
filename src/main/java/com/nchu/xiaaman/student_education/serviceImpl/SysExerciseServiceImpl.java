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
}
