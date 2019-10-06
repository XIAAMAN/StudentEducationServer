package com.nchu.xiaaman.student_education.controller;

import com.alibaba.fastjson.JSONObject;
import com.nchu.xiaaman.student_education.config.MyLog;
import com.nchu.xiaaman.student_education.domain.*;
import com.nchu.xiaaman.student_education.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/score")
public class LookScoreController {
    @Autowired
    private ExerciseScoreService exerciseScoreService;

    @Autowired
    private SysCourseService sysCourseService;

    @Autowired
    private CourseCollectionService courseCollectionService;

    @Autowired
    private SysCollectionService sysCollectionService;

    @Autowired
    private CollectionExerciseService collectionExerciseService;

    @Autowired
    private SysExerciseService sysExerciseService;

    @Autowired
    private ClassTeacherService classTeacherService;

    @Autowired
    private SysClassService sysClassService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    private SysUserService sysUserService;

    @MyLog(value = "学生查看成绩")
    @RequestMapping(value = "/studentLook")
    public String lookScore(@RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "size",defaultValue = "10") int size, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("user");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //学生查询成绩，返回已经结束的题目集
        //根据学生班级号查询课程
        List<SysCourse> courseList = sysCourseService.getCourseId('%' + user.getUserClass() + '%');
        //根据课程id查询已经结束的题目集id
        List<String> collectionId;
        List<SysCollection> collectionList;
        List<String> exerciseId;
        List<StudentScoreData> studentScoreDataList = new ArrayList<>();
        StudentScoreData studentScoreData;
        ExerciseScore exerciseScore;
        int deciedTimes;    //题目已提交个数
        float getScore;
        float totalScore;
        for(int i=0; i<courseList.size(); i++) {
            collectionId = courseCollectionService.getCollectionIdListByCourseId(courseList.get(i).getCourseId());
            for (int j=0; j<collectionId.size(); j++) {
                SysCollection collection = sysCollectionService.getByIdAndTime(collectionId.get(j), sdf.format(new Date()));
                if(collection != null) {
                    exerciseId = collectionExerciseService.getExerciseIdListByCollectionId(collection.getCollectionId());
                    studentScoreData = new StudentScoreData();
                    studentScoreData.setCollectionId(collectionId.get(j));
                    studentScoreData.setCollectionName(collection.getCollectionName());
                    studentScoreData.setCourseName(courseList.get(i).getCourseName());
                    studentScoreData.setExerciseNumber(exerciseId.size());
                    deciedTimes = 0;
                    getScore = 0;
                    totalScore = 0;
                    for(int k=0; k<exerciseId.size(); k++) {
                        exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseId.get(k), user.getUserId(),collectionId.get(j));
                        if(exerciseScore != null) {
                            getScore += exerciseScore.getExerciseScoreAutoGrade();
                            deciedTimes ++;
                        }
                        totalScore += sysExerciseService.getById(exerciseId.get(k)).getExerciseScore();
                    }
                    studentScoreData.setDeciedNumber(deciedTimes);
                    studentScoreData.setCollectionScore(getScore);
                    studentScoreData.setTotalScore(totalScore);
                    studentScoreDataList.add(studentScoreData);
                }
            }
        }
        return new StudentScoreDataUnion().union(studentScoreDataList, page, size);

    }

    //学生查看题目集得分详情
    @MyLog(value = "查看题目集得分详情")
    @RequestMapping(value = "/scoreDetail")
    public String lookScore(@RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "size",defaultValue = "10") int size,
                            @RequestParam(value = "collectionId")String collectionId, HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("user");
        List<ScoreDetail> scoreDetailList = new ArrayList<>();
        ScoreDetail scoreDetail;
        SysExercise sysExercise;
        ExerciseScore exerciseScore;
        List<String> exerciseIdList;
        exerciseIdList = collectionExerciseService.getExerciseIdListByCollectionId(collectionId);
        for(int i=0; i<exerciseIdList.size(); i++) {
            sysExercise = sysExerciseService.getById(exerciseIdList.get(i));
            exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseIdList.get(i), user.getUserId(), collectionId);
            if(exerciseScore != null) {
                scoreDetail = new ScoreDetail();
                scoreDetail.setExerciseName(sysExercise.getExerciseName());
                scoreDetail.setExerciseLabel(sysExercise.getExerciseLabel());
                scoreDetail.setExerciseType(sysExercise.getExerciseType());
                scoreDetail.setTimes(exerciseScore.getExerciseScoreTimes());
                scoreDetail.setRightScore(exerciseScore.getExerciseScoreAutoGrade());
                scoreDetail.setTotalScore(sysExercise.getExerciseScore());
                scoreDetailList.add(scoreDetail);
            }
        }
        return new ScoreDetailUnion().union(scoreDetailList, page, size);
    }

    //教师查看题目集得分详情
    @MyLog(value = "查看题目集得分详情")
    @RequestMapping(value = "/teacherScoreDetail")
    public String teacherLookScore(@RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "size",defaultValue = "10") int size,
                            @RequestParam(value = "collectionId")String collectionId,
                                   @RequestParam(value = "userNumber") String userNumber) {
        SysUser user = sysUserService.getUserByUserNumber(userNumber);
        List<ScoreDetail> scoreDetailList = new ArrayList<>();
        ScoreDetail scoreDetail;
        SysExercise sysExercise;
        ExerciseScore exerciseScore;
        List<String> exerciseIdList;
        exerciseIdList = collectionExerciseService.getExerciseIdListByCollectionId(collectionId);
        for(int i=0; i<exerciseIdList.size(); i++) {
            sysExercise = sysExerciseService.getById(exerciseIdList.get(i));
            exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseIdList.get(i), user.getUserId(), collectionId);
            if(exerciseScore != null) {
                scoreDetail = new ScoreDetail();
                scoreDetail.setExerciseName(sysExercise.getExerciseName());
                scoreDetail.setExerciseLabel(sysExercise.getExerciseLabel());
                scoreDetail.setExerciseType(sysExercise.getExerciseType());
                scoreDetail.setTimes(exerciseScore.getExerciseScoreTimes());
                scoreDetail.setRightScore(exerciseScore.getExerciseScoreAutoGrade());
                scoreDetail.setTotalScore(sysExercise.getExerciseScore());
                scoreDetailList.add(scoreDetail);
            }
        }
        return new ScoreDetailUnion().union(scoreDetailList, page, size);
    }

    @MyLog(value = "查看学生成绩详情")
    @RequestMapping(value = "/studentDetail")
    public String lookStudentDetail(@RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "size",defaultValue = "10") int size,
                                    @RequestParam(value = "courseName") String courseName,
                                    @RequestParam(value = "userNumber") String userNumber) {
        SysUser user = sysUserService.getUserByUserNumber(userNumber);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //学生查询成绩，返回已经结束的题目集
        //根据学生班级号查询课程
        //根据课程id查询已经结束的题目集id
        String courseId = sysCourseService.getCourseIdByName(courseName);
        List<String> collectionId;
        List<String> exerciseId;
        List<StudentScoreData> studentScoreDataList = new ArrayList<>();
        StudentScoreData studentScoreData;
        ExerciseScore exerciseScore;

        collectionId = courseCollectionService.getCollectionIdListByCourseId(courseId);
//        for (int j=0; j<collectionId.size(); j++) {
//            SysCollection collection = sysCollectionService.getByIdAndTime(collectionId.get(j), sdf.format(new Date()));
//            if(collection != null) {
//                exerciseId = collectionExerciseService.getExerciseIdListByCollectionId(collection.getCollectionId());
//                for(int k=0; k<exerciseId.size(); k++) {
//                    studentScoreData = new StudentScoreData();
//                    studentScoreData.setCourseName(courseName);
//                    studentScoreData.setCollectionName(collection.getCollectionName());
//                    studentScoreData.setExerciseName(sysExerciseService.getById(exerciseId.get(k)).getExerciseName());
//                    exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseId.get(k), user.getUserId(), collection.getCollectionId());
//                    if(exerciseScore == null) {
//                        //学生可能这道题没有提交，则默认0分
//                        studentScoreData.setExerciseAutoScore(0);
//                    } else {
//                        studentScoreData.setExerciseAutoScore(exerciseScore.getExerciseScoreAutoGrade());
//                    }
//                    studentScoreData.setExerciseTotalScore(sysExerciseService.getById(exerciseId.get(k)).getExerciseScore());
//                    studentScoreDataList.add(studentScoreData);
//                }
//            }
//        }

        return new StudentScoreDataUnion().union(studentScoreDataList, page, size);
    }


    @MyLog(value = "教师查看成绩")
    @RequestMapping(value = "/teacherLook")
    public String lookScore(@RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "size",defaultValue = "10") int size,
                            @RequestParam(value = "classNumber") String classNumber,
                            @RequestParam(value = "courseName") String courseName,
                            @RequestParam(value = "collectionName") String collectionName) {
        //判断该班级是否有这门课程
        if(sysCourseService.getBycourseNameandClass(courseName, "%"+classNumber+"%") == null) {
            return "400";
        }
        //判断该课程是否有该题目集
        String collectionId = sysCollectionService.getByCollectionName(collectionName).getCollectionId();
        String courseId = sysCourseService.getCourseIdByName(courseName);
        if(courseCollectionService.getByCollectionIdAndCourseId(collectionId, courseId) == null) {
            return "400";
        }

        //该题目集下所有题目id
        List<String> exerciseIdList = collectionExerciseService.getExerciseIdListByCollectionId(collectionId);
        //该班级所有用户
        List<SysUser> userList = sysUserService.getUserListByClass(classNumber);
        List<TeacherScoreData> teacherScoreDataList = new ArrayList<>();
        TeacherScoreData teacherScoreData;
        ExerciseScore exerciseScore;
        float totalScore;
        float getScore;
        int deciedNumber;
        for(int i=0; i<userList.size(); i++) {
            totalScore = 0;
            getScore = 0;
            deciedNumber = 0;
            teacherScoreData = new TeacherScoreData();
            teacherScoreData.setCollectionId(collectionId);
            teacherScoreData.setUserNumber(userList.get(i).getUserNumber());    //学号
            teacherScoreData.setUserRealName(userList.get(i).getUserRealName());    //姓名
            teacherScoreData.setExerciseNumber(exerciseIdList.size());  //题目总数

            for(int j=0; j<exerciseIdList.size(); j++) {
                totalScore += sysExerciseService.getById(exerciseIdList.get(j)).getExerciseScore();
                exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseIdList.get(j), userList.get(i).getUserId(), collectionId);
                if(exerciseScore != null) {
                    deciedNumber++;
                    getScore += exerciseScore.getExerciseScoreAutoGrade();
                }
            }

            teacherScoreData.setDeciedNumber(deciedNumber);     //题目提交数量
            teacherScoreData.setCollectionScore(getScore);      //得分
            teacherScoreData.setTotalScore(totalScore);     //总分
            teacherScoreDataList.add(teacherScoreData);
        }
        //获得所有已经结束的题目集
        return new TeacherScoreDataUnion().union(teacherScoreDataList, page, size);
    }
    //查询教师或管理员管理班级
    @RequestMapping(value = "/getClass")
    public String getClasses() {
        return JSONObject.toJSONString(sysClassService.getClassNameList());
    }

    //查询教师所授课程
    @RequestMapping(value = "/getCourse")
    public String getCourse() {
        return JSONObject.toJSONString(sysCourseService.getCourseNameList());

    }

    //查询题目集
    @RequestMapping(value = "/getCollection")
    public String getCollection() {
        return JSONObject.toJSONString(sysCollectionService.getCollectionName());

    }


    //
    class StudentScoreData{
        private String collectionId;
        private String courseName;
        private String collectionName;
        private int deciedNumber;   //学生答题数量
        private int exerciseNumber;   //题目集题目数量
        private float collectionScore;  //题目集得分
        private float totalScore;  //题目集总分

        public String getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(String collectionId) {
            this.collectionId = collectionId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCollectionName() {
            return collectionName;
        }

        public void setCollectionName(String collectionName) {
            this.collectionName = collectionName;
        }

        public int getDeciedNumber() {
            return deciedNumber;
        }

        public void setDeciedNumber(int deciedNumber) {
            this.deciedNumber = deciedNumber;
        }

        public int getExerciseNumber() {
            return exerciseNumber;
        }

        public void setExerciseNumber(int exerciseNumber) {
            this.exerciseNumber = exerciseNumber;
        }

        public float getCollectionScore() {
            return collectionScore;
        }

        public void setCollectionScore(float collectionScore) {
            this.collectionScore = collectionScore;
        }

        public float getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(float totalScore) {
            this.totalScore = totalScore;
        }
    }

    class TeacherScoreData{
        private String collectionId;
        private String userNumber;
        private String userRealName;
        private int deciedNumber;   //学生答题数量
        private int exerciseNumber;   //题目集题目数量
        private float collectionScore;  //题目集得分
        private float totalScore;  //题目集总分

        public String getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(String collectionId) {
            this.collectionId = collectionId;
        }

        public String getUserNumber() {
            return userNumber;
        }

        public void setUserNumber(String userNumber) {
            this.userNumber = userNumber;
        }

        public String getUserRealName() {
            return userRealName;
        }

        public void setUserRealName(String userRealName) {
            this.userRealName = userRealName;
        }

        public int getDeciedNumber() {
            return deciedNumber;
        }

        public void setDeciedNumber(int deciedNumber) {
            this.deciedNumber = deciedNumber;
        }

        public int getExerciseNumber() {
            return exerciseNumber;
        }

        public void setExerciseNumber(int exerciseNumber) {
            this.exerciseNumber = exerciseNumber;
        }

        public float getCollectionScore() {
            return collectionScore;
        }

        public void setCollectionScore(float collectionScore) {
            this.collectionScore = collectionScore;
        }

        public float getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(float totalScore) {
            this.totalScore = totalScore;
        }
    }

    class ScoreDetail{
        private String exerciseName;
        private String exerciseLabel;   //题目标签
        private int exerciseType;    //题目类型
        private int times;  //提交次数
        private float rightScore;   //得分
        private float totalScore;   //总分

        public String getExerciseName() {
            return exerciseName;
        }

        public void setExerciseName(String exerciseName) {
            this.exerciseName = exerciseName;
        }

        public String getExerciseLabel() {
            return exerciseLabel;
        }

        public void setExerciseLabel(String exerciseLabel) {
            this.exerciseLabel = exerciseLabel;
        }

        public int getExerciseType() {
            return exerciseType;
        }

        public void setExerciseType(int exerciseType) {
            this.exerciseType = exerciseType;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public float getRightScore() {
            return rightScore;
        }

        public void setRightScore(float rightScore) {
            this.rightScore = rightScore;
        }

        public float getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(float totalScore) {
            this.totalScore = totalScore;
        }
    }

    class ScoreDetailUnion{
        private List<ScoreDetail> content;
        private long totalElements;

        public List<ScoreDetail> getContent() {
            return content;
        }

        public void setContent(List<ScoreDetail> content) {
            this.content = content;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        public String union(List<ScoreDetail> scoreDataList, int page, int size) {
            int length = scoreDataList.size();
            ScoreDetailUnion studentScoreDataUnion = new ScoreDetailUnion();
            studentScoreDataUnion.setTotalElements(length);
            List<ScoreDetail> studentScoreDataList = new ArrayList<>();
            //当前数量少于一页数量
            if(length <= size) {
                studentScoreDataUnion.setContent(scoreDataList);
                return JSONObject.toJSONString(studentScoreDataUnion);
            } else {
                if(length >= (page*size)) {      //未达到最后一页
                    for(int i=(page-1)*size; i< page*size; i++) {
                        studentScoreDataList.add(scoreDataList.get(i));
                    }
                    studentScoreDataUnion.setContent(studentScoreDataList);
                    return JSONObject.toJSONString(studentScoreDataUnion);

                } else {
                    for(int i=(page-1)*size; i< length; i++) {
                        studentScoreDataList.add(scoreDataList.get(i));
                    }
                    studentScoreDataUnion.setContent(studentScoreDataList);
                    return JSONObject.toJSONString(studentScoreDataUnion);
                }
            }
        }
    }
    class StudentScoreDataUnion{
        private List<StudentScoreData> content;
        private long totalElements;

        public List<StudentScoreData> getContent() {
            return content;
        }

        public void setContent(List<StudentScoreData> content) {
            this.content = content;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        public String union(List<StudentScoreData> scoreDataList, int page, int size) {
            int length = scoreDataList.size();
            StudentScoreDataUnion studentScoreDataUnion = new StudentScoreDataUnion();
            studentScoreDataUnion.setTotalElements(length);
            List<StudentScoreData> studentScoreDataList = new ArrayList<>();
            //当前数量少于一页数量
            if(length <= size) {
                studentScoreDataUnion.setContent(scoreDataList);
                return JSONObject.toJSONString(studentScoreDataUnion);
            } else {
                if(length >= (page*size)) {      //未达到最后一页
                    for(int i=(page-1)*size; i< page*size; i++) {
                        studentScoreDataList.add(scoreDataList.get(i));
                    }
                    studentScoreDataUnion.setContent(studentScoreDataList);
                    return JSONObject.toJSONString(studentScoreDataUnion);

                } else {
                    for(int i=(page-1)*size; i< length; i++) {
                        studentScoreDataList.add(scoreDataList.get(i));
                    }
                    studentScoreDataUnion.setContent(studentScoreDataList);
                    return JSONObject.toJSONString(studentScoreDataUnion);
                }
            }
        }
    }

    class TeacherScoreDataUnion {
        private List<TeacherScoreData> content;
        private long totalElements;

        public String union(List<TeacherScoreData> scoreDataList, int page, int size) {
            int length = scoreDataList.size();
            TeacherScoreDataUnion teacherScoreDataUnion = new TeacherScoreDataUnion();
            teacherScoreDataUnion.setTotalElements(length);
            List<TeacherScoreData> teacherScoreDataList = new ArrayList<>();
            //当前数量少于一页数量
            if(length <= size) {
                teacherScoreDataUnion.setContent(scoreDataList);
                return JSONObject.toJSONString(teacherScoreDataUnion);
            } else {
                if(length >= (page*size)) {      //未达到最后一页
                    for(int i=(page-1)*size; i< page*size; i++) {
                        teacherScoreDataList.add(scoreDataList.get(i));
                    }
                    teacherScoreDataUnion.setContent(teacherScoreDataList);
                    return JSONObject.toJSONString(teacherScoreDataUnion);

                } else {
                    for(int i=(page-1)*size; i< length; i++) {
                        teacherScoreDataList.add(scoreDataList.get(i));
                    }
                    teacherScoreDataUnion.setContent(teacherScoreDataList);
                    return JSONObject.toJSONString(teacherScoreDataUnion);
                }
            }

        }

        public List<TeacherScoreData> getContent() {
            return content;
        }

        public void setContent(List<TeacherScoreData> content) {
            this.content = content;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }
    }
}
