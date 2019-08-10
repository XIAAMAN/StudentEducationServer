package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.SysCourseDao;
import com.nchu.xiaaman.student_education.domain.SysCourse;
import com.nchu.xiaaman.student_education.service.SysCourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysCourseServiceImpl implements SysCourseService {
    @Resource
    private SysCourseDao sysCourseDao;
    @Override
    public Page<SysCourse> getCourse(Pageable pageable) {
        return sysCourseDao.getCourse(pageable);
    }

    @Override
    public void saveCourse(SysCourse sysCourse) {
        sysCourseDao.save(sysCourse);
    }

    @Override
    public SysCourse getByCourseId(String courseId) {
        return sysCourseDao.getOne(courseId);
    }

    @Override
    public Page<SysCourse> getCourse(String ClassNumber, Pageable pageable) {
        return sysCourseDao.getCourse(ClassNumber, pageable);
    }

    @Override
    public Page<SysCourse> getTeacherCourse(String courseUserRealName, Pageable pageable) {
        return sysCourseDao.getTeacherCourse(courseUserRealName, pageable);
    }

    @Override
    public String getCourseIdByName(String courseName) {
        return sysCourseDao.getCourseIdByName(courseName);
    }
}
