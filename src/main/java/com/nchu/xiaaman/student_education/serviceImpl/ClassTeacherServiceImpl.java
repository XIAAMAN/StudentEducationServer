package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.ClassTeacherDao;
import com.nchu.xiaaman.student_education.domain.ClassTeacher;
import com.nchu.xiaaman.student_education.service.ClassTeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClassTeacherServiceImpl implements ClassTeacherService {
    @Resource
    private ClassTeacherDao classTeacherDao;
    @Override
    public void saveClassTeacher(ClassTeacher classTeacher) {
        classTeacherDao.save(classTeacher);
    }

    @Override
    public String getUserIdByClassId(String classId) {
        return classTeacherDao.getUserIdByClassId(classId);
    }

    @Override
    public String[] getClassIdListByTeacherId(String userId) {
        return classTeacherDao.getClassIdListByTeacherId(userId);
    }
}
