-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: student_education
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `class_teacher`
--

DROP TABLE IF EXISTS `class_teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class_teacher` (
  `class_teacher_id` varchar(50) NOT NULL COMMENT '班级教师编号',
  `user_id` varchar(50) DEFAULT NULL COMMENT '教师编号',
  `class_id` varchar(50) DEFAULT NULL COMMENT '班级编号',
  PRIMARY KEY (`class_teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级教师表描述的是教师和班级之间的关系。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_teacher`
--

LOCK TABLES `class_teacher` WRITE;
/*!40000 ALTER TABLE `class_teacher` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collection_exercise`
--

DROP TABLE IF EXISTS `collection_exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection_exercise` (
  `collection_exercise_id` varchar(50) NOT NULL COMMENT '题目集题目编号',
  `collection_id` varchar(50) DEFAULT NULL COMMENT '题目集编号',
  `exercise_id` varchar(50) DEFAULT NULL COMMENT '题目编号',
  PRIMARY KEY (`collection_exercise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目集题目表是描述题目集表和题目表之间的关系。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection_exercise`
--

LOCK TABLES `collection_exercise` WRITE;
/*!40000 ALTER TABLE `collection_exercise` DISABLE KEYS */;
/*!40000 ALTER TABLE `collection_exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compile_error_label`
--

DROP TABLE IF EXISTS `compile_error_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compile_error_label` (
  `compile_error_label_id` varchar(50) NOT NULL COMMENT '编译标签编号',
  `compile_error_label_name` varchar(50) NOT NULL COMMENT '标签名称，唯一',
  `compile_error_label_value` varchar(50) NOT NULL COMMENT '标签值，也就是名称对应的英文，唯一',
  `compile_error_label_language` varchar(50) DEFAULT NULL COMMENT '标签语言种类，说明那种语言的标签',
  `compile_error_label_description` varchar(200) DEFAULT NULL COMMENT '对标签进行描述',
  PRIMARY KEY (`compile_error_label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='编译错误都有对应的原因，把这些原因汇聚为一张表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compile_error_label`
--

LOCK TABLES `compile_error_label` WRITE;
/*!40000 ALTER TABLE `compile_error_label` DISABLE KEYS */;
/*!40000 ALTER TABLE `compile_error_label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_compile`
--

DROP TABLE IF EXISTS `exercise_compile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exercise_compile` (
  `exercise_compile_id` varchar(50) NOT NULL COMMENT '题目编译信息编号',
  `exercise_id` varchar(50) DEFAULT NULL COMMENT '题目编号',
  `user_id` varchar(50) DEFAULT NULL COMMENT '学生编号',
  `exercise_compile_content` text COMMENT '编译错误信息内容',
  `exercise_compile_time` datetime DEFAULT NULL COMMENT '日期',
  `exercise_compile_error_label` varchar(100) DEFAULT NULL COMMENT '编译错误原因标签，标签之间用空格隔开',
  PRIMARY KEY (`exercise_compile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目编译信息表描述的是学生那些做错题目的编译信息。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_compile`
--

LOCK TABLES `exercise_compile` WRITE;
/*!40000 ALTER TABLE `exercise_compile` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercise_compile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_label`
--

DROP TABLE IF EXISTS `exercise_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exercise_label` (
  `exercise_label_id` varchar(50) NOT NULL COMMENT '题目标签编号',
  `exercise_label_name` varchar(50) DEFAULT NULL COMMENT '标签名称，唯一',
  `exercise_label_name_value` varchar(50) DEFAULT NULL COMMENT '标签值，也就是名称对应的英文，唯一',
  `exercise_label_language` varchar(50) DEFAULT NULL COMMENT '标签语言种类，说明那种语言的标签',
  `exercise_label_description` varchar(200) DEFAULT NULL COMMENT '对标签进行描述',
  PRIMARY KEY (`exercise_label_id`),
  UNIQUE KEY `exercise_label_name_value_UNIQUE` (`exercise_label_name_value`),
  UNIQUE KEY `exercise_label_name_UNIQUE` (`exercise_label_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每到题目都有对应的知识点，把这些知识点做成一个标签库';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_label`
--

LOCK TABLES `exercise_label` WRITE;
/*!40000 ALTER TABLE `exercise_label` DISABLE KEYS */;
INSERT INTO `exercise_label` VALUES ('0369b718ae0811e9aa592c56dc0a6a5b','break','break','C','循环内结束，或搭配switch使用'),('0a0f2925ae0811e9aa592c56dc0a6a5b','地址','address','C','传值与传址'),('129110f6ae0811e9aa592c56dc0a6a5b','矩阵','matrix','C','矩阵问题'),('17c6f4ddae0811e9aa592c56dc0a6a5b','打印图形','print_graph','C','打印图形，例如：杨辉三角、乘法表'),('1d0ee70eae0811e9aa592c56dc0a6a5b','方程组','equation set','C','数学问题，找方程的解'),('212b7b76ae0911e9aa592c56dc0a6a5b','字符串','string','C','字符串反转、替换，字母大小写转换'),('2207558cae0811e9aa592c56dc0a6a5b','链表合并','union_link_list','C','多链表的合并'),('281cbfb8ae0811e9aa592c56dc0a6a5b','数组合并','union_array','C','多个数组间的合并'),('2dcff0e3ae0811e9aa592c56dc0a6a5b','动态数组','dynamic_arrays','C','数组动态分配问题'),('336b6052ae0811e9aa592c56dc0a6a5b','数组','array','C','数组查找排序删除等问题'),('386c70e2ae0811e9aa592c56dc0a6a5b','指针','pointer','C','一维指针和多为指针'),('3eb6964fae0811e9aa592c56dc0a6a5b','链表','link_list','C','链表的使用，包括增删改查'),('444b1cc1ae0811e9aa592c56dc0a6a5b','static','static','C','static静态变量使用问题'),('49d7e1abae0811e9aa592c56dc0a6a5b','随机数','random','C','随机产生一个数'),('4ea1c127ae0811e9aa592c56dc0a6a5b','进制转换','conversion_number_systems','C','二、八、十、十六进制转换'),('5339c3b5ae0811e9aa592c56dc0a6a5b','switch语句','switch','C','switch选择'),('5878e52fae0811e9aa592c56dc0a6a5b','do while循环','do_while_loop','C','do while 循环问题'),('5e6a7179ae0811e9aa592c56dc0a6a5b','for循环','for_loop','C','for循环问题'),('632d38c5ae0811e9aa592c56dc0a6a5b','排序','sort','C','将一个数组进行排序，经典算法有冒泡排序、快速排序、希尔排序'),('67eec8eaae0811e9aa592c56dc0a6a5b','查找','search','C','在一个数组中查找一个数，经典算法有二分查找'),('9a557f30ae0c11e9aa592c56dc0a6a5b','递归','recursion','C','使用递归算法求解'),('eb9975e7ae0711e9aa592c56dc0a6a5b','经典题目','classic_question','C','约瑟夫问题，求鞍点，哥德巴赫猜想'),('f22a1a3aae0711e9aa592c56dc0a6a5b','求和','summation','C','求和问题'),('f8c56765ae0711e9aa592c56dc0a6a5b','判断','judgement','C','例如：判断质数、判断回文、判断闰年'),('fe6fb195ae0711e9aa592c56dc0a6a5b','continue','continue','C','循环跳过');
/*!40000 ALTER TABLE `exercise_label` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise_score`
--

DROP TABLE IF EXISTS `exercise_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exercise_score` (
  `exercise_score_id` varchar(50) NOT NULL COMMENT '题目成绩编号',
  `user_id` varchar(50) DEFAULT NULL COMMENT '学生编号',
  `exercise_id` varchar(50) DEFAULT NULL COMMENT '题目编号',
  `collection_id` varchar(50) DEFAULT NULL COMMENT '题目集编号',
  `exercise_score_auto_grade` float DEFAULT NULL COMMENT '自动评分分数',
  `exercise_score_times` int(11) DEFAULT NULL COMMENT '提交次数',
  `exercise_score_manual_grade` float DEFAULT NULL COMMENT '手动评分分数',
  `user_name` varchar(30) DEFAULT NULL COMMENT '题目批改人账户',
  PRIMARY KEY (`exercise_score_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目成绩表描述的是学生做完某道题目后，该题得分情况的表。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise_score`
--

LOCK TABLES `exercise_score` WRITE;
/*!40000 ALTER TABLE `exercise_score` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercise_score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permis`
--

DROP TABLE IF EXISTS `role_permis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permis` (
  `role_permis_id` varchar(50) NOT NULL COMMENT '角色权限编号',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色编号',
  `permis_id` varchar(50) DEFAULT NULL COMMENT '权限编号',
  `role_permis_state` int(11) DEFAULT '1' COMMENT '用来描述状态，0表示未激活，1表示激活,默认为1',
  PRIMARY KEY (`role_permis_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表是角色表与权限表相关联而生成的一个表，一个角色可以有多种权限，一种权限能被多个角色所拥有。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permis`
--

LOCK TABLES `role_permis` WRITE;
/*!40000 ALTER TABLE `role_permis` DISABLE KEYS */;
INSERT INTO `role_permis` VALUES ('30a22cf8a77511e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','04712732a77211e9a33c2c56dc0a6a5b',1),('61a2c3a3a77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','ff518bd0a76f11e9a33c2c56dc0a6a5b',1),('70f53434ae0111e9aa592c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','72a68d49ade111e9aa592c56dc0a6a5b',1),('78b36674ae7311e9aa592c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','16c7dcb4ae7311e9aa592c56dc0a6a5b',1),('790d75a2ae0111e9aa592c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','dab0df57ade111e9aa592c56dc0a6a5b',1),('79d24f5da77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','e4847253a77111e9a33c2c56dc0a6a5b',1),('7ea83222ae0111e9aa592c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','d0ff10ebade111e9aa592c56dc0a6a5b',1),('81ce77aea77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','e1d5904ea76e11e9a33c2c56dc0a6a5b',1),('822b4985aadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','b1d21a81aa3e11e9a33c2c56dc0a6a5b',1),('8624678da77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','dc282388a76f11e9a33c2c56dc0a6a5b',1),('8f3d7085a77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','c838938da77111e9a33c2c56dc0a6a5b',1),('948f97b6a77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','b33a3081a77011e9a33c2c56dc0a6a5b',1),('9540fc25aadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','e1d5904ea76e11e9a33c2c56dc0a6a5b',1),('9a6a4dcfaadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','ff518bd0a76f11e9a33c2c56dc0a6a5b',1),('9be8959fa77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','b2f0e825a76f11e9a33c2c56dc0a6a5b',1),('9f0e1915aadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','04712732a77211e9a33c2c56dc0a6a5b',1),('a3c39d2baadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','b2f0e825a76f11e9a33c2c56dc0a6a5b',1),('a56cdeada77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','ab238184a77111e9a33c2c56dc0a6a5b',1),('a8afeeb6aadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','29b3b707a77211e9a33c2c56dc0a6a5b',1),('acb8341aa77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','9e691fe7a77311e9a33c2c56dc0a6a5b',1),('ae163ba3aadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','2cf3e8f4a77111e9a33c2c56dc0a6a5b',1),('b1a5a8dba77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','786e4cfda77311e9a33c2c56dc0a6a5b',1),('b2a018dfaadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','734226f4a77011e9a33c2c56dc0a6a5b',1),('b7cbe2c5a77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','734226f4a77011e9a33c2c56dc0a6a5b',1),('b968ca25aadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','b33a3081a77011e9a33c2c56dc0a6a5b',1),('be287400aadd11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b','dc282388a76f11e9a33c2c56dc0a6a5b',1),('be9644b7a77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','6ac4787aa77111e9a33c2c56dc0a6a5b',1),('c50cbf47a77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','655323c7a77211e9a33c2c56dc0a6a5b',1),('cc27ce1fa77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','4e12824ca77311e9a33c2c56dc0a6a5b',1),('d23eeed5a77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','2d654ca2a77311e9a33c2c56dc0a6a5b',1),('d791e286a77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','2cf3e8f4a77111e9a33c2c56dc0a6a5b',1),('dc65d06ea77411e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','29b3b707a77211e9a33c2c56dc0a6a5b',1),('fb251c5daa3e11e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b','b1d21a81aa3e11e9a33c2c56dc0a6a5b',1);
/*!40000 ALTER TABLE `role_permis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_class`
--

DROP TABLE IF EXISTS `sys_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_class` (
  `class_id` varchar(50) NOT NULL COMMENT '班级编号',
  `class_number` varchar(20) DEFAULT NULL COMMENT '班级',
  `class_people` int(11) DEFAULT NULL COMMENT '班级人数',
  `class_grade` int(11) DEFAULT NULL COMMENT '班级所处年级',
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表描述的是班级信息。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_class`
--

LOCK TABLES `sys_class` WRITE;
/*!40000 ALTER TABLE `sys_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_collection`
--

DROP TABLE IF EXISTS `sys_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_collection` (
  `collection_id` varchar(50) NOT NULL COMMENT '题目集编号',
  `course_id` varchar(50) DEFAULT NULL COMMENT '课程编号',
  `collection_name` varchar(50) DEFAULT NULL COMMENT '题目集名称',
  `collection_rate` float DEFAULT NULL COMMENT '该题目集在整个课程中的比重',
  `collection_start_time` datetime DEFAULT NULL COMMENT '题目集开始时间',
  `collection_end_time` datetime DEFAULT NULL COMMENT '题目集结束时间',
  PRIMARY KEY (`collection_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目集表示描述学生某门课程的某次实验，例如：C语言程序设计第一次实验；一门课程有多次实验，一次实验有多个题目。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_collection`
--

LOCK TABLES `sys_collection` WRITE;
/*!40000 ALTER TABLE `sys_collection` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_course`
--

DROP TABLE IF EXISTS `sys_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_course` (
  `course_id` varchar(50) NOT NULL COMMENT '课程编号',
  `course_name` varchar(30) DEFAULT NULL COMMENT '课程名称,可以相同',
  `course_type` int(11) DEFAULT '1' COMMENT '课程类型，1表示课程，2表示比赛，3表示其他，默认为1',
  `course_class` varchar(200) DEFAULT NULL COMMENT '所创课程面向的班级，班级间用空格分开',
  `course_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `course_create_user_name` varchar(30) DEFAULT NULL COMMENT '创建人账户名称',
  `course_language` varchar(30) DEFAULT NULL COMMENT '课程语言，例如：C、Java、Python',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='课程表是用来描述学生课程的一些信息。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_course`
--

LOCK TABLES `sys_course` WRITE;
/*!40000 ALTER TABLE `sys_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_exercise`
--

DROP TABLE IF EXISTS `sys_exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_exercise` (
  `exercise_id` varchar(50) NOT NULL COMMENT '题目编号',
  `user_id` varchar(50) DEFAULT NULL COMMENT '上传题目人编号',
  `exercise_name` varchar(200) DEFAULT NULL COMMENT '题目名称',
  `exercise_time` datetime DEFAULT NULL COMMENT '题目上传时间',
  `exercise_description` varchar(1000) DEFAULT NULL COMMENT '题目描述',
  `exercise_input_example` varchar(100) DEFAULT NULL COMMENT '输入样例',
  `exercise_output_example` varchar(100) DEFAULT NULL COMMENT '输出样例',
  `exercise_warning` varchar(100) DEFAULT NULL COMMENT '提示（警告）信息',
  `exercise_code` text COMMENT '参考代码',
  `exercise_score` float DEFAULT NULL COMMENT '题目分值',
  `exercise_file_name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `exercise_file_url` varchar(100) DEFAULT NULL COMMENT '文件存储路径',
  `exercise_state` int(11) DEFAULT '1' COMMENT '0表示未审核，1表示审核通过，默认为1',
  `exercise_classify_cause` int(11) DEFAULT '1' COMMENT '题目分类依据，1表示按照题目标签，2表示按照章节，3表示其他，默认为1',
  `exercise_label` varchar(50) DEFAULT NULL COMMENT '题目标签，多个标签用空格隔开',
  `exercise_difficult_value` float DEFAULT NULL COMMENT '难度系数，值为1-5',
  `exercise_type` int(11) DEFAULT '1' COMMENT '1表示编程题，2表示选择题，3表示判断题、4表示填空题，默认为1',
  `exercise_status` int(11) DEFAULT '1' COMMENT '0表示不可见，1表示可见，默认为1.当删除时，才将状态设置为0',
  PRIMARY KEY (`exercise_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='题目表是用来描述实验题目的信息。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_exercise`
--

LOCK TABLES `sys_exercise` WRITE;
/*!40000 ALTER TABLE `sys_exercise` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_log` (
  `log_id` varchar(50) NOT NULL COMMENT '日志编号',
  `log_name` varchar(50) DEFAULT NULL COMMENT '日志名称',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户账号名称',
  `log_create_time` datetime DEFAULT NULL COMMENT '操作开始时间',
  `log_params` varchar(200) DEFAULT NULL COMMENT '传递参数',
  `log_method` varchar(100) DEFAULT NULL COMMENT '操作方法',
  `log_ip` varchar(50) DEFAULT '操作电脑ip地址',
  `log_url` varchar(100) DEFAULT '请求路径',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表是用来记录用户操作的日志信息。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` VALUES ('402809816c1d6a7b016c1d9a506a002d','查询日志信息','xiaaman','2019-07-23 14:52:28','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1d6a7b016c1d9a6ee4002e','查询日志信息','xiaaman','2019-07-23 14:52:35','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1d6a7b016c1d9bfeb4002f','查询日志信息','xiaaman','2019-07-23 14:54:18','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1d6a7b016c1d9c3d330030','用户登录','xiaaman','2019-07-23 14:54:34','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563861660364,\"id\":\"042ECFAA71CA266212405A92E5D1AB60\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('402809816c1d6a7b016c1d9c45350031','查询日志信息','xiaaman','2019-07-23 14:54:36','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1d6a7b016c1d9d12840032','查询日志信息','xiaaman','2019-07-23 14:55:28','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1da86a080000','用户登录','xiaaman','2019-07-23 15:07:52','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563865671997,\"id\":\"A7F27A200142D0F4EE2BA86AE699979F\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('402809816c1da812016c1da88f360001','查询日志信息','xiaaman','2019-07-23 15:08:01','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1daa44620002','用户登录','tanlijuan','2019-07-23 15:09:53','[{\"userName\":\"tanlijuan\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563865671997,\"id\":\"A7F27A200142D0F4EE2BA86AE699979F\",\"lastAccessedTime\"...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('402809816c1da812016c1daa52ac0003','查询日志信息','tanlijuan','2019-07-23 15:09:57','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dac21150004','查询日志信息','tanlijuan','2019-07-23 15:11:55','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dac98320005','查询日志信息','tanlijuan','2019-07-23 15:12:26','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dad53eb0006','查询日志信息','tanlijuan','2019-07-23 15:13:14','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1daec2140007','查询日志信息','tanlijuan','2019-07-23 15:14:47','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1daef01b0008','查询日志信息','tanlijuan','2019-07-23 15:14:59','[2,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1daf06130009','查询日志信息','tanlijuan','2019-07-23 15:15:05','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1db8dfac000a','查询日志信息','tanlijuan','2019-07-23 15:25:50','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1db91518000b','查询日志信息','tanlijuan','2019-07-23 15:26:04','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1db9b4d1000c','查询日志信息','tanlijuan','2019-07-23 15:26:45','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dba6dbd000d','查询日志信息','tanlijuan','2019-07-23 15:27:32','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dbf3218000e','查询日志信息','tanlijuan','2019-07-23 15:32:45','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dbf91fe000f','查询日志信息','tanlijuan','2019-07-23 15:33:09','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc22dbe0010','查询日志信息','tanlijuan','2019-07-23 15:36:00','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc366cf0011','查询日志信息','tanlijuan','2019-07-23 15:37:20','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc3cfaf0012','查询日志信息','tanlijuan','2019-07-23 15:37:47','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc40dce0013','查询日志信息','tanlijuan','2019-07-23 15:38:03','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc658300014','查询日志信息','tanlijuan','2019-07-23 15:40:33','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc6d2da0015','查询日志信息','tanlijuan','2019-07-23 15:41:05','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc73a2e0016','查询日志信息','tanlijuan','2019-07-23 15:41:31','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc7d6d40017','查询日志信息','tanlijuan','2019-07-23 15:42:11','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc88d240018','查询日志信息','tanlijuan','2019-07-23 15:42:58','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dc9e4210019','查询日志信息','tanlijuan','2019-07-23 15:44:26','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dca16e5001a','查询日志信息','tanlijuan','2019-07-23 15:44:39','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1dca55f5001b','查询日志信息','tanlijuan','2019-07-23 15:44:55','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e2e474d001c','用户登录','xiaaman','2019-07-23 17:34:05','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563874428780,\"id\":\"2252E291677FDEAE67EEB7BC356E157E\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('402809816c1da812016c1e2e4e8d001d','查询日志信息','xiaaman','2019-07-23 17:34:06','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e2f4628001e','查询日志信息','xiaaman','2019-07-23 17:35:10','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e2fe51c001f','查询日志信息','xiaaman','2019-07-23 17:35:51','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e30397e0020','查询日志信息','xiaaman','2019-07-23 17:36:12','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e30a0be0021','查询日志信息','xiaaman','2019-07-23 17:36:39','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e30e1c20022','查询日志信息','xiaaman','2019-07-23 17:36:55','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e34b02f0023','查询日志信息','xiaaman','2019-07-23 17:41:05','[2,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e34b37e0024','查询日志信息','xiaaman','2019-07-23 17:41:06','[4,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e34b8500025','查询日志信息','xiaaman','2019-07-23 17:41:07','[5,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e34c97a0026','查询日志信息','xiaaman','2019-07-23 17:41:11','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e355cf30027','查询日志信息','xiaaman','2019-07-23 17:41:49','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e3907ff0028','查询日志信息','xiaaman','2019-07-23 17:45:49','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e3a66e40029','查询日志信息','xiaaman','2019-07-23 17:47:19','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e3dad6c002a','查询日志信息','xiaaman','2019-07-23 17:50:54','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e3eb09a002b','查询日志信息','xiaaman','2019-07-23 17:52:00','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e3fe765002c','查询日志信息','xiaaman','2019-07-23 17:53:20','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e4139f3002d','查询日志信息','xiaaman','2019-07-23 17:54:46','[3,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e413c9c002e','查询日志信息','xiaaman','2019-07-23 17:54:47','[5,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e414016002f','查询日志信息','xiaaman','2019-07-23 17:54:48','[6,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e414c500030','查询日志信息','xiaaman','2019-07-23 17:54:51','[6,20]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e414d7d0031','查询日志信息','xiaaman','2019-07-23 17:54:51','[3,20]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e4157df0032','查询日志信息','xiaaman','2019-07-23 17:54:54','[1,20]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e41d5d60033','查询日志信息','xiaaman','2019-07-23 17:55:26','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e4232d30034','查询日志信息','xiaaman','2019-07-23 17:55:50','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e4386b90035','查询日志信息','xiaaman','2019-07-23 17:57:17','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e43f5170036','查询日志信息','xiaaman','2019-07-23 17:57:45','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e4661da0037','查询日志信息','xiaaman','2019-07-23 18:00:24','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e4734db0038','查询日志信息','xiaaman','2019-07-23 18:01:18','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e4914800039','查询日志信息','xiaaman','2019-07-23 18:03:21','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e502dfe003a','查询日志信息','xiaaman','2019-07-23 18:11:06','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e52025e003b','查询日志信息','xiaaman','2019-07-23 18:13:06','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e532aaf003c','查询日志信息','xiaaman','2019-07-23 18:14:22','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e53d061003d','查询日志信息','xiaaman','2019-07-23 18:15:05','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e54c554003e','查询日志信息','xiaaman','2019-07-23 18:16:07','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e55c69c003f','查询日志信息','xiaaman','2019-07-23 18:17:13','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e56e5cd0040','查询日志信息','xiaaman','2019-07-23 18:18:27','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e574e260041','查询日志信息','xiaaman','2019-07-23 18:18:53','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e581b900042','查询日志信息','xiaaman','2019-07-23 18:19:46','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e5f62aa0043','查询日志信息','xiaaman','2019-07-23 18:27:43','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e6013b10044','查询日志信息','xiaaman','2019-07-23 18:28:28','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e6115460045','查询日志信息','xiaaman','2019-07-23 18:29:34','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e6150e50046','查询日志信息','xiaaman','2019-07-23 18:29:49','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e6266900047','查询日志信息','xiaaman','2019-07-23 18:31:01','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e66315c0048','查询日志信息','xiaaman','2019-07-23 18:35:09','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e66cdc10049','查询日志信息','xiaaman','2019-07-23 18:35:49','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e67b09a004a','查询日志信息','xiaaman','2019-07-23 18:36:47','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e67f9cf004b','查询日志信息','xiaaman','2019-07-23 18:37:06','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e683438004c','查询日志信息','xiaaman','2019-07-23 18:37:21','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1e6853a3004d','查询日志信息','xiaaman','2019-07-23 18:37:29','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1ea4a46e004e','用户登录','xiaaman','2019-07-23 19:43:22','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563882189717,\"id\":\"AD08D71F3438A272109A598B7CA35EF9\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('402809816c1da812016c1ea4ab8d004f','查询日志信息','xiaaman','2019-07-23 19:43:24','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1ea53de00050','查询日志信息','xiaaman','2019-07-23 19:44:01','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1ea5d5560051','查询日志信息','xiaaman','2019-07-23 19:44:40','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c1da812016c1ea608200052','查询日志信息','xiaaman','2019-07-23 19:44:53','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('402809816c234e52016c234e995b0000','用户登录','xiaaman','2019-07-24 17:27:29','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563960448939,\"id\":\"1078AE9D967328E9E2DCD96D32413663\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('402809816c23a268016c23a30a6a0000','用户登录','xiaaman','2019-07-24 18:59:43','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563965982866,\"id\":\"DCD8F58E695623174DE08CBF20BC14D0\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('4028ab466c1f63be016c1f8dbb520000','用户登录','xiaaman','2019-07-23 23:57:57','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563897477380,\"id\":\"74AD66B37C77C14243D57A5BD9CCAC19\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('4028ab466c1f63be016c1f9c4faf0001','用户登录','tanlijuan','2019-07-24 00:13:53','[{\"userName\":\"tanlijuan\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563898433414,\"id\":\"7332B991A2F01BACFFC9D176BB6250A4\",\"lastAccessedTime\"...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('4028ab466c1f63be016c1f9d78960002','用户登录','tanlijuan','2019-07-24 00:15:09','[{\"userName\":\"tanlijuan\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563898509438,\"id\":\"AAEA16E1E0A9A870CF6CE93C9728C13A\",\"lastAccessedTime\"...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('4028ab466c1f63be016c1f9e67c00003','用户登录','夏阿蛮','2019-07-24 00:16:10','[{\"userName\":\"夏阿蛮\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563897477380,\"id\":\"74AD66B37C77C14243D57A5BD9CCAC19\",\"lastAccessedTime\":15638...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('4028ab466c1f63be016c1f9eda550004','用户登录','xiaaman','2019-07-24 00:16:40','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563897477380,\"id\":\"74AD66B37C77C14243D57A5BD9CCAC19\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('4028ab466c1fb73b016c1fb83fc30000','用户登录','xiaaman','2019-07-24 00:44:24','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563900264023,\"id\":\"4B0A22236A6A27EE7530F1E95620AB25\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('4028ab466c1fb73b016c1fb8492c0001','查询日志信息','xiaaman','2019-07-24 00:44:26','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('4028ab466c1fb73b016c1fb8deef0002','查询日志信息','xiaaman','2019-07-24 00:45:05','[2,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('4028ab466c1fb73b016c1fb8e83f0003','查询日志信息','xiaaman','2019-07-24 00:45:07','[10,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('4028ab466c1fb73b016c1fb8f6770004','查询日志信息','xiaaman','2019-07-24 00:45:11','[5,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c215c96016c215da5c50000','用户登录','xiaaman','2019-07-24 08:24:41','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563927880863,\"id\":\"ED5AD7A94790BC06F3F1056C1D0F0617\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c215c96016c215dae770001','查询日志信息','xiaaman','2019-07-24 08:24:43','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c215c96016c216090a70002','查询日志信息','xiaaman','2019-07-24 08:27:52','[2,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2167e8016c21681d370000','用户登录','xiaaman','2019-07-24 08:36:07','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563928566851,\"id\":\"5E9433478E322E79BCA8B83B5CE0E093\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2167e8016c2168360d0001','查询日志信息','xiaaman','2019-07-24 08:36:13','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c216d0f016c216e97290000','用户登录','xiaaman','2019-07-24 08:43:11','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563928991306,\"id\":\"8CE58106D4C771658CC592782A5DB503\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c216d0f016c2255684d0001','用户登录','xiaaman','2019-07-24 12:55:18','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563928991306,\"id\":\"8CE58106D4C771658CC592782A5DB503\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c216d0f016c22559db50002','查询日志信息','xiaaman','2019-07-24 12:55:32','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c216d0f016c22a5845c0003','用户登录','tanlijuan','2019-07-24 14:22:48','[{\"userName\":\"tanlijuan\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563928991306,\"id\":\"8CE58106D4C771658CC592782A5DB503\",\"lastAccessedTime\"...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c216d0f016c22bb677e0004','用户登录','xiaaman','2019-07-24 14:46:42','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563928991306,\"id\":\"8CE58106D4C771658CC592782A5DB503\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c216d0f016c22bb7b8e0005','查询日志信息','xiaaman','2019-07-24 14:46:47','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c216d0f016c22c616dc0006','查询日志信息','xiaaman','2019-07-24 14:58:23','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c216d0f016c22c6b7820007','查询日志信息','xiaaman','2019-07-24 14:59:04','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c216d0f016c22c8d56e0008','查询日志信息','xiaaman','2019-07-24 15:01:22','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c216d0f016c22d6f4650009','查询日志信息','xiaaman','2019-07-24 15:16:48','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c216d0f016c22d77b19000a','用户登录','tanlijuan','2019-07-24 15:17:22','[{\"userName\":\"tanlijuan\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563928991306,\"id\":\"8CE58106D4C771658CC592782A5DB503\",\"lastAccessedTime\"...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c216d0f016c22d93529000b','查询日志信息','tanlijuan','2019-07-24 15:19:16','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c22f2a8016c22f3ea1c0000','用户登录','xiaaman','2019-07-24 15:48:26','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563954466392,\"id\":\"936C68CEA4D7D750759551F70403949E\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c22f910016c22f9900e0000','用户登录','tanlijuan','2019-07-24 15:54:36','[{\"userName\":\"tanlijuan\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563954876036,\"id\":\"B3080BE223EAF2022F5D94C45485EF44\",\"lastAccessedTime\"...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c22fc8d016c22fd21d10000','用户登录','tanlijuan','2019-07-24 15:58:30','[{\"userName\":\"tanlijuan\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955109972,\"id\":\"E8384F6F269F6788A4DDA83A81946277\",\"lastAccessedTime\"...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c23041e930000','用户登录','xiaaman','2019-07-24 16:06:08','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c23050dbe0001','用户登录','xiaaman','2019-07-24 16:07:09','[{\"userName\":\"123\",\"userPassword\":\"6c14da109e294d1e8155be8aa4b1ce8e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":15639...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c2305645f0002','用户登录','xiaaman','2019-07-24 16:07:31','[{\"userName\":\"123\",\"userPassword\":\"6c14da109e294d1e8155be8aa4b1ce8e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":15639...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c2306b7410003','用户登录','xiaaman','2019-07-24 16:08:58','[{\"userName\":\"123\",\"userPassword\":\"202cb962ac59075b964b07152d234b70\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":15639...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c230740b30004','用户登录','xiaaman','2019-07-24 16:09:33','[{\"userName\":\"123\",\"userPassword\":\"202cb962ac59075b964b07152d234b70\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":15639...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c23090dc30005','用户登录','xiaaman','2019-07-24 16:11:31','[{\"userName\":\"123\",\"userPassword\":\"202cb962ac59075b964b07152d234b70\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":15639...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c230935280006','用户登录','xiaaman','2019-07-24 16:11:41','[{\"userName\":\"123\",\"userPassword\":\"202cb962ac59075b964b07152d234b70\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":15639...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c23098c1b0007','用户登录','xiaaman','2019-07-24 16:12:03','[{\"userName\":\"123\",\"userPassword\":\"202cb962ac59075b964b07152d234b70\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":15639...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c2309b2110008','用户登录','xiaaman','2019-07-24 16:12:13','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c230a7b1c0009','用户登录','xiaaman','2019-07-24 16:13:05','[{\"userName\":\"1231\",\"userPassword\":\"37693cfc748049e45d87b8c7d8b9aacd\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":1563...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c230a9e46000a','用户登录','xiaaman','2019-07-24 16:13:14','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c231679f6000b','用户登录','xiaaman','2019-07-24 16:26:11','[{\"userName\":\"123\",\"userPassword\":\"202cb962ac59075b964b07152d234b70\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":15639...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c2316a0fe000c','用户登录','xiaaman','2019-07-24 16:26:21','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c231a2481000d','用户登录','xiaaman','2019-07-24 16:30:11','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c231de25a000e','用户登录','xiaaman','2019-07-24 16:34:16','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c231ea77c000f','用户登录','xiaaman','2019-07-24 16:35:07','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c23208ff10010','用户登录','xiaaman','2019-07-24 16:37:12','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1563955567826,\"id\":\"832CF281753B2E2232E34B694DD5D6B4\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c2303cf016c2320fa110011','查询日志信息','xiaaman','2019-07-24 16:37:39','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c232108290012','查询日志信息','xiaaman','2019-07-24 16:37:43','[2,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c23211e760013','查询日志信息','xiaaman','2019-07-24 16:37:48','[3,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c2321271e0014','查询日志信息','xiaaman','2019-07-24 16:37:51','[8,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c2321335e0015','查询日志信息','xiaaman','2019-07-24 16:37:54','[8,100]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c232133690016','查询日志信息','xiaaman','2019-07-24 16:37:54','[2,100]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c232148a10017','查询日志信息','xiaaman','2019-07-24 16:37:59','[1,100]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c2321701e0018','查询日志信息','xiaaman','2019-07-24 16:38:09','[2,100]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c23218fb80019','查询日志信息','xiaaman','2019-07-24 16:38:17','[1,100]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c2321dc29001a','查询日志信息','xiaaman','2019-07-24 16:38:37','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c2303cf016c2321f331001b','查询日志信息','xiaaman','2019-07-24 16:38:43','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c26758a016c267630820000','用户登录','xiaaman','2019-07-25 08:09:35','[{\"userName\":\"xiaaman\",\"userPassword\":\"e10adc3949ba59abbe56e057f20f883e\"},{\"attributeNames\":[\"user\"],\"creationTime\":1564013375107,\"id\":\"D4E94836B7B2D207C0976621E8912C7B\",\"lastAccessedTime\":1...]','com.nchu.xiaaman.student_education.controller.LoginController.login','127.0.0.1','http://localhost:8848/login'),('8a8d840a6c26758a016c267662090001','查询日志信息','xiaaman','2019-07-25 08:09:48','[1,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get'),('8a8d840a6c26758a016c26817c6b0002','查询日志信息','xiaaman','2019-07-25 08:21:55','[2,10]','com.nchu.xiaaman.student_education.controller.SysLogController.getLogList','127.0.0.1','http://localhost:8848/log/get');
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_permis`
--

DROP TABLE IF EXISTS `sys_permis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_permis` (
  `permis_id` varchar(50) NOT NULL COMMENT '权限编号',
  `permis_parent_id` varchar(50) DEFAULT NULL COMMENT '父节点编号，‘0000’表示为根菜单',
  `permis_name` varchar(30) DEFAULT NULL COMMENT '权限名称',
  `permis_name_value` varchar(30) DEFAULT NULL COMMENT '权限值（不能中文）',
  `permis_description` varchar(100) DEFAULT NULL COMMENT '对权限进行描述',
  `permis_position` int(11) DEFAULT NULL COMMENT '该字段主要是对导航栏的一二级菜单，表示每个菜单的位置，对根菜单来说，0表示在最左边，数字递增表示从左往右。子菜单0表示最上面一个，数字递增表示从上到下。该数字不能代表在数组中的位置，数字一定不能重复。一级和二级菜单不能为空，其他等级菜单位置值可以为空',
  `permis_type` int(11) DEFAULT NULL COMMENT '表示为几级菜单',
  `permis_url` varchar(50) DEFAULT NULL COMMENT '页面url路径',
  `permis_icon` varchar(50) DEFAULT NULL COMMENT '菜单图标名称',
  PRIMARY KEY (`permis_id`),
  UNIQUE KEY `permis_name_value_UNIQUE` (`permis_name_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表描述的是各种权限，用户必须拥有某种权限才能进行相应的操作。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permis`
--

LOCK TABLES `sys_permis` WRITE;
/*!40000 ALTER TABLE `sys_permis` DISABLE KEYS */;
INSERT INTO `sys_permis` VALUES ('04712732a77211e9a33c2c56dc0a6a5b','0000','教学中心','education',NULL,3,1,'',NULL),('16c7dcb4ae7311e9aa592c56dc0a6a5b','ff518bd0a76f11e9a33c2c56dc0a6a5b','查看详情','exercises:management:detail',NULL,NULL,3,NULL,NULL),('29b3b707a77211e9a33c2c56dc0a6a5b','04712732a77211e9a33c2c56dc0a6a5b','查看成绩','education:student_score',NULL,0,2,'',NULL),('2cf3e8f4a77111e9a33c2c56dc0a6a5b','ff518bd0a76f11e9a33c2c56dc0a6a5b','学生贡献榜','exercises:student_rake',NULL,1,2,'',NULL),('2d654ca2a77311e9a33c2c56dc0a6a5b','04712732a77211e9a33c2c56dc0a6a5b','成绩数据统计','education:score_statistics',NULL,3,2,'',NULL),('4e12824ca77311e9a33c2c56dc0a6a5b','04712732a77211e9a33c2c56dc0a6a5b','导出学生成绩','education:export_score',NULL,2,2,'',NULL),('655323c7a77211e9a33c2c56dc0a6a5b','04712732a77211e9a33c2c56dc0a6a5b','批改作业','education:correct_exercise',NULL,1,2,'',NULL),('6ac4787aa77111e9a33c2c56dc0a6a5b','0000','用户管理','sys_users',NULL,4,1,'',NULL),('72a68d49ade111e9aa592c56dc0a6a5b','ff518bd0a76f11e9a33c2c56dc0a6a5b','上传题目','exercises:management:add',NULL,NULL,3,NULL,NULL),('734226f4a77011e9a33c2c56dc0a6a5b','ff518bd0a76f11e9a33c2c56dc0a6a5b','查看题库','exercises:management',NULL,0,2,'/home/lookExercise',NULL),('786e4cfda77311e9a33c2c56dc0a6a5b','0000','系统日志','sys_log',NULL,5,1,'',NULL),('9e691fe7a77311e9a33c2c56dc0a6a5b','786e4cfda77311e9a33c2c56dc0a6a5b','查看日志','sys_log:look',NULL,0,2,'/home/lookLog',NULL),('ab238184a77111e9a33c2c56dc0a6a5b','6ac4787aa77111e9a33c2c56dc0a6a5b','查看用户','sys_users:management',NULL,0,2,'',NULL),('b1d21a81aa3e11e9a33c2c56dc0a6a5b','0000','首页','home',NULL,0,1,'',NULL),('b2f0e825a76f11e9a33c2c56dc0a6a5b','e1d5904ea76e11e9a33c2c56dc0a6a5b','我的课程','experiment:course',NULL,0,2,'',NULL),('b33a3081a77011e9a33c2c56dc0a6a5b','ff518bd0a76f11e9a33c2c56dc0a6a5b','题目排行榜','exercises:rake',NULL,2,2,'',NULL),('c838938da77111e9a33c2c56dc0a6a5b','6ac4787aa77111e9a33c2c56dc0a6a5b','配置权限','sys_users:permis',NULL,2,2,'',NULL),('d0ff10ebade111e9aa592c56dc0a6a5b','ff518bd0a76f11e9a33c2c56dc0a6a5b','删除','exercises:management:delete',NULL,NULL,3,NULL,NULL),('dab0df57ade111e9aa592c56dc0a6a5b','ff518bd0a76f11e9a33c2c56dc0a6a5b','修改','exercises:management:modify',NULL,NULL,3,NULL,NULL),('dc282388a76f11e9a33c2c56dc0a6a5b','e1d5904ea76e11e9a33c2c56dc0a6a5b','题库练习','experiment:practice',NULL,1,2,'',NULL),('e1d5904ea76e11e9a33c2c56dc0a6a5b','0000','实验习题区','experiment',NULL,1,1,'',NULL),('e4847253a77111e9a33c2c56dc0a6a5b','6ac4787aa77111e9a33c2c56dc0a6a5b','角色管理','sys_users:roles',NULL,1,2,'',NULL),('ff518bd0a76f11e9a33c2c56dc0a6a5b','0000','题库中心','exercises',NULL,2,1,'',NULL);
/*!40000 ALTER TABLE `sys_permis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `role_id` varchar(50) NOT NULL COMMENT '角色编号',
  `role_name` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `role_name_value` varchar(30) DEFAULT NULL COMMENT '角色值（不能为中文）',
  `role_description` varchar(100) DEFAULT NULL COMMENT '对角色进行描述',
  `role_rank` int(11) DEFAULT NULL COMMENT '角色等级，角色等级低的不能对角色等级高的进行操作',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name_UNIQUE` (`role_name`),
  UNIQUE KEY `role_name_value_UNIQUE` (`role_name_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表是对角色进行说明，本系统拥有学生、教师、管理员、助教、超级管理员等角色。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('034f781ca76711e9a33c2c56dc0a6a5b','超级管理员','super_admin','拥有系统所有权限，主要方便前期开发人员进行测试',10),('2e6247eca76711e9a33c2c56dc0a6a5b','管理员','admin',NULL,9),('3aca1afaa76711e9a33c2c56dc0a6a5b','教师','teacher',NULL,8),('48ad3440a76711e9a33c2c56dc0a6a5b','助教','teacher_assistant',NULL,7),('6edc935aa76711e9a33c2c56dc0a6a5b','学生','student',NULL,6),('7d0b6f78a76711e9a33c2c56dc0a6a5b','散客学生','individual_student',NULL,5);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '用户编号',
  `user_name` varchar(30) DEFAULT NULL COMMENT '账号名称，唯一',
  `user_real_name` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `user_class` varchar(20) DEFAULT NULL COMMENT '班级',
  `user_number` varchar(20) DEFAULT NULL COMMENT '学号或工号,唯一',
  `user_phone` varchar(15) DEFAULT NULL COMMENT '手机号',
  `user_password` varchar(100) DEFAULT NULL COMMENT '密码',
  `user_email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `user_recommend_name` varchar(30) DEFAULT NULL COMMENT '推荐人账号名称，主要是对于老师创建助教账号',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `user_number_UNIQUE` (`user_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表是用来存储用户的信息，包括学生、管理员、教师等都属于用户。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('32f37d77a76611e9a33c2c56dc0a6a5b','xiaaman','夏传林',NULL,'','15870811824','e10adc3949ba59abbe56e057f20f883e','756585746@qq.com',NULL),('8589dbffa76611e9a33c2c56dc0a6a5b','tanlijuan','谭莉娟',NULL,NULL,'15970864586','e10adc3949ba59abbe56e057f20f883e','1404824745@qq.com',NULL),('95b2e41baadc11e9a33c2c56dc0a6a5b','夏阿蛮','夏阿蛮','152021','15202131','15870811824','e10adc3949ba59abbe56e057f20f883e','756585746@qq.com',NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_course`
--

DROP TABLE IF EXISTS `teacher_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher_course` (
  `teacher_course_id` varchar(50) NOT NULL COMMENT '授课编号',
  `user_id` varchar(50) DEFAULT NULL COMMENT '教师（用户）编号',
  `course_id` varchar(50) DEFAULT NULL COMMENT '课程编号',
  PRIMARY KEY (`teacher_course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授课表表述的是教师与课程的某种关系的表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_course`
--

LOCK TABLES `teacher_course` WRITE;
/*!40000 ALTER TABLE `teacher_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_role_id` varchar(50) NOT NULL COMMENT '用户角色编号',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表是用户表与角色表相关联而生成的一个表，一个用户可以有多种角色，一种角色能被多个用户所拥有。';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES ('0b07e299aae011e9a33c2c56dc0a6a5b','32f37d77a76611e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b'),('0eb2a2a9a76811e9a33c2c56dc0a6a5b','32f37d77a76611e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b'),('44c5f801a76811e9a33c2c56dc0a6a5b','8589dbffa76611e9a33c2c56dc0a6a5b','034f781ca76711e9a33c2c56dc0a6a5b'),('e0b4d567aadc11e9a33c2c56dc0a6a5b','95b2e41baadc11e9a33c2c56dc0a6a5b','6edc935aa76711e9a33c2c56dc0a6a5b');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-25  8:32:58
