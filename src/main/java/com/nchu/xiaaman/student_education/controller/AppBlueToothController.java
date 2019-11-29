package com.nchu.xiaaman.student_education.controller;

import com.nchu.xiaaman.student_education.domain.BlueTooth;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.BlueToothService;
import com.nchu.xiaaman.student_education.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping(value = "/blue")
public class AppBlueToothController {

    @Autowired
    private BlueToothService blueToothService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/save")
    public void saveBlueInfo(@RequestBody List<BlueTooth> blueToothList, HttpServletRequest req, HttpServletResponse rep) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        rep.setContentType("text/html;charset=utf-8");
        SysUser sysUser;
        for(int i=0; i<blueToothList.size(); i++) {
            //根据扫描到的mac地址，判断是否有该用户
            sysUser = sysUserService.getSysUserByMacAddress(blueToothList.get(i).getFriendMacAddress());
            if(sysUser != null) {
                blueToothService.saveBlueToothList(blueToothList.get(i));
            }
        }


    }

}
