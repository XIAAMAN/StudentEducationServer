package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.CourseCollection;

import java.util.List;

public interface CourseCollectionService {
    List<String> getCollectionIdListByCourseId(String courseId);
    CourseCollection getCourseCollectionById(String id);
    void saveCourseCollection(CourseCollection courseCollection);
    void deleteCollectionListByCourseId(String courseId);
    List<CourseCollection> getByCollectionId(String collection_id);
}
