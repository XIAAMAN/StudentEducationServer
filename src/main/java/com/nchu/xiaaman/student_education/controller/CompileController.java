package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.*;
import com.nchu.xiaaman.student_education.service.ExerciseCompileService;
import com.nchu.xiaaman.student_education.service.ExercisePracticeService;
import com.nchu.xiaaman.student_education.service.ExerciseScoreService;
import com.nchu.xiaaman.student_education.utils.CompileUnit;
import com.nchu.xiaaman.student_education.utils.UnionData;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/compile")
public class CompileController {

    @Autowired
    private ExercisePracticeService exercisePracticeService;

    @Autowired
    private ExerciseScoreService exerciseScoreService;

    @Autowired
    private ExerciseCompileService exerciseCompileService;

    private String CODE_PATH = "";
    private List<InputOutputFile> inputFiles;
    private List<InputOutputFile> outputFiles;
    private static final String base = "D:\\compileCode\\code\\";

    @MyLog(value = "在线运行")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/test")
    public String compileTest(@RequestBody SysExercise exercise, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("user");
        CompileUnit compileUnitTest = new CompileUnit();
        //设置一些参数值
        compileUnitTest.setUserName(user.getUserName());
        compileUnitTest.setFileUrl(exercise.getExerciseFileUrl());
        compileUnitTest.setInputCode(exercise.getExerciseInputExample());
        //返回对象
        JSONObject object = new JSONObject();
        String compileResult = compileUnitTest.compile(exercise.getExerciseCode());
        if("".equals(compileResult)) {
            //编译成功
            String result = compileUnitTest.runExe();
            if(compileUnitTest.getIsSuccess()) {
                //运行成功
                object.put("state", "200");
            } else {
                object.put("state", "400");
            }
            object.put("result", result);
            return object.toJSONString();

        } else {
            //编译失败，保存编译信息
            saveCompileInfo(user, exercise, compileResult);
            object.put("state", "400");
            object.put("result", compileResult);
            return object.toJSONString();
        }
    }

    @MyLog(value = "代码提交")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/submit")
    public String compileSubmit(@RequestBody UnionData unionData, HttpSession session) throws IOException {
        SysUser user = (SysUser) session.getAttribute("user");
        float score = 0;

        CompileUnit compileUnitSubmit = new CompileUnit();
        CODE_PATH = base + user.getUserName() + "\\";
        compileUnitSubmit.setUserName(user.getUserName());
        compileUnitSubmit.setFileUrl(unionData.getExercise().getExerciseFileUrl());
        compileUnitSubmit.setInputCode(unionData.getExercise().getExerciseInputExample());
        JSONObject object = new JSONObject();
        int rightNumber = 0;
        int fileNumber = 0;
        if(getSubmitTimes(user, unionData.getExercise(), unionData.getCollectionId()) >= 10) {
            object.put("state", "600");
            object.put("result", "题目已提交10次，不能再提交");
            return object.toJSONString();
        }
        String result = compileUnitSubmit.compile(unionData.getExercise().getExerciseCode());
        if(result.equals("")) {
            //编译成功
            //文件处理
            dealFiles(unionData.getExercise());
            fileNumber = inputFiles.size();
            for(int i=0; i<inputFiles.size(); i++) {
                String runOUtCome = run(inputFiles.get(i).getFile(), compileUnitSubmit);
                if(compileUnitSubmit.getIsSuccess()) {
                    for(int j=0; j<outputFiles.size(); j++) {
                        if(inputFiles.get(i).getFileName().equals(outputFiles.get(j).getFileName())
                                && runOUtCome.equals(IOUtils.toString(outputFiles.get(j).getFile(), String.valueOf(StandardCharsets.UTF_8)))) {
                            rightNumber ++;
                            outputFiles.get(j).getFile().close();
                        }
                    }
                }else {
                    //运行失败
                    saveScoreInfo(user, unionData.getExercise(), unionData.getCollectionId(), 0);
                    object.put("state", "400");
                    object.put("result", runOUtCome);
                    return object.toJSONString();
                }
            }
        } else {
            //编译失败,保存编译信息
            saveCompileInfo(user, unionData.getExercise(), result);
            saveScoreInfo(user, unionData.getExercise(), unionData.getCollectionId(), 0);
            object.put("state", "400");
            object.put("result", result);
            return object.toJSONString();
        }
        score = (((float)rightNumber)/fileNumber) * unionData.getExercise().getExerciseScore();
        score = Float.parseFloat(String.format("%.1f", score));     //保留一位小数
        //保存学生成绩
        saveScoreInfo(user, unionData.getExercise(), unionData.getCollectionId(), score);
        object.put("state", "200");
        object.put("result", "本题最终得分: "+score +"分");

        return object.toJSONString();
    }

