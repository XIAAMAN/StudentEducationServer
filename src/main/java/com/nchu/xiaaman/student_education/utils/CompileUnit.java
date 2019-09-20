package com.nchu.xiaaman.student_education.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompileUnit {
    //线程池大小
    private static final int THREAD_COUNT = 30;
    //超时时间 s
    private static final int RUN_TIME_LIMIT = 10;

    private static final String INTERRUPT_ERROR = "programme interrupted";

    private static final String TIMEOUT_ERROR = "time out";

    private static final String EXECUTE_ERROR = "execute error";

    private String inputCode;

    private String userName;

    private String fileUrl;

    public boolean isSuccess = true;
    private boolean isrunexe = false;

    //线程池
    private static final ExecutorService executePool = new ThreadPoolExecutor(
            THREAD_COUNT,
            THREAD_COUNT,
            60L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(THREAD_COUNT)
    );

    //代码存放路径
    public  String CODE_PATH = "";
    private static final String base = "D:\\compileCode\\code\\";

    /**
     * @param content C代码
     * */
    private boolean generateCFile(String content){
        isSuccess = true;
        isrunexe = false;
        BufferedWriter out = null;
        CODE_PATH = base + userName + "\\";
        File newFile = new File(CODE_PATH + userName + ".c");
        if(!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        try{
            //写入
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CODE_PATH + userName + ".c", false)));
            out.write(content);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                out.close();
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public String compile(String sourceCode) {
        generateCFile(sourceCode);
        //编译C文件
        String compileResult = null;
        try {
            compileResult = execCmd("gcc -o "+CODE_PATH+ userName + " "+CODE_PATH+ userName +".c",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compileResult;
    }

    public String runExe() {
        try {
            BufferedWriter bwInput=new BufferedWriter(new FileWriter(CODE_PATH + "test.in"));
            bwInput.write(inputCode);
            bwInput.close();
            String out = execCmd("cmd /c "+CODE_PATH+ userName + ".exe < test.in",new File(CODE_PATH));
            if(isrunexe) {
                try {
                    execCmd("taskkill /f /im "+userName + ".exe",null);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            return out;
        } catch (Exception e) {
            System.out.println("gcc调用出错");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 执行系统命令, 返回执行结果
     * @param cmd 需要执行的命令
     * @param dir 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
     */
    public String execCmd(String cmd, File dir) throws Exception {
        StringBuilder result = new StringBuilder();
        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;
        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmd, null, dir);
            // 方法阻塞, 等待命令执行完成（成功会返回0）


            CompileUnit.Worker worker = new CompileUnit.Worker(process);
            worker.start();

            try {
                worker.join(10000);     //设置10秒超时
                if (worker.exit != null){
//                    return worker.exit;
                } else{
                    isSuccess = false;
                    isrunexe = true;
                    return "运行超时，代码可能存在死循环";
                }
            } catch (InterruptedException ex) {
                worker.interrupt();
                Thread.currentThread().interrupt();
                throw ex;
            } finally {
                process.destroy();
            }


//            process.waitFor();
            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), Charset.defaultCharset()));
            // 读取输出
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                result.append(line);
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
                isSuccess = false;
            }
        } finally {
            closeStream(bufrIn);
            closeStream(bufrError);
            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }
        // 返回执行结果
        return result.toString();
    }

    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean getIsrunexe() {
        return isrunexe;
    }

    public void setIsrunexe(boolean isrunexe) {
        this.isrunexe = isrunexe;
    }

    private void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                // nothing
            }
        }
    }


    static class Worker extends Thread {
        private final Process process;
        private Integer exit;

        private Worker(Process process) {
            this.process = process;
        }

        public void run() {
            try {
                exit = process.waitFor();
            } catch (InterruptedException ignore) {
                return;
            }
        }
    }
}
