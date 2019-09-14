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
        for(int i=0; i<courseList.size(); i++) {
            collectionId = courseCollectionService.getCollectionIdListByCourseId(courseList.get(i).getCourseId());
            for (int j=0; j<collectionId.size(); j++) {
                SysCollection collection = sysCollectionService.getByIdAndTime(collectionId.get(j), sdf.format(new Date()));
                if(collection != null) {
                    exerciseId = collectionExerciseService.getExerciseIdListByCollectionId(collection.getCollectionId());
                    for(int k=0; k<exerciseId.size(); k++) {
                        studentScoreData = new StudentScoreData();
                        studentScoreData.setCourseName(courseList.get(i).getCourseName());
                        studentScoreData.setCollectionName(collection.getCollectionName());
                        studentScoreData.setExerciseName(sysExerciseService.getById(exerciseId.get(k)).getExerciseName());
                        exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseId.get(k), user.getUserId(), collection.getCollectionId());
                        if(exerciseScore == null) {
                            //学生可能这道题没有提交，则默认0分
                            studentScoreData.setExerciseAutoScore(0);
                        } else {
                            studentScoreData.setExerciseAutoScore(exerciseScore.getExerciseScoreAutoGrade());
                        }
                        studentScoreData.setExerciseTotalScore(sysExerciseService.getById(exerciseId.get(k)).getExerciseScore());
                        studentScoreDataList.add(studentScoreData);
                    }
                }
            }
        }
        return new StudentScoreDataUnion().union(studentScoreDataList, page, size);

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
        for (int j=0; j<collectionId.size(); j++) {
            SysCollection collection = sysCollectionService.getByIdAndTime(collectionId.get(j), sdf.format(new Date()));
            if(collection != null) {
                exerciseId = collectionExerciseService.getExerciseIdListByCollectionId(collection.getCollectionId());
                for(int k=0; k<exerciseId.size(); k++) {
                    studentScoreData = new StudentScoreData();
                    studentScoreData.setCourseName(courseName);
                    studentScoreData.setCollectionName(collection.getCollectionName());
                    studentScoreData.setExerciseName(sysExerciseService.getById(exerciseId.get(k)).getExerciseName());
                    exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseId.get(k), user.getUserId(), collection.getCollectionId());
                    if(exerciseScore == null) {
                        //学生可能这道题没有提交，则默认0分
                        studentScoreData.setExerciseAutoScore(0);
                    } else {
                        studentScoreData.setExerciseAutoScore(exerciseScore.getExerciseScoreAutoGrade());
                    }
                    studentScoreData.setExerciseTotalScore(sysExerciseService.getById(exerciseId.get(k)).getExerciseScore());
                    studentScoreDataList.add(studentScoreData);
                }
            }
        }

        return new StudentScoreDataUnion().union(studentScoreDataList, page, size);
    }


    @MyLog(value = "教师查看成绩")
    @RequestMapping(value = "/teacherLook")
    public String lookScore(@RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "size",defaultValue = "10") int size,
                            @RequestParam(value = "classNumber") String classNumber,
                            @RequestParam(value = "courseName") String courseName) {
        //判断该班级是否有这门课程
        if(sysCourseService.getBycourseNameandClass(courseName, "%"+classNumber+"%") == null) {
            return "400";
        }

        //查询课程id
        String courseId = sysCourseService.getCourseIdByName(courseName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> collectionId = courseCollectionService.getCollectionIdListByCourseId(courseId);
        List<SysUser> userList = sysUserService.getUserListByClass(classNumber);
        List<SysCollection> sysCollectionList = new ArrayList<>();
        List<Float> collectionRate = new ArrayList<>();
        List<TeacherScoreData> teacherScoreDataList = new ArrayList<>();
        TeacherScoreData teacherScoreData;
        SysCollection sysCollection;
        ExerciseScore exerciseScore;
        float courseScore;
        float singleScore;
        float totalScore;
        float totalRate = 0;
        for(int i=0; i<collectionId.size(); i++) {
            sysCollection = sysCollectionService.getByIdAndTime(collectionId.get(i), sdf.format(new Date()));
            if(sysCollection != null) {
                collectionRate.add(sysCollection.getCollectionRate());
                totalRate += sysCollection.getCollectionRate();
                sysCollectionList.add(sysCollection);
            }
        }
        //获得所有已经结束的题目集

        for(int i=0; i<userList.size(); i++) {
            courseScore = 0;
            teacherScoreData = new TeacherScoreData();
            for(int j=0; j<sysCollectionList.size(); j++) {
                List<String> exerciseId = collectionExerciseService.getExerciseIdListByCollectionId(sysCollectionList.get(j).getCollectionId());
                singleScore = 0;     //每个题目集的得分总和
                totalScore = 0;     //每个题目集的总分
                for(int k=0; k<exerciseId.size(); k++) {
                    exerciseScore = exerciseScoreService.getByUserIdExerciseIdAndCollectionId(exerciseId.get(k), userList.get(i).getUserId(), collectionId.get(j));
                    if(exerciseScore == null) {
                        singleScore += 0;
                    } else {
                        singleScore += exerciseScore.getExerciseScoreAutoGrade();
                    }
                    totalScore += sysExerciseService.getById(exerciseId.get(k)).getExerciseScore();
                }
                courseScore += (singleScore/totalScore) * collectionRate.get(j);
            }
            if(collectionRate.size() > 0) {
                courseScore *= (100/totalRate);
            } else {
                courseScore = 0;
            }

            courseScore = Float.parseFloat(String.format("%.0f", courseScore));     //不保留小数
            teacherScoreData.setCourseName(courseName);
            teacherScoreData.setCourseScore(courseScore);
            teacherScoreData.setUserName(userList.get(i).getUserRealName());
            teacherScoreData.setUserClass(userList.get(i).getUserClass());
            teacherScoreData.setUserNumber(userList.get(i).getUserNumber());
            teacherScoreDataList.add(teacherScoreData);
        }
        return new TeacherScoreDataUnion().union(teacherScoreDataList, page, size);
    }
    //查询教师或管理员管理班级
    @RequestMapping(value = "/getClass")
    public String getClass(HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("user");
        String[] classId = classTeacherService.getClassIdListByTeacherId(user.getUserId());
        List<String> classNumber = new ArrayList<>();
        for(int i=0; i<classId.length; i++) {
            classNumber.add(sysClassService.getById(classId[i]).getClassNumber());
        }
        return JSONObject.toJSONString(classNumber);
    }

    //查询教师所授课程
    @RequestMapping(value = "/getCourse")
    public String getCourse(HttpSession session) {
        SysUser user = (SysUser) session.getAttribute("user");
        List<String> courseId = teacherCourseService.getCourseIdListByUserId(user.getUserId());
        List<String> sysCourseNameList = new ArrayList<>();
        for(int i=0; i<courseId.size(); i++) {
            sysCourseNameList.add(sysCourseService.getByCourseId(courseId.get(i)).getCourseName());
        }
        return JSONObject.toJSONString(sysCourseNameList);

    }

    //
    class StudentScoreData{
        private String courseName;
        private String collectionName;
        private String exerciseName;
        private float exerciseAutoScore;
        private float exerciseTotalScore;

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

        public String getExerciseName() {
            return exerciseName;
        }

        public void setExerciseName(String exerciseName) {
            this.exerciseName = exerciseName;
        }

        public float getExerciseAutoScore() {
            return exerciseAutoScore;
        }

        public void setExerciseAutoScore(float exerciseAutoScore) {
            this.exerciseAutoScore = exerciseAutoScore;
        }

        public float getExerciseTotalScore() {
            return exerciseTotalScore;
        }

        public void setExerciseTotalScore(float exerciseTotalScore) {
            this.exerciseTotalScore = exerciseTotalScore;
        }
    }

    class TeacherScoreData{
        private String courseName;
        private String userName;
        private String userNumber;
        private String userClass;
        private float courseScore;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserNumber() {
            return userNumber;
        }

        public void setUserNumber(String userNumber) {
            this.userNumber = userNumber;
        }

        public String getUserClass() {
            return userClass;
        }

        public void setUserClass(String userClass) {
            this.userClass = userClass;
        }

        public float getCourseScore() {
            return courseScore;
        }

        public void setCourseScore(float courseScore) {
            this.courseScore = courseScore;
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
