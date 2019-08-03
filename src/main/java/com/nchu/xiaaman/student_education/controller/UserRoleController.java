package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;
    @RequestMapping(value = "/get")
    public String  getRoles(){
        //先查询用户的角色等级值
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        //获得用户最大角色等级值
        int maxRoleRank = userRoleService.getMaxRoleRank(user.getUserId());
        List<String > roleListString = userRoleService.getRoles(maxRoleRank);
        return JSONObject.toJSONString(roleListString);
    }

}
