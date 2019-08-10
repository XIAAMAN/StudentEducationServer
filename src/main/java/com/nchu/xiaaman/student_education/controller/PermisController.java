package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.RolePermis;
import com.nchu.xiaaman.student_education.domain.SysPermis;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.domain.UserRole;
import com.nchu.xiaaman.student_education.service.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/permis")
public class PermisController {
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermisService rolePermisService;

    @Autowired
    private SysPermisService sysPermisService;

    @MyLog(value = "题目所有角色")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/getRole")
    public String getRoleList(@RequestParam(value = "page",defaultValue = "1") int page,
                              @RequestParam(value = "size",defaultValue = "10") int size, HttpSession session) {
        int rankValue = (int) session.getAttribute("rankValue");
        Pageable pageable= PageRequest.of(page-1, size);
        return JSONObject.toJSONString(sysRoleService.getRoleList(rankValue, pageable));
    }

    @RequestMapping(value = "/getPermis")
    public String getHavedPermis( HttpSession session) {
        List<UserRole> userRoleList;        //接收用户角色表的数据
        List<RolePermis> rolePermisList = new ArrayList<>();        // 接收角色权限表的数据
        List<SysPermis> permisList = new ArrayList<>();         // 用户权限
        SysPermis tempPermis;
        SysUser user = (SysUser) session.getAttribute("user");
        //获得用户所有角色
        userRoleList = userRoleService.getUserRoleListByUserId(user.getUserId());
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
            tempPermis = sysPermisService.getAllRankSysPermisByPermisId(rolePermisList.get(i).getPermisId());
            if(tempPermis != null) {
                permisList.add(tempPermis);
            }
        }
        return new PermisUnion().decoratePermis(permisList);
    }

    @RequestMapping(value = "/rolePermis")
    public String getRolePermisValueArray(@RequestParam("roleId") String roleId) {
        //根据角色id查询权限id
        String[] permisIdArray = rolePermisService.getPermisIdListByRoleId(roleId);
        List<String> permisValueList = new ArrayList<>();
        List<SysPermis> permisList = new ArrayList<>();
        //根据角色id查询该角色下所有角色值
        for(int i=0; i<permisIdArray.length; i++) {
            permisList.add(sysPermisService.getById(permisIdArray[i]));
        }
        for(int i=0; i<permisList.size(); i++) {
            if((permisList.get(i).getPermisType()==1)) {
                for(int j=0; j<permisList.size();j++) {
                    //一二级菜单如果拥有子菜单权限，则移除父菜单权限，否则前台会包含所有子权限
                    if(permisList.get(j).getPermisParentId().equals(permisList.get(i).getPermisId())) {
                        permisList.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }

        for(int i=0; i<permisList.size(); i++) {
            if((permisList.get(i).getPermisType()==2)) {
                for(int j=0; j<permisList.size();j++) {
                    //一二级菜单如果拥有子菜单权限，则移除父菜单权限，否则前台会包含所有子权限
                    if(permisList.get(j).getPermisParentId().equals(permisList.get(i).getPermisId())) {
                        permisList.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }

        //将权限值存入
        for(int i=0; i<permisList.size();i++) {
            permisValueList.add(permisList.get(i).getPermisNameValue());
        }
        return JSONObject.toJSONString(permisValueList);
    }

    //修改权限
    @MyLog(value = "修改权限")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/modify")
    public int modifyRolePermis(@RequestParam("roleId") String roleId, @RequestParam("permisList") String[] permisValue) {
        rolePermisService.deleteByRoleId(roleId);
        List<String> permisList = new ArrayList<>();
        for(int i=0; i<permisValue.length; i++) {
            permisList.add(sysPermisService.getIdByNameValue(permisValue[i]));
        }
        RolePermis rolePermis;
        for(int i=0; i<permisList.size(); i++) {
            SysPermis sysPermis = sysPermisService.getById(permisList.get(i));
            //如果该权限是一二级菜单权限，则表示拥有所有子菜单权限
            if(sysPermis.getPermisType()==1) {  //home
                List<SysPermis> permis = sysPermisService.getPermisListByParentId(sysPermis.getPermisId());
                if(permis!=null) {
                    for(int j=0; j<permis.size(); j++) {
                        RolePermis secondRp = new RolePermis();
                        List<SysPermis> secondPermis = sysPermisService.getPermisListByParentId(permis.get(j).getPermisId());
                        if(secondPermis != null){
                            //将二级权限下的所有子权限存储
                            for(int k=0; k<secondPermis.size(); k++) {
                                RolePermis rp = new RolePermis();
                                rp.setRoleId(roleId);
                                rp.setPermisId(secondPermis.get(k).getPermisId());
                                rp.setRolePermisState(1);
                                rolePermisService.saveRolePermis(rp);
                            }
                        }
                        secondRp.setRoleId(roleId);
                        secondRp.setPermisId(permis.get(j).getPermisId());
                        secondRp.setRolePermisState(1);
                        rolePermisService.saveRolePermis(secondRp);
                    }
                }
            }
            else if(sysPermis.getPermisType() == 2) {
                //查询二级菜单权限的父权限,判断该权限是否已存在
                RolePermis rolePermis1 = rolePermisService.getByRoleAndPermis(roleId, sysPermis.getPermisParentId());
                if(rolePermis1 == null) {
                    RolePermis rolePer = new RolePermis();
                    rolePer.setRoleId(roleId);
                    rolePer.setPermisId(sysPermis.getPermisParentId());
                    rolePer.setRolePermisState(1);
                    rolePermisService.saveRolePermis(rolePer);
                }

                List<SysPermis> secondPermis = sysPermisService.getPermisListByParentId(sysPermis.getPermisId());
                if(secondPermis != null){
                    //将二级权限下的所有子权限存储
                    for(int k=0; k<secondPermis.size(); k++) {
                        RolePermis rp = new RolePermis();
                        rp.setRoleId(roleId);
                        rp.setPermisId(secondPermis.get(k).getPermisId());
                        rp.setRolePermisState(1);
                        rolePermisService.saveRolePermis(rp);
                    }
                }
            }
            else {
                //查询三级菜单权限的一二级父菜单权限是否存在
                RolePermis secondPermis = rolePermisService.getByRoleAndPermis(roleId, sysPermis.getPermisParentId());
                if(secondPermis == null) {
                    RolePermis rolePer = new RolePermis();
                    rolePer.setRoleId(roleId);
                    rolePer.setPermisId(sysPermis.getPermisParentId());
                    rolePer.setRolePermisState(1);
                    rolePermisService.saveRolePermis(rolePer);
                    String firstPermisId = sysPermisService.getById(sysPermis.getPermisParentId()).getPermisParentId();
                    RolePermis firstPermis = rolePermisService.getByRoleAndPermis(roleId, firstPermisId);
                    if(firstPermis == null) {
                        RolePermis firstRolePermis = new RolePermis();
                        firstRolePermis.setRoleId(roleId);
                        firstRolePermis.setPermisId(firstPermisId);
                        firstRolePermis.setRolePermisState(1);
                        rolePermisService.saveRolePermis(firstRolePermis);
                    }
                }
            }

            rolePermis = new RolePermis();
            rolePermis.setRoleId(roleId);
            rolePermis.setPermisId(permisList.get(i));
            rolePermis.setRolePermisState(1);
            rolePermisService.saveRolePermis(rolePermis);
        }
        return 200;
    }
    //对权限进行封装
    class PermisUnion{
        private String title;
        private String value;
        private String key;
        private boolean isLeaf = true;
        private List<PermisUnion> children;

        public String decoratePermis(List<SysPermis> permisList) {
            List<ChildrenPermis> childrenPermisList = new ArrayList<>();
            ChildrenPermis childrenPermis;
            List<PermisUnion> permisUnionsList = new ArrayList<>();
            //先找以及根菜单
            for(int i=0; i<permisList.size(); i++) {
                if(permisList.get(i).getPermisType() == 1) {        //一级权限
                    SysPermis firstPermis = permisList.get(i);
                    ChildrenPermis firstChildrenPermis = new ChildrenPermis();
                    firstChildrenPermis.setPermis(firstPermis);
                    List<ChildrenPermis> secondChildrenPermisList = new ArrayList<>();
                    for(int j=0; j<permisList.size(); j++) {
                        if(permisList.get(j).getPermisParentId().equals(firstPermis.getPermisId())) {   //二级权限
                            SysPermis secondPermis = permisList.get(j);
                            ChildrenPermis secondChildrenPermis = new ChildrenPermis();
                            secondChildrenPermis.setPermis(secondPermis);
                            List<ChildrenPermis> thirdChildrenPermisList = new ArrayList<>();
                            for(int k=0; k<permisList.size(); k++) {
                                if(permisList.get(k).getPermisParentId().equals(secondPermis.getPermisId())) {  //三级权限
                                    childrenPermis = new ChildrenPermis();
                                    childrenPermis.setPermis(permisList.get(k));
                                    thirdChildrenPermisList.add(childrenPermis);
                                }
                            }
                            if(thirdChildrenPermisList.size()>0) {
                                secondChildrenPermis.setChildren(thirdChildrenPermisList);
                            }
                            secondChildrenPermisList.add(secondChildrenPermis);
                        }
                    }
                    if(secondChildrenPermisList.size() > 0) {
                        firstChildrenPermis.setChildren(secondChildrenPermisList);
                    }
                    childrenPermisList.add(firstChildrenPermis);
                }
            }
            Collections.sort(childrenPermisList);
            for(int i=0; i<childrenPermisList.size(); i++) {
                if(childrenPermisList.get(i).getChildren() != null) {
                    Collections.sort(childrenPermisList.get(i).getChildren());
                }
            }

            for(int i=0; i<childrenPermisList.size(); i++) {
                //根目录权限
                PermisUnion firstPermisUnion = new PermisUnion();

                firstPermisUnion.setTitle(childrenPermisList.get(i).getPermis().getPermisName());
                firstPermisUnion.setValue(childrenPermisList.get(i).getPermis().getPermisName());
                firstPermisUnion.setKey(childrenPermisList.get(i).getPermis().getPermisNameValue());
                if(childrenPermisList.get(i).getChildren() != null) {
                    List<PermisUnion> secondList = new ArrayList<>();
                    firstPermisUnion.setLeaf(false);
                    for(int j=0; j<childrenPermisList.get(i).getChildren().size(); j++) {
                        //二级目录权限
                        PermisUnion secondPermisUnion = new PermisUnion();

                        secondPermisUnion.setTitle(childrenPermisList.get(i).getChildren().get(j).getPermis().getPermisName());
                        secondPermisUnion.setValue(childrenPermisList.get(i).getChildren().get(j).getPermis().getPermisName());
                        secondPermisUnion.setKey(childrenPermisList.get(i).getChildren().get(j).getPermis().getPermisNameValue());
                        if(childrenPermisList.get(i).getChildren().get(j).getChildren() != null) {
                            List<PermisUnion> thirdList = new ArrayList<>();
                            secondPermisUnion.setLeaf(false);
                            for(int k=0; k<childrenPermisList.get(i).getChildren().get(j).getChildren().size(); k++) {
                                //三级目录权限
                                PermisUnion thirdPermisUnion = new PermisUnion();
                                thirdPermisUnion.setTitle(childrenPermisList.get(i).getChildren().get(j).getChildren().get(k).getPermis().getPermisName());
                                thirdPermisUnion.setValue(childrenPermisList.get(i).getChildren().get(j).getChildren().get(k).getPermis().getPermisName());
                                thirdPermisUnion.setKey(childrenPermisList.get(i).getChildren().get(j).getChildren().get(k).getPermis().getPermisNameValue());
                                thirdList.add(thirdPermisUnion);
                            }
                            secondPermisUnion.setChildren(thirdList);
                        }
                        secondList.add(secondPermisUnion);
                    }
                    firstPermisUnion.setChildren(secondList);
                }
                permisUnionsList.add(firstPermisUnion);
            }
            return permisUnionsList.toString();
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isLeaf() {
            return isLeaf;
        }

        public void setLeaf(boolean leaf) {
            isLeaf = leaf;
        }

        public List<PermisUnion> getChildren() {
            return children;
        }

        public void setChildren(List<PermisUnion> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }
    }

    class ChildrenPermis implements Comparable <ChildrenPermis>{
        private SysPermis permis;
        private List<ChildrenPermis> children;

        public SysPermis getPermis() {
            return permis;
        }

        public void setPermis(SysPermis permis) {
            this.permis = permis;
        }

        public List<ChildrenPermis> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenPermis> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
        }

        @Override
        public int compareTo(ChildrenPermis o) {
            return this.permis.getPermisPosition() - o.getPermis().getPermisPosition();
        }
    }
}
