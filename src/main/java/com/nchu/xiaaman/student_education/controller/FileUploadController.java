package com.nchu.xiaaman.student_education.controller;

import com.nchu.xiaaman.student_education.domain.ClassTeacher;
import com.nchu.xiaaman.student_education.domain.SysClass;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.domain.UserRole;
import com.nchu.xiaaman.student_education.service.*;
import com.nchu.xiaaman.student_education.utils.Md5Utils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping(value = "/file")
public class FileUploadController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private ClassTeacherService classTeacherService;

    public Md5Utils md5Utils;
    @RequestMapping(value = "/upload")
    public long uploadFile(@RequestParam("file") MultipartFile file) {
        long time = new Date().getTime();
        String filePath = "D:\\compileFiles\\" + time + file.getOriginalFilename();

        File newFile = new File(filePath);
        if (!newFile.getParentFile().exists()) { //判断文件父目录是否存在
            newFile.getParentFile().mkdir();
        }
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            out.write(file.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return time;
    }

    @RequestMapping(value = "/studentExcel")
    public int signInStudent(@RequestParam("file") MultipartFile file) {
        int studentNumber  = 0;
        int everyClassNumber = 0;
        String classNumber = "";
        SysClass sysClass;
        String password = md5Utils.md5("123456");
        //获取学生角色id
        String roleId = sysRoleService.getRoleIdByRoleName("学生");
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        HttpSession session = request.getSession();
        SysUser sessionUser = (SysUser) session.getAttribute("user");
        try {
            InputStream inp = file.getInputStream();
            XSSFWorkbook work = new XSSFWorkbook(inp);
            XSSFSheet sheet = null;
            Row row = null;
            Cell cell = null;
            SysUser user;
            UserRole userRole;
            //获取有多少sheet
            //测试账户是否被注册和文件内容是否符合要求
            //班级是否已经存在
            //文件中所有班级号是否一样
            for (int i = 0; i < work.getNumberOfSheets(); i++) {
                sheet = work.getSheetAt(i);
                if(sheet==null){continue;}
                for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if(row==null){continue;}
                    for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        cell.setCellType(CellType.STRING);
                        String cellStr =""+ cell.getStringCellValue();
                       if(y==1) {    //设置姓名、密码、时间
                            if(cellStr.length() != 8) {
                                return 300;         //学号格式错误
                            }
                            user = sysUserService.getUserByUserNumber(cellStr);
                            if(user != null) {
                                return 400;     //学号被占用
                            }
                            if(sysUserService.getUserByUserName(cellStr) != null) {
                                return 500;     //用户名已被注册
                            }
                        } else if(y==2) {
                            if(j==0) {
                                classNumber =cellStr;
                                sysClass = sysClassService.getSysClassByNumber(cellStr);
                                if(sysClass != null) {
                                    return 600;     //班级已存在
                                }
                            } else {
                                if(!classNumber.equals(cellStr)){
                                    return 700;         //班级号不一致
                                }
                            }
                       }
                    }
                }
            }

            //将数据存储到数据库
            for (int i = 0; i < work.getNumberOfSheets(); i++) {
                everyClassNumber = 0;
                String sysClassNumber = "";
                sheet = work.getSheetAt(i);
                SysClass newClass;
                ClassTeacher classTeacher;
                if(sheet==null){continue;}
                //每个sheet有多少行,从第一条有用数据开始读取
                for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                    row = sheet.getRow(j);
                    if(row==null){continue;}
                    //每一行就是一个user
                    user = new SysUser();
                    //List<Object> li = new ArrayList<Object>();
                    //每行有多少列，学号、姓名、密码初始化为123456
                    for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                        cell = row.getCell(y);
                        cell.setCellType(CellType.STRING);
                        String cellStr =""+ cell.getStringCellValue();
                        if (y == 0) {   //设置姓名
                            user.setUserRealName(cellStr);
                        }else if(y==1) {    //设置学号、用户名,将学号作为用户名
                            user.setUserNumber(cellStr);
                            user.setUserName(cellStr);
                        } else if(y==2) {      //设置班级
                            if(j==0) {
                                sysClassNumber = cellStr;
                            }
                            user.setUserClass(cellStr);
                        }
                    }
                    //密码初始为123456、状态、创建人
                    user.setUserPassword(password);
                    user.setUserStatus(1);
                    user.setUserRecommendName(sessionUser.getUserName());
                    //将用户存入数据库
                    sysUserService.saveUser(user);
                    //给用户配置学生角色
                    SysUser newUser = sysUserService.getUserByUserName(user.getUserName());
                    userRole = new UserRole();
                    userRole.setRoleId(roleId);
                    userRole.setUserId(newUser.getUserId());
                    userRoleService.saveUserRole(userRole);
                    studentNumber++;
                    everyClassNumber ++;
                }
                //增加班级到数据库
                newClass = new SysClass();
                newClass.setClassPeople(everyClassNumber);
                newClass.setClassNumber(sysClassNumber);
                newClass.setClassGrade(2000+Integer.parseInt(sysClassNumber.substring(0,2)));
                sysClassService.saveClass(newClass);

                //增加班级教师数据到数据库
                classTeacher = new ClassTeacher();
                classTeacher.setUserId(sessionUser.getUserId());
                classTeacher.setClassId(sysClassService.getSysClassByNumber(newClass.getClassNumber()).getClassId());
                classTeacherService.saveClassTeacher(classTeacher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentNumber;
    }

    @RequestMapping(value = "/labWord")
    public int saveLabWordFile (@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String foldName = "";
        String listName[] = {"第一次实验报告", "第二次实验报告", "第三次实验报告", "第四次实验报告", "第五次实验报告"
                , "第六次实验报告", "第七次实验报告", "第八次实验报告", "第九次实验报告", "第十次实验报告"};
        if(fileName.contains("一")) {
            foldName = listName[0];
        } else if(fileName.contains("二")){
            foldName = listName[1];
        }else if(fileName.contains("三")){
            foldName = listName[2];
        }else if(fileName.contains("四")){
            foldName = listName[3];
        }else if(fileName.contains("五")){
            foldName = listName[4];
        }else if(fileName.contains("六")){
            foldName = listName[5];
        }else if(fileName.contains("七")){
            foldName = listName[6];
        }else if(fileName.contains("八")){
            foldName = listName[7];
        }else if(fileName.contains("九")){
            foldName = listName[8];
        }else if(fileName.contains("十")){
            foldName = listName[9];
        }else{
            foldName = "错误格式文档";
        }
        String filePath = "D:\\实验报告\\"+foldName+"\\"+file.getOriginalFilename();
        File newFile = new File(filePath);
        if (!newFile.getParentFile().exists()) { //判断文件父目录是否存在
            newFile.getParentFile().mkdir();
        }
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            out.write(file.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 200;
    }
}
