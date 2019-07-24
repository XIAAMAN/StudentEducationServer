package com.nchu.xiaaman.student_education.service;

import com.nchu.xiaaman.student_education.domain.SysLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SysLogService {
    void save(SysLog sysLog);
    Page<SysLog> getAll(Pageable page);
}
