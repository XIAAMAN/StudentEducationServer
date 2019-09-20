package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRelationshipDao extends JpaRepository<UserRelationship, String> {
    //根据id查询所有的记录
    @Query(value = "select * from user_relationship where user_id = ?1", nativeQuery = true)
    List<UserRelationship> getAllByUserId(String user_id);

    //根据id和最近时间查询所有的记录
    @Query(value = "select * from user_relationship where user_id = ?1 and user_relationship_time = ?2", nativeQuery = true)
    List<UserRelationship> getAllByUserIdAndTime(String user_id, String time);
}
