package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.CourseCollectionDao;
import com.nchu.xiaaman.student_education.domain.CourseCollection;
import com.nchu.xiaaman.student_education.service.CourseCollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseCollectionServiceImpl implements CourseCollectionService {
    @Resource
    private CourseCollectionDao courseCollectionDao;
    @Override
    public List<String> getCollectionIdListByCourseId(String courseId) {
        return courseCollectionDao.getCollectionIdListByCourseId(courseId);
    }

    @Override
    public CourseCollection getCourseCollectionById(String id) {
        return courseCollectionDao.getOne(id);
    }

    @Override
    public void saveCourseCollection(CourseCollection courseCollection) {
        courseCollectionDao.save(courseCollection);
    }

    @Override
    public void deleteCollectionListByCourseId(String courseId) {
        courseCollectionDao.deleteCollectionListByCourseId(courseId);
    }

    @Override
    public List<CourseCollection> getByCollectionId(String collection_id) {
        return courseCollectionDao.getByCollectionId(collection_id);
    }

    @Override
    public CourseCollection getByCollectionIdAndCourseId(String collection_id, String course_id) {
        return courseCollectionDao.getByCollectionIdAndCourseId(collection_id, course_id);
    }
}
