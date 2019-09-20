package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_relationship")
public class UserRelationship {
    @Id
    @Column(name = "user_relationship_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String userRelationshipId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "friend_id")
    private String friendId;

    @Column(name = "user_relationship_rank")
    private String userRelationshipRank;

    @Column(name = "user_relationship_time")
    private String userRelationshipTime;

    public String getUserRelationshipId() {
        return userRelationshipId;
    }

    public void setUserRelationshipId(String userRelationshipId) {
        this.userRelationshipId = userRelationshipId;
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

    public String getUserRelationshipRank() {
        return userRelationshipRank;
    }

    public void setUserRelationshipRank(String userRelationshipRank) {
        this.userRelationshipRank = userRelationshipRank;
    }

    public String getUserRelationshipTime() {
        return userRelationshipTime;
    }

    public void setUserRelationshipTime(String userRelationshipTime) {
        this.userRelationshipTime = userRelationshipTime;
    }
}
