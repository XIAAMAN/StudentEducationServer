package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.UserRelationshipDao;
import com.nchu.xiaaman.student_education.domain.UserRelationship;
import com.nchu.xiaaman.student_education.service.UserRelationshipService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserRelationshipServiceImpl implements UserRelationshipService {
    @Resource
    private UserRelationshipDao userRelationshipDao;
    @Override
    public List<UserRelationship> getAllByUserId(String user_id) {
        return userRelationshipDao.getAllByUserId(user_id);
    }

    @Override
    public void save(UserRelationship userRelationship) {
        userRelationshipDao.save(userRelationship);
    }

    @Override
    public List<UserRelationship> getAllByUserIdAndTime(String user_id, String time) {
        return userRelationshipDao.getAllByUserIdAndTime(user_id, time);
    }
}
