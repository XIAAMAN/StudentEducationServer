package com.nchu.xiaaman.student_education.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sys_permis")
public class SysPermis {
    @Id
    @Column(name = "permis_id")
    @GeneratedValue(generator="uuidGenerator")
    @GenericGenerator(name="uuidGenerator",strategy="uuid") //UUID生成策略
    private String permisId;

    @Column(name = "permis_parent_id")
    private String permisParentId;

    @Column(name = "permis_name")
    private String permisName;

    @Column(name = "permis_name_value")
    private String permisNameValue;

    @Column(name = "permis_description")
    private String permisDescription;

    @Column(name = "permis_type")
    private int permisType;      //权限类别，1表示根节点，2表示子节点，不能三层节点

    @Column(name = "permis_url")
    private String permisUrl;   //权限路径，对应加载前台页面

    @Column(name = "permis_icon")
    private String  permisIcon;     //权限图标

    public String getPermisId() {
        return permisId;
    }

    public void setPermisId(String permisId) {
        this.permisId = permisId;
    }

    public String getPermisParentId() {
        return permisParentId;
    }

    public void setPermisParentId(String permisParentId) {
        this.permisParentId = permisParentId;
    }

    public String getPermisName() {
        return permisName;
    }

    public void setPermisName(String permisName) {
        this.permisName = permisName;
    }

    public String getPermisNameValue() {
        return permisNameValue;
    }

    public void setPermisNameValue(String permisNameValue) {
        this.permisNameValue = permisNameValue;
    }

    public String getPermisDescription() {
        return permisDescription;
    }

    public void setPermisDescription(String permisDescription) {
        this.permisDescription = permisDescription;
    }

    public int getPermisType() {
        return permisType;
    }

    public void setPermisType(int permisType) {
        this.permisType = permisType;
    }

    public String getPermisUrl() {
        return permisUrl;
    }

    public void setPermisUrl(String permisUrl) {
        this.permisUrl = permisUrl;
    }

    public String getPermisIcon() {
        return permisIcon;
    }

    public void setPermisIcon(String permisIcon) {
        this.permisIcon = permisIcon;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
