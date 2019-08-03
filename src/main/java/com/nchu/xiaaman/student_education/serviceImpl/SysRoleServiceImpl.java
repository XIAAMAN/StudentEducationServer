package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.SysRoleDao;
import com.nchu.xiaaman.student_education.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public String getRoleIdByRoleName(String roleName) {
        return sysRoleDao.getRoleIdByRoleName(roleName);
    }

    @Override
    public String getSysRoleIdByName(String roleName) {
        return sysRoleDao.getSysRoleIdByName(roleName);
    }
}
