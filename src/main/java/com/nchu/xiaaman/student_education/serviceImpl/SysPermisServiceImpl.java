package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.SysPermisDao;
import com.nchu.xiaaman.student_education.domain.SysPermis;
import com.nchu.xiaaman.student_education.service.SysPermisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysPermisServiceImpl implements SysPermisService {
    @Resource
    private SysPermisDao sysPermisDao;

    // 查询用户权限，只查询根目录和子菜单两级的权限
    @Override
    public SysPermis getSysPermisByPermisId(String permisId) {
        return sysPermisDao.getSysPermisByPermisId(permisId);
    }

    @Override
    public SysPermis getById(String permisId) {
        return sysPermisDao.getOne(permisId);
    }

    @Override
    public String getPermisValueByPermisId(String permisId) {
        return sysPermisDao.getPermisValueByPermisId(permisId);
    }

    @Override
    public SysPermis getAllRankSysPermisByPermisId(String permisId) {
        return sysPermisDao.getAllRankSysPermisByPermisId(permisId);
    }

    @Override
    public List<SysPermis> getPermisListByParentId(String parentId) {
        return sysPermisDao.getPermisListByParentId(parentId);
    }

    @Override
    public String getIdByNameValue(String nameValue) {
        return sysPermisDao.getIdByNameValue(nameValue);
    }
}
