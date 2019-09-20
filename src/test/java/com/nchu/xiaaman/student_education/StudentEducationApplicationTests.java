package com.nchu.xiaaman.student_education;

import com.nchu.xiaaman.student_education.domain.RolePermis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentEducationApplicationTests {
    @Test
    public void contextLoads() {
        String str = "xiaa ma[i = 3]n\"xi\"‘wfwe’efe‘’“分”分为";
        System.out.println("替换前: "+str);
        str = str.replace("\"", "\'");
        str = str.replace("‘", "\'");
        str = str.replace("’", "\'");
        str = str.replace("“", "\'");
        str = str.replace("”", "\'");
        str = str.replace(" ", "");
        System.out.println("替换后: "+str);
    }



}
