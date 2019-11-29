package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "blue_tooth")
public class BlueTooth {
    @Id
    @Column(name = "blue_tooth_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String blueToothId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "friend_id")
    private String friendId;

    @Column(name = "user_mac_address")
    private String userMacAddress;

    @Column(name = "friend_mac_address")
    private String friendMacAddress;

    @Column(name = "blue_distance")
    private float blueDistance;

    @Column(name = "blue_time")
    private String blueTime;

    public String getBlueToothId() {
        return blueToothId;
    }

    public void setBlueToothId(String blueToothId) {
        this.blueToothId = blueToothId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getUserMacAddress() {
        return userMacAddress;
    }

    public void setUserMacAddress(String userMacAddress) {
        this.userMacAddress = userMacAddress;
    }

    public String getFriendMacAddress() {
        return friendMacAddress;
    }

    public void setFriendMacAddress(String friendMacAddress) {
        this.friendMacAddress = friendMacAddress;
    }

    public float getBlueDistance() {
        return blueDistance;
    }

    public void setBlueDistance(float blueDistance) {
        this.blueDistance = blueDistance;
    }

    public String getBlueTime() {
        return blueTime;
    }

    public void setBlueTime(String blueTime) {
        this.blueTime = blueTime;
    }

}
