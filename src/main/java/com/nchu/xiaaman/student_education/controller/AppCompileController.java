package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.*;
import com.nchu.xiaaman.student_education.service.*;
import com.nchu.xiaaman.student_education.utils.CompileUnit;
import com.nchu.xiaaman.student_education.utils.UnionData;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

@Controller
@RequestMapping(value = "/appCompile")
public class AppCompileController {
    @Autowired
    private ExercisePracticeService exercisePracticeService;

    @Autowired
    private ExerciseScoreService exerciseScoreService;

    @Autowired
    private ExerciseCompileService exerciseCompileService;

    @Autowired
    private SysExerciseService sysExerciseService;

    @Autowired
    private SysUserService sysUserService;

    private String CODE_PATH = "";
    private List<InputOutputFile> inputFiles;
    private List<InputOutputFile> outputFiles;
    private static final String base = "D:\\compileCode\\code\\";

    @RequestMapping(value = "/test")
    public void compileTest(@RequestBody List<SysExercise> exerciseList,  HttpServletRequest req,HttpServletResponse rep)  throws IOException {
        req.setCharacterEncoding("UTF-8");
        rep.setContentType("text/html;charset=utf-8");
        SysExercise exercise = exerciseList.get(0);
        SysUser user = sysUserService.getUserByUserName(exercise.getExerciseCheckUserName());
        CompileUnit compileUnitTest = new CompileUnit();
        //设置一些参数值
        compileUnitTest.setUserName(user.getUserName());
        compileUnitTest.setFileUrl(exercise.getExerciseFileUrl());
        compileUnitTest.setInputCode(exercise.getExerciseInputExample());
        //返回对象
//        JSONObject object = new JSONObject();
        String compileResult = compileUnitTest.compile(exercise.getExerciseCode());
        if("".equals(compileResult)) {
            //编译成功
            String result = compileUnitTest.runExe();
//            if(compileUnitTest.getIsSuccess()) {
//                //运行成功
//                object.put("state", "200");

//            } else {
//                object.put("state", "400");
//            }
//            object.put("result", result);
//            return object.toJSONString();
            rep.getWriter().append(result).flush();
            rep.getWriter().close();
            return;
//            return JSONObject.toJSONString(result);

        } else {
            //编译失败，保存编译信息
            saveCompileInfo(user, exercise, compileResult);
//            object.put("state", "400");
//            object.put("result", compileResult);
//            return object.toJSONString();
            rep.getWriter().append(compileResult).flush();
            rep.getWriter().close();
            return;
//            return JSONObject.toJSONString(compileResult);
        }

    }

