package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "course_collection")
public class CourseCollection {
    @Id
    @Column(name = "course_collection_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String courseCollectionId;

    @Column(name = "collection_id")
    private String collectionId;

    @Column(name = "course_id")
    private String courseId;

    public String getCourseCollectionId() {
        return courseCollectionId;
    }

    public void setCourseCollectionId(String courseCollectionId) {
        this.courseCollectionId = courseCollectionId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
