package com.nchu.xiaaman.student_education.utils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.nchu.xiaaman.student_education.domain.SysPermis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PermisMenu {
    private String permisValueList;
    private int state;
    private int rankValue;
    private List<ParentPermis> parentPermisList;
//    private List<String> permisValue;

    // 获得所有权限的值


    // 将权限进行封装返回
    public PermisMenu decorateData(List<SysPermis> permisList, int state, String permisValueList, int rankValue) {
        PermisMenu permisMenu = new PermisMenu();
        ParentPermis parentPermis;
        List<ParentPermis> parentPermisList = new ArrayList<>();
        List<SysPermis> childrenPermisList;
        permisMenu.state = state;
        permisMenu.rankValue = rankValue;
        permisMenu.permisValueList = permisValueList;
        if (state == 400) {
            permisMenu.parentPermisList = null;
//            permisMenu.permisValue = null;
        }else {
            for (int i=0; i<permisList.size(); i++) {
                if (permisList.get(i).getPermisType() == 1) {
                    //表示这个根目录菜单
                    parentPermis = new ParentPermis();
                    childrenPermisList = new ArrayList<SysPermis>();

                    parentPermis.setPermisId(permisList.get(i).getPermisId());
                    parentPermis.setPermisParentId(permisList.get(i).getPermisParentId());
                    parentPermis.setPermisName(permisList.get(i).getPermisName());
                    parentPermis.setPermisPosition(permisList.get(i).getPermisPosition());
                    parentPermis.setPermisNameValue(permisList.get(i).getPermisNameValue());
                    parentPermis.setPermisDescription(permisList.get(i).getPermisDescription());
                    parentPermis.setPermisType(permisList.get(i).getPermisType());
                    parentPermis.setPermisUrl(permisList.get(i).getPermisUrl());
                    parentPermis.setPermisIcon(permisList.get(i).getPermisIcon());

                    for (int j=0; j<permisList.size(); j++) {
                        //找根目录菜单的子菜单
                        if(permisList.get(j).getPermisParentId().equals(parentPermis.getPermisId())) {
                            childrenPermisList.add(permisList.get(j));
                        }
                    }
                    parentPermis.setChildrenPermisList(childrenPermisList);
                    parentPermisList.add(parentPermis);
//                    permisMenu.parentPermisList.add(parentPermis);
                }
            }

//            permisMenu.permisValue = getPermisValue(permisList);
            //按照position进行升序排序
            // 先把根菜单进行排序
            Collections.sort(parentPermisList);
            for (int m=0; m<parentPermisList.size(); m++) {
                // 对子菜单进行排序
                Collections.sort(parentPermisList.get(m).getChildrenPermisList());
            }
            permisMenu.parentPermisList = parentPermisList;
        }

        return permisMenu;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

class ParentPermis implements Comparable <ParentPermis>{
    private String permisId;
    private String permisParentId;
    private String permisName;
    private String permisNameValue;
    private String permisDescription;
    private int permisPosition;
    private int permisType;
    private String permisUrl;
    private String permisIcon;
    private List<SysPermis> childrenPermisList;

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

    public List<SysPermis> getChildrenPermisList() {
        return childrenPermisList;
    }

    public int getPermisPosition() {
        return permisPosition;
    }

    public void setPermisPosition(int permisPosition) {
        this.permisPosition = permisPosition;
    }

    public void setChildrenPermisList(List<SysPermis> childrenPermisList) {
        this.childrenPermisList = childrenPermisList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public int compareTo(ParentPermis o) {
        return this.permisPosition - o.getPermisPosition();
    }
}
