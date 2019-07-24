package com.nchu.xiaaman.student_education.serviceImpl;

import com.nchu.xiaaman.student_education.dao.SysLogDao;
import com.nchu.xiaaman.student_education.domain.SysLog;
import com.nchu.xiaaman.student_education.service.SysLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Resource
    private SysLogDao sysLogDao;

    //存储日志
    public void save(SysLog sysLog) {
        sysLogDao.save(sysLog);
    }

    @Override
    public Page<SysLog> getAll(Pageable page) {
        return sysLogDao.getAll(page);
    }


}
