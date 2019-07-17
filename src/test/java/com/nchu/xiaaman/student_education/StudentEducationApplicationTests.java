package com.nchu.xiaaman.student_education;

import com.nchu.xiaaman.student_education.utils.Md5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentEducationApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(new Md5Utils().md5("123456"));
    }

}