    //题目练习提交代码
    @MyLog(value = "题目练习提交")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/practice")
    public String compilePractice(@RequestBody SysExercise exercise, HttpSession session) throws IOException {
        SysUser user = (SysUser) session.getAttribute("user");
        CompileUnit compileUnitPractice = new CompileUnit();

        CODE_PATH = base + user.getUserName() + "\\";
        compileUnitPractice.setUserName(user.getUserName());
        compileUnitPractice.setFileUrl(exercise.getExerciseFileUrl());
        compileUnitPractice.setInputCode(exercise.getExerciseInputExample());
        JSONObject object = new JSONObject();
        int rightNumber = 0;
        int fileNumber = 0;
        if(getPracticeTimes(user, exercise) >= 10) {
            object.put("state", "600");
            object.put("result", "题目已提交10次，不能再提交");
            return object.toJSONString();
        }
        String result = compileUnitPractice.compile(exercise.getExerciseCode());
        if(result.equals("")) {
            //编译成功
            //文件处理
            dealFiles(exercise);
            fileNumber = inputFiles.size();
            for(int i=0; i<inputFiles.size(); i++) {
                String runOUtCome = run(inputFiles.get(i).getFile(), compileUnitPractice);
                if(compileUnitPractice.getIsSuccess()) {
                    for(int j=0; j<outputFiles.size(); j++) {
                        if(inputFiles.get(i).getFileName().equals(outputFiles.get(j).getFileName())
                                && runOUtCome.equals(IOUtils.toString(outputFiles.get(j).getFile(), String.valueOf(StandardCharsets.UTF_8)))) {
                            rightNumber ++;
                            outputFiles.get(j).getFile().close();
                        }
                    }
                }else {
                    //运行失败
                    dealFailedExercisePractice(user, exercise);
                    object.put("state", "400");
                    object.put("result", runOUtCome);
                    return object.toJSONString();
                }
            }
        } else {
            //编译失败，保存编译信息
            saveCompileInfo(user, exercise, result);
            dealFailedExercisePractice(user, exercise);
            object.put("state", "400");
            object.put("result", result);
            return object.toJSONString();
        }

        if(rightNumber == fileNumber) {
            dealSuccessExercisePractice(user, exercise);
            object.put("state", "200");
            object.put("result", "恭喜你，答题正确，再接再厉");
        }else {
            dealFailedExercisePractice(user, exercise);
            object.put("state", "400");
            object.put("result", "很遗憾，答题失败，继续加油，早日找出bug");
        }

        return object.toJSONString();
    }

    @RequestMapping(value = "/getExercisePractice")
    public String getExercisePracticeByUserIdAndExerciseId(@RequestParam("exerciseId") String exerciseId, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("user");
        ExercisePractice practice = exercisePracticeService.getByUserIdAndExerciseId(exerciseId, user.getUserId());
        if(practice == null) {
            return JSONObject.toJSONString("");
        } else {
            if(practice.getExerciseCode() == null) {
                return JSONObject.toJSONString("");
            } else {
                return JSONObject.toJSONString(practice.getExerciseCode());
            }
        }
    }

