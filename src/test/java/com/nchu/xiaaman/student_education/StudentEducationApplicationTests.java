package com.nchu.xiaaman.student_education;

import com.nchu.xiaaman.student_education.domain.RolePermis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentEducationApplicationTests {
    @Test
    public void contextLoads() {
        RolePermis s1 = new RolePermis();
        s1.setRolePermisId("123456");
        RolePermis s2 = new RolePermis();
        s2.setRolePermisId("12345625874");
        RolePermis s3 = new RolePermis();
        s3.setRolePermisId("123456");
        List<RolePermis> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        System.out.println(list.contains(s3));
    }
}
