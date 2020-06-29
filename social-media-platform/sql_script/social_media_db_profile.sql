-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: localhost    Database: social_media_db
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `profile` (
  `firstname` varchar(25) NOT NULL,
  `lastname` varchar(25) NOT NULL,
  `time_created` date DEFAULT NULL,
  `profilename` varchar(30) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`profilename`),
  UNIQUE KEY `profilename` (`profilename`),
  UNIQUE KEY `password` (`password`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES ('Chien','Pham','2020-06-01','Chien1','quangchiennnn@gmail.com','0366271270','Ha Noi','$31$16$7vUFsZh_GzP6YDuZgqxm9M2vxJfwfhifRDbsqAGjRNM','Oat','What is your nickname?'),('Chien','Pham','2020-06-01','Chien2','quangchiennnn@gmail.com','0366271270','Ha Noi','Oat','What is your nickname?','$31$16$PaMcENX-3PCewctZXubEJqEfBm2OQwDW0XbXg_PH8bM'),('Chien','Pham','2020-06-01','Chien3','quangchiennnn@gmail.com','0366271270','Ha Noi','Oat','What is your nickname?','$31$16$OCblfzKZe2KEV-BZqUzNIKKH7BjMN3rf5yxbCL_0BXE'),('Chiennnnnn','Trammmm','2020-06-01','Chien4','quangchiennnn@gmail.com','0366271270','Ha Noi','Oat','What is your nickname?','$31$16$EbHT2LCIO2Ce7ldkNtH0wdMX7rRPv2E5gNRrDiVXEMA'),('Chien','Pham','2020-06-01','Chien5','quangchiennnn@gmail.com','0366271270','Ha Noi','Oat','What is your nickname?','$31$16$bgf-lPUwAjgZYauXRsW-_HLc3N2NT-il_atGxHW_e6E'),('Chien','Pham','2020-05-29','Quangchien1','quangchiennnn@gmail.com','0366271270','Ha Noi',NULL,NULL,'Quangchien17@'),('Chienn','Phamm','2020-06-05','Quangchien19',NULL,NULL,NULL,NULL,NULL,'$31$16$OM4mPLoxldcTJ9mPuIQMSa7KQk1jIJLipisypxfDTE8'),('Chien','Pham','2020-05-29','Quangchien2','quangchiennnn@gmail.com','0366271270','Ha Noi',NULL,NULL,'$31$16$TC1nBYO3Rpx__pfsphbl4nxG4FJ25tdB6a7hIcgjcR4'),('Chien','Pham','2020-05-29','Quangchien3','quangchiennnn@gmail.com','0366271270','Ha Noi','$31$16$eEHHo2xQ6d5V5AzS50V0Xjzf13JqVBPF5xkfGJq9F4Y','Every time very time','What is your\r\n												favourite book?'),('Nguyen','Ha','2020-06-06','Thu Ha','ngthuha219@gmail.com','0984060146','Ha Noi, Viet Nam','Harry Potter','What is your\r\n												favourite book?','$31$16$RpZm6kfzBgxm_8zZEie4ojfxRWYxzt3vt3JGI-UNCHc'),('Ha','Nguyen','2020-06-26','ThuHa','ngthuha219@gmail.com','0984060146','HN','HP','What is your favorite book?','$31$16$EkX29pcOuKVTeOoiE3PMLgjSsuETkRjbKkfhueJvKOc'),('Ha','Nguyen','2020-05-29','ThuHa219',NULL,NULL,NULL,'harry potter','what is favorite book ?','123456');
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-29  8:40:06
