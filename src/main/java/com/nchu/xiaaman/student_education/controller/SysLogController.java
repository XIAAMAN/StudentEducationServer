package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/log")
public class SysLogController {
    @Autowired
    private SysLogService logService;
    @MyLog(value = "查询日志信息")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/get")
    public String getLogList(@RequestParam(value = "page",defaultValue = "1") int page,
                             @RequestParam(value = "size",defaultValue = "10") int size) {
        Pageable pageable=PageRequest.of(page-1, size);
        return JSONObject.toJSONString(logService.getAll(pageable));

    }
}
