package com.nchu.xiaaman.student_education.dao;

import com.nchu.xiaaman.student_education.domain.SysLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SysLogDao extends JpaRepository<SysLog, String> {
    @Query(value = "select * from sys_log order by log_create_time desc", nativeQuery = true)
    Page<SysLog> getAll(Pageable page);
}
