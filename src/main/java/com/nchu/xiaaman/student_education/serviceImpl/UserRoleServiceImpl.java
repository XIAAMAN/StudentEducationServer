package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.UserRoleDao;
import com.nchu.xiaaman.student_education.domain.UserRole;
import com.nchu.xiaaman.student_education.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserRoleDao userRoleDao;

    // 根据用户id查询角色List
    @Override
    public List<UserRole> getUserRoleListByUserId(String userId) {
        return userRoleDao.getUserRoleListByUserId(userId);
    }

    @Override
    public int getMaxRoleRank(String userId) {
        return userRoleDao.getMaxRoleRank(userId);
    }
}
