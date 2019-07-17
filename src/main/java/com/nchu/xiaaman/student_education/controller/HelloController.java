package com.nchu.xiaaman.student_education.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin        //解决跨域问题
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public int index() {
        System.out.print("Hello world");
        return 200;
    }
}
