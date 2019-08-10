package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.TeacherCourseDao;
import com.nchu.xiaaman.student_education.domain.TeacherCourse;
import com.nchu.xiaaman.student_education.service.TeacherCourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {
    @Resource
    private TeacherCourseDao teacherCourseDao;
    @Override
    public List<String> getCourseIdListByUserId(String userId) {
        return teacherCourseDao.getCourseIdListByUserId(userId);
    }

    @Override
    public void saveTeacherCourse(TeacherCourse teacherCourse) {
        teacherCourseDao.save(teacherCourse);
    }

    @Override
    public void deleteByCourseId(String courseId) {
        teacherCourseDao.deleteByCourseId(courseId);
    }
}
