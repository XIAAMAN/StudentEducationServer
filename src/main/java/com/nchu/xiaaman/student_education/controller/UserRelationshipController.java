package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.domain.SysClass;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.domain.UserRelationship;
import com.nchu.xiaaman.student_education.service.UserRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/user_relationship")
public class UserRelationshipController {
    @Autowired
    private UserRelationshipService userRelationshipService;
    @RequestMapping(value = "/judgeDate")
    public int judgeDate(HttpSession session) throws ParseException {
        SysUser user = (SysUser)session.getAttribute("user");
        List<UserRelationship> userRelationshipList = userRelationshipService.getAllByUserId(user.getUserId());
        String recentTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nowTime = new Date().getTime();
        if(userRelationshipList==null || userRelationshipList.size()==0) {
            return 200;
        }else {
            //寻找最近一次提交时间
            recentTime = userRelationshipList.get(0).getUserRelationshipTime();
            for(int i=0; i<userRelationshipList.size(); i++) {
                if(recentTime.compareTo(userRelationshipList.get(i).getUserRelationshipTime())<0) {
                    recentTime = userRelationshipList.get(i).getUserRelationshipTime();
                }
            }
            //如果上次提交与现在相差超过十天则允许提交返回200
            Date date = sdf.parse(recentTime);
            long recnet = date.getTime();
            if((nowTime-recnet)/(1000*3600*24) >= 10) {
                return 200;
            } else {
                return 400;
            }
        }
    }

    @RequestMapping(value = "/save")
    public int saveRelationship(@RequestBody List<UserRelationship> relationships, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("user");
        String userId = user.getUserId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        for(int i=0; i<relationships.size(); i++) {
            relationships.get(i).setUserId(userId);
            relationships.get(i).setUserRelationshipTime(time);
            userRelationshipService.save(relationships.get(i));
        }
        return 200;
    }

    @RequestMapping(value = "/blankModel")
    public String getBlankModel(@RequestParam("number") int number, HttpSession session) {
        SysUser user = (SysUser)session.getAttribute("user");
        String recentTime = "";
        List<UserRelationship> userRelationshipList = userRelationshipService.getAllByUserId(user.getUserId());
        if(userRelationshipList==null || userRelationshipList.size()==0) {
            userRelationshipList = new ArrayList<>();
            UserRelationship userRelationship;
            for(int i=0; i<number;i++) {
                userRelationship = new UserRelationship();
                userRelationship.setUserRelationshipId("");
                userRelationship.setUserId("");
                userRelationship.setUserRelationshipTime("");
                userRelationship.setFriendId("");
                userRelationship.setUserRelationshipRank("");
                userRelationshipList.add(userRelationship);
            }
            return JSONObject.toJSONString(userRelationshipList);
        } else {
            //先找最近时间
            recentTime = userRelationshipList.get(0).getUserRelationshipTime();
            for(int i=0; i<userRelationshipList.size(); i++) {
                if(recentTime.compareTo(userRelationshipList.get(i).getUserRelationshipTime())<0) {
                    recentTime = userRelationshipList.get(i).getUserRelationshipTime();
                }
            }
            return JSONObject.toJSONString(userRelationshipService.getAllByUserIdAndTime(user.getUserId(), recentTime));
        }

    }
}
