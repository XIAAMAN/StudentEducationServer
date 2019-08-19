package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.RolePermis;
import com.nchu.xiaaman.student_education.domain.SysPermis;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.domain.UserRole;
import com.nchu.xiaaman.student_education.service.RolePermisService;
import com.nchu.xiaaman.student_education.service.SysPermisService;
import com.nchu.xiaaman.student_education.service.SysUserService;
import com.nchu.xiaaman.student_education.service.UserRoleService;
import com.nchu.xiaaman.student_education.utils.Md5Utils;
import com.nchu.xiaaman.student_education.utils.PermisMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private SysUserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermisService rolePermisService;

    @Autowired
    private SysPermisService sysPermisService;

    public Md5Utils md5Utils;
    @MyLog(value = "用户登录")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody SysUser user,HttpSession session) {
        List<UserRole> userRoleList;        //接收用户角色表的数据
        List<RolePermis> rolePermisList = new ArrayList<>();        // 接收角色权限表的数据
        List<SysPermis> permisList = new ArrayList<>();         // 用户权限
        List<String> permisValueList = new ArrayList<>();
        PermisMenu permisMenu;      //最终封装后的数据
        String permisValue;
        SysPermis tempPermis;
        // 对密码进行加密与数据库进行比对
        user.setUserPassword(md5Utils.md5(user.getUserPassword()));

        SysUser newUser = userService.getByNameAndPassword(user.getUserName(), user.getUserPassword());
        if(newUser != null) {
            //获取UserRole对象数组（一个用户可能拥有多个角色）
            SysUser testUser = (SysUser) session.getAttribute("user");
            if(testUser != null) {
                permisValue = JSONObject.toJSONString(permisValueList);
                permisMenu = new PermisMenu().decorateData(permisList, 600, permisValue,0);
                return permisMenu.toString();
            }
            session.setAttribute("user", newUser);
            int rankValue = userRoleService.getMaxRoleRank(newUser.getUserId());
            session.setAttribute("rankValue", rankValue);
            session.setMaxInactiveInterval(48*3600);
            userRoleList = userRoleService.getUserRoleListByUserId(newUser.getUserId());

            for(int i=0; i<userRoleList.size(); i++) {
                //根据角色id查询该角色所有权限
                List<RolePermis> tempRolePermisList = rolePermisService.getRolePermisByRoleId(userRoleList.get(i).getRoleId());

                // 判断rolePermis是否已经添加到list中，防止冗余
                // 因为用户有多个角色，可能有相同的权限，所以可能会导致相同的权限重复
                // 必须要重写RolePermis中的equals()方法，不然返回的一定是false，因为默认比较的对象地址
                for (int j=0; j<tempRolePermisList.size(); j++) {
                    if (!rolePermisList.contains(tempRolePermisList.get(j))) {
                        rolePermisList.add(tempRolePermisList.get(j));
                    }
                }
            }
            // 根据权限id，查询权限对象
            for (int i=0; i<rolePermisList.size(); i++) {
                //不能直接添加，可能为空
                tempPermis = sysPermisService.getSysPermisByPermisId(rolePermisList.get(i).getPermisId());
                if(tempPermis != null) {
                    permisList.add(tempPermis);
                }

                //把用户拥有的所有权限值存入list
                permisValueList.add(sysPermisService.getPermisValueByPermisId(rolePermisList.get(i).getPermisId()));
            }

            permisValue = JSONObject.toJSONString(permisValueList);
            permisMenu = new PermisMenu().decorateData(permisList, 200, permisValue, rankValue);

            return permisMenu.toString();
        } else {
            permisValue = JSONObject.toJSONString(permisValueList);
            permisMenu = new PermisMenu().decorateData(permisList, 400, permisValue,0);
            return permisMenu.toString();
        }
    }
}
