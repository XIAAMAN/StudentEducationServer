package com.nchu.xiaaman.student_education.config;
import com.alibaba.fastjson.JSON;
import com.nchu.xiaaman.student_education.domain.SysLog;
import com.nchu.xiaaman.student_education.domain.SysUser;
import com.nchu.xiaaman.student_education.service.SysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.nchu.xiaaman.student_education.config.MyLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        SysLog sysLog = new SysLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setLogName(value);//保存获取的操作名称
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setLogMethod(className + "." + methodName);


        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        if(params.length() > 990) {
            params = params.substring(0, 990) + "...]";
        }
        if(sysLog.getLogName()!=null && sysLog.getLogName().equals("用户登录")) {
            //对用户登录数据参数进行保护
            sysLog.setLogParams("[******]");
        } else {
            sysLog.setLogParams(params);
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sysLog.setLogCreateTime(dateFormat.format(new Date()));

        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        sysLog.setLogIp(request.getRemoteAddr());
        sysLog.setLogUrl(request.getRequestURL().toString());
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("user");
        if(user != null) {
            sysLog.setUserName(user.getUserName());
        }
        //调用service保存SysLog实体类到数据库
        sysLogService.save(sysLog);
    }

}