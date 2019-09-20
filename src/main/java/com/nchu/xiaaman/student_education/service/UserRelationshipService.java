package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.UserRelationship;

import java.util.List;

public interface UserRelationshipService {
    List<UserRelationship> getAllByUserId(String user_id);
    void save(UserRelationship userRelationship);
    List<UserRelationship> getAllByUserIdAndTime(String user_id, String time);
}
