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
        System.out.println("上传成功");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        List<MultipartFile> files = multipartRequest.getFiles("file");
        MultipartFile file = files.get(0);

        String filePath = "D:\\实验报告\\"+file.getOriginalFilename();

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
