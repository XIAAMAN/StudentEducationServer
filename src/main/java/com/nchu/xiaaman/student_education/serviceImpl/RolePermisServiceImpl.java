package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.RolePermisDao;
import com.nchu.xiaaman.student_education.domain.RolePermis;
import com.nchu.xiaaman.student_education.service.RolePermisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RolePermisServiceImpl implements RolePermisService {
    @Resource
    private RolePermisDao rolePermisDao;
    @Override
    public List<RolePermis> getRolePermisByRoleId(String roleId) {
        return rolePermisDao.getRolePermisByRoleId(roleId);
    }
}
