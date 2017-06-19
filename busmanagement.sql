-- MySQL dump 10.13  Distrib 5.5.49, for Win64 (x86)
--
-- Host: localhost    Database: busmanagement
-- ------------------------------------------------------
-- Server version	5.5.49

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
-- Table structure for table `bus`
--

DROP TABLE IF EXISTS `bus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bus` (
  `bnumber` int(11) NOT NULL,
  `bname` varchar(10) NOT NULL,
  PRIMARY KEY (`bnumber`,`bname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bus`
--

LOCK TABLES `bus` WRITE;
/*!40000 ALTER TABLE `bus` DISABLE KEYS */;
INSERT INTO `bus` VALUES (1,'1号线'),(2,'2号线'),(3,'3号线'),(4,'4号线'),(5,'5号线'),(6,'6号线'),(7,'7号线'),(8,'8号线'),(302,'3号线北延段');
/*!40000 ALTER TABLE `bus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sb`
--

DROP TABLE IF EXISTS `sb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sb` (
  `snumber` int(11) NOT NULL,
  `bnumber` int(11) NOT NULL,
  PRIMARY KEY (`snumber`,`bnumber`),
  KEY `FK_BNUMBER` (`bnumber`),
  CONSTRAINT `FK_BNUMBER` FOREIGN KEY (`bnumber`) REFERENCES `bus` (`bnumber`),
  CONSTRAINT `FK_SNUMBER` FOREIGN KEY (`snumber`) REFERENCES `station` (`snumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sb`
--

LOCK TABLES `sb` WRITE;
/*!40000 ALTER TABLE `sb` DISABLE KEYS */;
INSERT INTO `sb` VALUES (101,1),(102,1),(103,1),(104,1),(105,1),(106,1),(107,1),(108,1),(109,1),(110,1),(111,1),(112,1),(113,1),(114,1),(115,1),(116,1),(201,2),(202,2),(203,2),(204,2),(205,2),(206,2),(207,2),(208,2),(209,2),(210,2),(211,2),(212,2),(213,2),(214,2),(215,2),(216,2),(217,2),(218,2),(219,2),(220,2),(221,2),(222,2),(223,2),(224,2),(301,3),(302,3),(303,3),(304,3),(305,3),(306,3),(307,3),(308,3),(309,3),(310,3),(311,3),(312,3),(313,3),(314,3),(315,3),(316,3),(401,4),(402,4),(403,4),(405,4),(406,4),(407,4),(408,4),(409,4),(410,4),(411,4),(412,4),(413,4),(414,4),(415,4),(416,4),(417,4),(418,4),(501,5),(502,5),(503,5),(504,5),(505,5),(506,5),(507,5),(508,5),(509,5),(510,5),(511,5),(512,5),(513,5),(514,5),(515,5),(516,5),(517,5),(518,5),(519,5),(520,5),(521,5),(522,5),(523,5),(524,5),(601,6),(602,6),(603,6),(604,6),(605,6),(607,6),(608,6),(609,6),(610,6),(611,6),(612,6),(613,6),(614,6),(615,6),(616,6),(617,6),(618,6),(619,6),(620,6),(621,6),(622,6),(623,6),(624,6),(625,6),(626,6),(627,6),(628,6),(629,6),(630,6),(631,6),(632,6),(701,7),(702,7),(703,7),(704,7),(705,7),(706,7),(707,7),(708,7),(709,7),(801,8),(802,8),(803,8),(804,8),(805,8),(806,8),(807,8),(808,8),(809,8),(810,8),(811,8),(812,8),(813,8),(30201,302),(30202,302),(30203,302),(30204,302),(30205,302),(30206,302),(30207,302),(30208,302),(30209,302),(30210,302),(30211,302),(30212,302);
/*!40000 ALTER TABLE `sb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `station`
--

DROP TABLE IF EXISTS `station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `station` (
  `snumber` int(11) NOT NULL,
  `sname` varchar(20) NOT NULL,
  PRIMARY KEY (`snumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `station`
--

LOCK TABLES `station` WRITE;
/*!40000 ALTER TABLE `station` DISABLE KEYS */;
INSERT INTO `station` VALUES (101,'广州东站'),(102,'体育中心'),(103,'体育西路'),(104,'杨箕'),(105,'东山口'),(106,'烈士陵园'),(107,'农讲所'),(108,'公园前'),(109,'西门口'),(110,'陈家祠'),(111,'长寿路'),(112,'黄沙'),(113,'芳村'),(114,'花地湾'),(115,'坑口'),(116,'西朗'),(201,'嘉禾望岗'),(202,'黄边'),(203,'江夏'),(204,'萧岗'),(205,'白云文化广场'),(206,'白云公园'),(207,'飞翔公园'),(208,'三元里'),(209,'广州火车站'),(210,'越秀公园'),(211,'纪念堂'),(212,'公园前'),(213,'海珠广场'),(214,'市二宫'),(215,'江南西'),(216,'昌岗'),(217,'江泰路'),(218,'东晓南'),(219,'南洲'),(220,'洛溪'),(221,'南浦'),(222,'会江'),(223,'石壁'),(224,'广州南站'),(301,'天河客运站'),(302,'五山'),(303,'华师'),(304,'岗顶'),(305,'石牌桥'),(306,'体育西路'),(307,'珠江新城'),(308,'广州塔'),(309,'客村'),(310,'大塘'),(311,'沥滘'),(312,'厦滘'),(313,'大石'),(314,'汉溪长隆'),(315,'市桥'),(316,'番禺广场'),(401,'金洲'),(402,'蕉门'),(403,'黄阁'),(404,'黄阁汽车城'),(405,'庆盛'),(406,'东涌'),(407,'低涌'),(408,'海傍'),(409,'石碁'),(410,'官桥'),(411,'新造'),(412,'大学城南'),(413,'大学城北'),(414,'官洲'),(415,'万胜围'),(416,'车陂南'),(417,'车陂'),(418,'黄村'),(501,'文冲'),(502,'大沙东'),(503,'大沙地'),(504,'鱼珠'),(505,'三溪'),(506,'东圃'),(507,'车陂南'),(508,'科韵路'),(509,'员村'),(510,'潭村'),(511,'猎德'),(512,'珠江新城'),(513,'五羊邨'),(514,'杨箕'),(515,'动物园'),(516,'区庄'),(517,'淘金'),(518,'小北'),(519,'广州火车站'),(520,'西村'),(521,'西场'),(522,'中山八'),(523,'坦尾'),(524,'滘口'),(601,'浔峰岗'),(602,'横沙'),(603,'沙贝'),(604,'河沙'),(605,'坦尾'),(606,'如意坊'),(607,'黄沙'),(608,'文化公园'),(609,'一德路'),(610,'海珠广场'),(611,'北京路'),(612,'团一大广场'),(613,'东湖'),(614,'东山口'),(615,'区庄'),(616,'黄花岗'),(617,'沙河顶'),(618,'沙河'),(619,'天平架'),(620,'燕塘'),(621,'天河客运站'),(622,'长湴'),(623,'植物园'),(624,'龙洞'),(625,'柯木塱'),(626,'高塘石'),(627,'黄陂'),(628,'金峰'),(629,'暹岗'),(630,'苏元'),(631,'萝岗'),(632,'香雪'),(701,'广州南站'),(702,'石壁'),(703,'谢村'),(704,'钟村'),(705,'汉溪长隆'),(706,'南村万博'),(707,'员岗'),(708,'板桥'),(709,'大学城南'),(801,'凤凰新村'),(802,'沙园'),(803,'宝岗大道'),(804,'昌岗'),(805,'晓港'),(806,'中大'),(807,'鹭江'),(808,'客村'),(809,'赤岗'),(810,'磨碟沙'),(811,'新港东'),(812,'琶洲'),(813,'万胜围'),(30201,'机场南'),(30202,'人和'),(30203,'龙归'),(30204,'嘉禾望岗'),(30205,'白云大道北'),(30206,'永泰'),(30207,'同和'),(30208,'京溪南方医院'),(30209,'梅花园'),(30210,'燕塘'),(30211,'广州东站'),(30212,'林和西');
/*!40000 ALTER TABLE `station` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-18 18:30:20
