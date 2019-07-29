package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.SysUserDao;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.SysUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao userDao;

    // 通过用户名和密码验证用户身份
    @Override
    public SysUser getByNameAndPassword(String userName, String password) {
        return userDao.getByNameAndPassword(userName, password);
    }

    // 增加单个用户
    @Override
    public void saveUser(SysUser user) {
        userDao.save(user);
    }

    @Override
    public Page<SysUser> getUserListByRank(int roleRank, Pageable pageable) {
        return userDao.getUserListByRank(roleRank, pageable);
    }

    @Override
    public String getUserIdByUserName(String userName) {
        return userDao.getUserIdByUserName(userName);
    }

    @Override
    public SysUser getUserById(String userId) {
        return userDao.getOne(userId);
    }


}
