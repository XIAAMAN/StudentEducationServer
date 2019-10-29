package com.nchu.xiaaman.student_education.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "sys_collection")
@Proxy(lazy = false)    //设置立即加载
public class SysCollection {
    @Id
    @Column(name = "collection_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String collectionId;

    @Column(name = "collection_name")
    private String collectionName;

    @Column(name = "collection_rate")
    private float collectionRate;       //每次试验在整个实验课程中的占比

    @Column(name = "collection_create_user_name")
    private String collectionCreateUserName;       //创建人

    @Column(name = "collection_start_time")
    private String collectionStartTime;             //本次试验开始时间

    @Column(name = "collection_end_time")
    private String collectionEndTime;               //本次试验结束时间

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public float getCollectionRate() {
        return collectionRate;
    }

    public void setCollectionRate(float collectionRate) {
        this.collectionRate = collectionRate;
    }

    public String getCollectionCreateUserName() {
        return collectionCreateUserName;
    }

    public void setCollectionCreateUserName(String collectionCreateUserName) {
        this.collectionCreateUserName = collectionCreateUserName;
    }

    public String getCollectionStartTime() {
        return collectionStartTime;
    }

    public void setCollectionStartTime(String collectionStartTime) {
        this.collectionStartTime = collectionStartTime;
    }

    public String getCollectionEndTime() {
        return collectionEndTime;
    }

    public void setCollectionEndTime(String collectionEndTime) {
        this.collectionEndTime = collectionEndTime;
    }
}
