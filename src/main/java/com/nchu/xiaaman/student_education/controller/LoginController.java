package com.nchu.xiaaman.student_education.controller;

import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.SysUserService;
import com.nchu.xiaaman.student_education.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private SysUserService userService;
    private Md5Utils md5Utils;
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public int login(@RequestBody SysUser user) {
//        System.out.println("userName : "+user.getUserName());
//        System.out.println("Login Successful");
//        if("xiaaman".equals(user.getUserName()) && "123456".equals(user.getUserPassword())) {
//            return 200;
//        } else {
//            return 400;
//        }
//
//    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public int login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
//        System.out.println("userName : "+userName);
//        System.out.println("Login Successful");
//        if("xiaaman".equals(userName) && "123456".equals(password)) {
//            return 200;
//        } else {
//            return 400;
//        }
        //对密码进行md5加密，然后与数据库进行比对
        password = md5Utils.md5(password);
        SysUser user = userService.getByNameAndPassword(userName, password);


        if(user != null) {
            System.out.println(user.getUserName());
            System.out.println(user.getUserPassword());
            return 200;
        } else {
            return 400;
        }
    }
}