    public void dealSuccessExercisePractice(SysUser user, SysExercise exercise) {
        ExercisePractice practice = exercisePracticeService.getByUserIdAndExerciseId(exercise.getExerciseId(), user.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(practice == null) {

            ExercisePractice newPractice = new ExercisePractice();
            newPractice.setUserId(user.getUserId());
            newPractice.setExerciseId(exercise.getExerciseId());
            newPractice.setExerciseCode(exercise.getExerciseCode());
            newPractice.setExercisePracticeTime(sdf.format(new Date()));
            newPractice.setExercisePracticeNumber(1);
            exercisePracticeService.saveExercisePractice(newPractice);
        } else {
            practice.setExercisePracticeNumber(practice.getExercisePracticeNumber() + 1);
            practice.setExercisePracticeTime(sdf.format(new Date()));
            practice.setExerciseCode(exercise.getExerciseCode());
            exercisePracticeService.saveExercisePractice(practice);
        }
    }

    public void dealFailedExercisePractice(SysUser user, SysExercise exercise) {
        ExercisePractice practice = exercisePracticeService.getByUserIdAndExerciseId(exercise.getExerciseId(), user.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(practice == null) {

            ExercisePractice newPractice = new ExercisePractice();
            newPractice.setUserId(user.getUserId());
            newPractice.setExerciseId(exercise.getExerciseId());
            newPractice.setExercisePracticeTime(sdf.format(new Date()));
            newPractice.setExercisePracticeNumber(1);
            exercisePracticeService.saveExercisePractice(newPractice);
        } else {
            practice.setExercisePracticeNumber(practice.getExercisePracticeNumber() + 1);
            practice.setExercisePracticeTime(sdf.format(new Date()));
            exercisePracticeService.saveExercisePractice(practice);
        }
    }

    public void dealFiles(SysExercise exercise) {
        inputFiles = new ArrayList<>();
        outputFiles = new ArrayList<>();
        InputOutputFile inputOutputFile;
        //文件处理
        String file = exercise.getExerciseFileUrl();
        file.replaceAll("\\\\", "/");
        ZipFile zFile = null;
        try {
            zFile = new ZipFile(file);
            // 此处最好立即设置字符集
            zFile.setFileNameCharset("GBK");
//            if (!zFile.isValidZipFile()) {
//                return object.toJSONString();
//            }

            // 获取ZIP中所有文件的FileHeader，以便后面对zip中文件进行遍历
            List<FileHeader> list = zFile.getFileHeaders();
            // 此时list的size包括：文件夹、子文件夹、文件的个数
            // 遍历其中的文件
            for (FileHeader fileHeader : list) {
                String fileName = fileHeader.getFileName();
                // fileName会将目录单独读出来，而且带有路径分割符
                if (fileName.endsWith("/") || fileName.endsWith("\\\\") || fileName.endsWith("\\")) {
                    continue;
                }else {
                    ZipInputStream inputStream = zFile.getInputStream(fileHeader);
                    int fileNameLength = fileName.length();
                    if(fileName.substring(fileNameLength-3,fileNameLength).equals(".in")) {
                        inputOutputFile = new InputOutputFile();
                        inputOutputFile.setFile(inputStream);
                        inputOutputFile.setFileName(fileName.substring(0,fileNameLength-3));
                        inputFiles.add(inputOutputFile);
                    }
                    if(fileName.substring(fileNameLength-4,fileNameLength).equals(".out")) {
                        inputOutputFile = new InputOutputFile();
                        inputOutputFile.setFile(inputStream);
                        inputOutputFile.setFileName(fileName.substring(0,fileNameLength-4));
                        outputFiles.add(inputOutputFile);
                    }
//                    String out = IOUtils.toString(inputStream, String.valueOf(StandardCharsets.UTF_8));
//                    System.out.println(out);
//                String Md5String = BigFileMD5.getStreamMD5(inputStream);
//                    System.out.println(fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String run(ZipInputStream file, CompileUnit compileUnit) {
        try {
            BufferedWriter bwInput=new BufferedWriter(new FileWriter(CODE_PATH + "test.in"));
            bwInput.write(IOUtils.toString(file, String.valueOf(StandardCharsets.UTF_8)));
            file.close();
            bwInput.close();
            String out = compileUnit.execCmd("cmd /c "+CODE_PATH+ compileUnit.getUserName() + ".exe < test.in",new File(CODE_PATH));
            //关掉exe进程
            if(compileUnit.getIsrunexe()) {
                try {
                    compileUnit.execCmd("taskkill /f /im "+compileUnit.getUserName() + ".exe",null);
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

    public void saveCompileInfo(SysUser user, SysExercise exercise, String result) {
        //编译失败，保存编译信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ExerciseCompile exerciseCompile = new ExerciseCompile();
        exerciseCompile.setUserId(user.getUserId());
        exerciseCompile.setExerciseId(exercise.getExerciseId());
        exerciseCompile.setExerciseCode(exercise.getExerciseCode());
        exerciseCompile.setExerciseCompileContent(result);
        exerciseCompile.setExerciseCompileTime(sdf.format(new Date()));
        exerciseCompileService.saveExerciseCompile(exerciseCompile);
    }

    //保存或更新学生成绩
    public void saveScoreInfo(SysUser user, SysExercise exercise, String collectionId, float score) {
        ExerciseScore es = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exercise.getExerciseId(), user.getUserId(), collectionId);
        if(es == null) {
            ExerciseScore exerciseScore = new ExerciseScore();
            exerciseScore.setUserId(user.getUserId());
            exerciseScore.setExerciseId(exercise.getExerciseId());
            exerciseScore.setCollectionId(collectionId);
            exerciseScore.setExerciseScoreAutoGrade(score);
            exerciseScore.setExerciseScoreManualGrade(0);
            exerciseScore.setExerciseScoreTimes(1);
            exerciseScore.setUserName(user.getUserName());
            exerciseScore.setExerciseCode(exercise.getExerciseCode());
            exerciseScoreService.saveExerciseScore(exerciseScore);
        } else {
            es.setExerciseScoreTimes(es.getExerciseScoreTimes()+1);
            es.setExerciseScoreAutoGrade(score);
            es.setExerciseCode(exercise.getExerciseCode());
            exerciseScoreService.saveExerciseScore(es);
        }


    }

    class InputOutputFile{
       private ZipInputStream file;
       private String fileName;

        public ZipInputStream getFile() {
            return file;
        }

        public void setFile(ZipInputStream file) {
            this.file = file;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }

    //检查课程题目提交次数
    public int getSubmitTimes(SysUser user, SysExercise exercise, String collectionId) {
        ExerciseScore es = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exercise.getExerciseId(), user.getUserId(), collectionId);
        if(es == null) {
            return 0;
        }else {
            return es.getExerciseScoreTimes();
        }
    }

    //检查题目练习提交次数
    public int getPracticeTimes(SysUser user, SysExercise exercise) {
        ExercisePractice exercisePractice = exercisePracticeService.getByUserIdAndExerciseId(exercise.getExerciseId(), user.getUserId());
        if(exercisePractice == null) {
            return 0;
        }else {
            return exercisePractice.getExercisePracticeNumber();
        }
    }
}
