package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.SysUserDao;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.SysUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<String> getUserNames() {
        return userDao.getUserNames();
    }

    @Override
    public List<String> getUserNumbers() {
        return userDao.getUserNumbers();
    }

    @Override
    public Page<SysUser> getUserListByUserNameAndUserNumber(String userName, String userNumber, int roleRank, Pageable pageable) {
        return userDao.getUserListByUserNameAndUserNumber(userName, userNumber, roleRank, pageable);
    }

    @Override
    public SysUser getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public SysUser getUserByUserNumber(String userNumber) {
        return userDao.getUserByUserNumber(userNumber);
    }

    @Override
    public Page<SysUser> getUserListByRank(String userRecommendName, int roleRank, Pageable pageable) {
        return userDao.getUserListByRank(userRecommendName, roleRank, pageable);
    }

    @Override
    public Page<SysUser> getUserListByUserNameAndUserNumber(String userName, String userNumber, String userRecommendName, int roleRank, Pageable pageable) {
        return userDao.getUserListByUserNameAndUserNumber(userName, userNumber, userRecommendName, roleRank, pageable);
    }

    @Override
    public List<SysUser> getUserListByClass(String userClass) {
        return userDao.getUserListByClass(userClass);
    }

    @Override
    public SysUser getSysUserByMacAddress(String macAddress) {
        return userDao.getSysUserByMacAddress(macAddress);
    }


}
