package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.SysUserService;
import com.nchu.xiaaman.student_education.service.UserRoleService;
import com.nchu.xiaaman.student_education.utils.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/sys_user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserRoleService userRoleService;

    public Md5Utils md5Utils;
    @MyLog(value = "查询用户")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/get")
    public String getUsers(@RequestParam(value = "page",defaultValue = "1") int page,
                         @RequestParam(value = "size",defaultValue = "10") int size){
        //先查询用户的角色等级值
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        //获得用户最大角色等级值
        int maxRoleRank = userRoleService.getMaxRoleRank(user.getUserId());
        Pageable pageable= PageRequest.of(page-1, size);

        return JSONObject.toJSONString(sysUserService.getUserListByRank(maxRoleRank, pageable));
    }

    @MyLog(value = "重置用户密码")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/resetPassword")
    public int getUsers(@RequestParam("userId") String userId){
        SysUser user = sysUserService.getUserById(userId);
        //将用户密码重置为123456
        user.setUserPassword(md5Utils.md5("123456"));
        sysUserService.saveUser(user);
        return 200;
    }

    @MyLog(value = "增加用户")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/add")
    public int addUser(@RequestBody SysUser user){
        //新增用户的初始密码都是123456
        user.setUserPassword(md5Utils.md5("123456"));
        sysUserService.saveUser(user);
        return 200;
    }

    @MyLog(value = "删除用户")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/delete")
    public int deleteUser(@RequestParam("userId") String userId){
        //新增用户的初始密码都是123456
        SysUser user = sysUserService.getUserById(userId);
        //将用户设置为不可见，表示删除该用户
        user.setUserStatus(0);
        sysUserService.saveUser(user);
        return 200;
    }

    @MyLog(value = "退出登录")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/logOut")
    public int logOut(){
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        HttpSession session = request.getSession();
        session.invalidate();
        return 200;
    }



}