    @RequestMapping(value = "/submit")
    public void submit(@RequestBody List<UnionData> unionDataList, HttpServletRequest req,HttpServletResponse rep) throws IOException {
        req.setCharacterEncoding("UTF-8");
        rep.setContentType("text/html;charset=utf-8");
        UnionData unionData = unionDataList.get(0);
        String userName = unionData.getExercise().getExerciseCheckUserName();
        SysUser user = sysUserService.getUserByUserName(userName);
        float score = 0;
        String testDetail = "";
        CompileUnit compileUnitSubmit = new CompileUnit();
        CODE_PATH = base + user.getUserName() + "\\";
        compileUnitSubmit.setUserName(user.getUserName());
        compileUnitSubmit.setFileUrl(unionData.getExercise().getExerciseFileUrl());
        compileUnitSubmit.setInputCode(unionData.getExercise().getExerciseInputExample());
//        JSONObject object = new JSONObject();
        int rightNumber = 0;
        int fileNumber = 0;
        //对编程题设置提交次数限制
        if(getSubmitTimes(user, unionData.getExercise(), unionData.getCollectionId()) >= 30 && unionData.getExercise().getExerciseType() == 1) {
//            object.put("state", "600");
//            object.put("result", "题目已提交10次，不能再提交");
//            return object.toJSONString();
            rep.getWriter().append("提交失败，提交次数已达到上限").flush();
            rep.getWriter().close();
            return;
//            return JSONObject.toJSONString("提交失败，提交次数已达到上限");
        }
        //主观题
        if(unionData.getExercise().getExerciseType() == 6) {
//            object.put("state", "200");
//            object.put("result", "答案已提交，等待教师评分");
            saveScoreInfo(user, unionData.getExercise(), unionData.getCollectionId(), 0);
//            return object.toJSONString();
            rep.getWriter().append("答案已提交").flush();
            rep.getWriter().close();
            return;
//            return JSONObject.toJSONString("答案已提交");
        }
        if(unionData.getExercise().getExerciseType() != 1) {
            float otherScore = dealSubmitOtherScore(user, unionData.getExercise(), unionData.getCollectionId());
            otherScore = Float.parseFloat(String.format("%.1f", otherScore));     //保留一位小数
            saveScoreInfo(user, unionData.getExercise(), unionData.getCollectionId(), otherScore);
//            object.put("state", "200");
//            object.put("result", "本题最终得分: "+otherScore +"分");
//            return object.toJSONString();

            rep.getWriter().append("答案已提交").flush();
            rep.getWriter().close();
            return;
//            return JSONObject.toJSONString("答案已提交");
        }
        String result = compileUnitSubmit.compile(unionData.getExercise().getExerciseCode());
        if(result.equals("")) {
            //编译成功
            //文件处理
            dealFiles(unionData.getExercise());
            fileNumber = inputFiles.size();
            for(int i=0; i<inputFiles.size(); i++) {
                String inputStr = IOUtils.toString(inputFiles.get(i).getFile(), String.valueOf(StandardCharsets.UTF_8));
                inputFiles.get(i).getFile().close();
                String runOUtCome = run(inputStr, compileUnitSubmit);

                //写入输入测试点
                testDetail +="测试点：" +(i+1)+"\n"+ "测试输入："+ inputStr + "\n";
                if(compileUnitSubmit.getIsSuccess()) {
                    for(int j=0; j<outputFiles.size(); j++) {
                        if(inputFiles.get(i).getFileName().equals(outputFiles.get(j).getFileName())) {
                            String answerOut = IOUtils.toString(outputFiles.get(j).getFile(), String.valueOf(StandardCharsets.UTF_8));
                            outputFiles.get(j).getFile().close();
                            testDetail+="测试输出：" + answerOut + "\n";
                            //过滤掉空格带来的错误
                            if(runOUtCome.replace(" ","").equals(answerOut.replace(" ",""))) {
                                rightNumber ++;
                                testDetail += "测试结果：通过\n\n";
                            } else {
                                testDetail += "测试结果：未通过\n\n";
                            }
                        }
                    }
                }else {
                    //运行失败
                    saveScoreInfo(user, unionData.getExercise(), unionData.getCollectionId(), 0);
//                    object.put("state", "400");
//                    object.put("result", runOUtCome);
//                    return object.toJSONString();
                    rep.getWriter().append(runOUtCome).flush();
                    rep.getWriter().close();
                    return;
                }
            }
        } else {
            //编译失败,保存编译信息
            saveCompileInfo(user, unionData.getExercise(), result);
            saveScoreInfo(user, unionData.getExercise(), unionData.getCollectionId(), 0);
//            object.put("state", "400");
//            object.put("result", result);
//            return object.toJSONString();
            rep.getWriter().append(result).flush();
            rep.getWriter().close();
            return;
//            return JSONObject.toJSONString(result);
        }
        score = (((float)rightNumber)/fileNumber) * unionData.getExercise().getExerciseScore();
        score = Float.parseFloat(String.format("%.1f", score));     //保留一位小数
        //保存学生成绩
        saveScoreInfo(user, unionData.getExercise(), unionData.getCollectionId(), score);
//        object.put("state", "200");
//        object.put("result", "本题最终得分: "+score +"分");
//
//        return object.toJSONString();
        rep.getWriter().append("本题最终得分: "+ score +"分\n\n"+testDetail).flush();
        rep.getWriter().close();
        return;
//        return JSONObject.toJSONString("本题最终得分: "+ score +"分");


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
            zFile.setFileNameCharset("UTF-8");
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

    public String run(String inputStr, CompileUnit compileUnit) {
        try {
            BufferedWriter bwInput=new BufferedWriter(new FileWriter(CODE_PATH + "test.in"));
            bwInput.write(inputStr);
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

    public String dealOtherExercise(SysExercise sysExercise, SysUser user) {
        JSONObject object = new JSONObject();
        SysExercise temp = sysExerciseService.getById(sysExercise.getExerciseId());
        if(sysExercise.getExerciseType() == 4) {
            String str = sysExercise.getExerciseCode();
            //去噪
            str = str.replace("\"", "\'");
            str = str.replace("‘", "\'");
            str = str.replace("’", "\'");
            str = str.replace("“", "\'");
            str = str.replace("”", "\'");
            str = str.replace(" ", "");

            String answerStr = temp.getExerciseCode();
            answerStr = answerStr.replace("\"", "\'");
            answerStr = answerStr.replace("‘", "\'");
            answerStr = answerStr.replace("’", "\'");
            answerStr = answerStr.replace("“", "\'");
            answerStr = answerStr.replace("”", "\'");
            answerStr = answerStr.replace(" ", "");
            if(answerStr.equals(str)) {
                dealSuccessExercisePractice(user, sysExercise);
                object.put("state", "200");
                object.put("result", "恭喜你，答题正确，再接再厉");
            } else {
                String result = "";
                String[] multiStr;
                int number = 0;
                int index=0;
                String[] answer = answerStr.split(";xiaaman;");
                String[] myAnswer = str.split(";xiaaman;");
                for(int i=0; i<answer.length; i++) {
                    //应对多种答案
                    index = i+1;
                    multiStr = answer[i].split(";or;");
                    for(int j=0; j<multiStr.length; j++) {
                        if(!multiStr[j].equals(myAnswer[i]) && j==multiStr.length-1) {
                            result += "第"+ index +"问答错;   ";
                            break;
                        }
                        if(multiStr[j].equals(myAnswer[i])) {
                            result += "第"+ index +"问答对;   ";
                            number++;
                            break;
                        }
                    }

                }
                if(number == myAnswer.length) {
                    dealSuccessExercisePractice(user, sysExercise);
                    object.put("state", "200");
                    object.put("result", "恭喜你，答题正确，再接再厉");
                } else {
                    dealFailedExercisePractice(user, sysExercise);
                    object.put("state", "400");
                    object.put("result", result);
                }

            }
//        }else if(sysExercise.getExerciseType() == 5) {
//            String myAnswer[] = sysExercise.getExerciseCode().split("");
//            String answer[] = temp.getExerciseCode().split("");
//            float number = 0;
//            if(temp.getExerciseCode().equals(sysExercise.getExerciseCode())) {
//                dealSuccessExercisePractice(user, sysExercise);
//                object.put("state", "200");
//                object.put("result", "恭喜你，答题正确，再接再厉");
//            } else {
//                for(int i=0; i<myAnswer.length; i++) {
//                    for(int j=0; j<answer.length; j++) {
//                        if(j==answer.length-1 && !(myAnswer[i].equals(answer[j]))) {
//                            dealFailedExercisePractice(user, sysExercise);
//                            object.put("state", "400");
//                            object.put("result", "很遗憾，答题失败，继续加油");
//                            return object.toJSONString();
//                        }
//                        if(myAnswer[i].equals(answer[j])) {
//                            number++;
//                        }
//                    }
//                }
//                dealFailedExercisePractice(user, sysExercise);
//                object.put("state", "400");
//                object.put("result", "很遗憾，答题错误");
//            }
//


        } else {
            if(temp.getExerciseCode().equals(sysExercise.getExerciseCode())) {
                dealSuccessExercisePractice(user, sysExercise);
                object.put("state", "200");
                object.put("result", "恭喜你，答题正确，再接再厉");
            }else {
                dealFailedExercisePractice(user, sysExercise);
                object.put("state", "400");
                object.put("result", "很遗憾，答题失败，继续加油");
            }
        }
        return object.toJSONString();
    }

    //算分数
    public float dealSubmitOtherScore(SysUser user, SysExercise exercise, String collectionId) {
        //获得原题目
        SysExercise answerExercise = sysExerciseService.getById(exercise.getExerciseId());
        //选择题或判断题
        if(exercise.getExerciseType()==2 || exercise.getExerciseType()==3) {
            if(exercise.getExerciseCode().equals(answerExercise.getExerciseCode())) {
                return exercise.getExerciseScore();
            } else {
                return 0;
            }
        }else if(exercise.getExerciseType() == 5){
            float number = 2;
            String myAnswer[] = exercise.getExerciseCode().split("");
            String answer[] = answerExercise.getExerciseCode().split("");
            if(answerExercise.getExerciseCode().equals(exercise.getExerciseCode())) {
                return exercise.getExerciseScore();
            } else {
                for(int i=0; i<myAnswer.length; i++) {
                    for(int j=0; j<answer.length; j++) {
                        if(j==answer.length-1 && !(myAnswer[i].equals(answer[j]))) {
                            return 0;
                        }
                        if(myAnswer[i].equals(answer[j])) {
                            break;
                        }
                    }
                }
                return exercise.getExerciseScore()/number;
            }
        } else {
            String str = exercise.getExerciseCode();
            //去噪
            str = str.replace("\"", "\'");
            str = str.replace("‘", "\'");
            str = str.replace("’", "\'");
            str = str.replace("“", "\'");
            str = str.replace("”", "\'");
            str = str.replace(" ", "");

            String answerStr = answerExercise.getExerciseCode();
            answerStr = answerStr.replace("\"", "\'");
            answerStr = answerStr.replace("‘", "\'");
            answerStr = answerStr.replace("’", "\'");
            answerStr = answerStr.replace("“", "\'");
            answerStr = answerStr.replace("”", "\'");
            answerStr = answerStr.replace(" ", "");

            if(str.equals(answerStr)) {
                return exercise.getExerciseScore();
            } else {
                String result = "";
                float number = 0;
                String[] answer = answerStr.split(";xiaaman;");
                String[] myAnswer = str.split(";xiaaman;");
                String[] multiStr;
                for(int i=0; i<answer.length; i++) {
                    multiStr = answer[i].split(";or;");
                    for(int j=0; j<multiStr.length; j++) {
                        if(multiStr[j].equals(myAnswer[i])) {
                            number++;
                            break;
                        }
                    }
                }
                return exercise.getExerciseScore()*(number/answer.length);
            }
        }
    }
}
