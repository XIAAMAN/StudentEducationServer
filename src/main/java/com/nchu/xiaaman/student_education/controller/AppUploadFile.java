package com.nchu.xiaaman.student_education.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class AppUploadFile {
    @RequestMapping(value = "/appUploadFile")
    public void getCollection(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        List<MultipartFile> files = multipartRequest.getFiles("file");
        MultipartFile file = files.get(0);

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

        rep.getWriter().append("200").flush();
        rep.getWriter().close();
    }
}
