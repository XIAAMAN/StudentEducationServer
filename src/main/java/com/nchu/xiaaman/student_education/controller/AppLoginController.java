package com.nchu.xiaaman.student_education.controller;

import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.SysUserService;
import com.nchu.xiaaman.student_education.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AppLoginController {
    @Autowired
    private SysUserService userService;
    public Md5Utils md5Utils;
    @RequestMapping(value = "/appLogin")
    public void login(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("userPassword");
        String message = "";
        password = md5Utils.md5(password);
        SysUser user = userService.getByNameAndPassword(userName, password);
        if(user!=null) {
            message = "200";
        }
        rep.getWriter().append(message).flush();
        rep.getWriter().close();
    }

}
