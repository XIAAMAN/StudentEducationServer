package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sys_user")
public class SysUser {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String userId;

    @Column(name = "user_name")
    private String userName;        //用户账号名称（唯一）

    @Column(name = "user_real_name")
    private String userRealName;

    @Column(name = "user_class")
    private String userClass;       //班级

    @Column(name = "user_number")
    private String userNumber;      //学号或工号

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_recommend_name")
    private String userRecommendName;       //用户推荐人账号名称，针对于助教用户，需要存取创建这个身份的老师账号名称

    @Column(name = "user_status")       //用户状态，默认为1,0表示该用户不可见用户状态，默认为1,0表示该用户不可见
    private int userStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRecommendName() {
        return userRecommendName;
    }

    public void setUserRecommendName(String userRecommendName) {
        this.userRecommendName = userRecommendName;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }
}
