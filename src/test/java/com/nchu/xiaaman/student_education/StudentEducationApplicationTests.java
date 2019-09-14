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
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

            double load;
            try {
                Method method = OperatingSystemMXBean.class.getMethod("getSystemLoadAverage");
                load = (Double)method.invoke(operatingSystemMXBean);
            } catch (Throwable e) {
                load = -1;
            }
            int cpu = operatingSystemMXBean.getAvailableProcessors();
        System.out.println("cpu : " + cpu);
        System.out.println("load : " + load);
            if (load >= cpu) {
                System.err.println("WARN!!load:" + load + ","+ "cpu:" + cpu);
            }

    }



}
