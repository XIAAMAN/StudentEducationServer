package com.nchu.xiaaman.student_education.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping(value = "/file")
public class FileUploadController {
    @RequestMapping(value = "/upload")
    public long uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("上传文件");
        System.out.println("file.Originalname : " + file.getOriginalFilename());
        System.out.println("file.name : " + file.getName());

        return new Date().getTime();
    }
}
