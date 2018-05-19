-- MySQL dump 10.13  Distrib 5.7.12, for Win32 (AMD64)
--
-- Host: localhost    Database: delivery
-- ------------------------------------------------------
-- Server version	5.7.15-log

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `card_number` varchar(16) NOT NULL,
  `expiration_month` int(11) NOT NULL,
  `expiration_year` int(11) NOT NULL,
  `csv` int(11) NOT NULL,
  `balance` double NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  PRIMARY KEY (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('1111222233334444',12,19,201,31,'VITALI','NOVIK'),('6666666688888888',1,19,666,347.99,'SERG','NOVIK');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(100) NOT NULL,
  `start_point` varchar(1000) NOT NULL,
  `finish_point` varchar(1000) NOT NULL,
  `delivery_date` date NOT NULL,
  `cargo_kg` double NOT NULL,
  `comment` varchar(10000) DEFAULT NULL,
  `car_number` varchar(8) DEFAULT NULL,
  `total_value` double DEFAULT NULL,
  `app_status` enum('new','confirmed','delivered','waiting','canceled') NOT NULL,
  `cancelation_reason` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`,`user_email`),
  KEY `fk_application_user_idx` (`user_email`),
  KEY `car_number` (`car_number`),
  CONSTRAINT `car_number` FOREIGN KEY (`car_number`) REFERENCES `courier` (`car_number`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_application_user` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,'hr@mail.ru','Minsk, Skriganova 4A, 317','Petrikov, Koroleva str 8, 13','2018-05-20',250,'Забрать груз 14.05.2018 в 18.00. Рояль!',NULL,NULL,'new',''),(2,'hr@mail.ru','Мозырь, Интрнационгальная 104б','Жлобин, Голубева 2','2018-04-30',10,'Небольшой пакет','3456XX-1',57.5,'delivered',NULL),(7,'customer1@mail.ru','Мозырь, Интернациональная 150','Петриков, Королева 7, 14','2018-04-21',200,'Небольшой пакет+ коробка','1442AX-7',18.9,'confirmed',''),(10,'hr@mail.ru','Витебск, Володарского 74б ','Петриков, Королева 7, 14','2018-04-15',400,'Доски','1442AX-7',189,'delivered',NULL),(11,'hr@mail.ru','Мозырь, Интернациональная 150','Петриков, Королева 7, 14','2018-04-28',400,'Цемент, позвонить за 15 минут.','3421MM-7',105,'canceled','Не актуально'),(12,'hr@mail.ru','Витебск, Володарского 74б ','Петриков, Королева 7, 14','2018-04-25',1233,'Цемент, позвонить за 15 минут.','3344TT-1',315,'delivered',NULL),(16,'hr@mail.ru','Витебск, Володарского 74б ','Могилев, Гурского 2, 317','2018-04-30',400,'Пианино','7667AM-7',160,'canceled','Дорого'),(17,'hr@mail.ru','Мозырь, Интернациональная 150','Могилев, Гурского 2, 317','2018-04-30',1500,'Небольшой пакет+ коробка','3344TT-1',87.5,'waiting',NULL),(18,'hr@mail.ru','Витабск, Володарского 74б ','Петриков, Королева 7, 14','2018-05-03',400,'Небольшой пакет+ коробка','3344TT-1',252,'canceled','Не актуально'),(20,'hr@mail.ru','Мозырь, Интернациональная 150','Петриков, Королева 7, 14','2018-05-27',400,'Небольшой пакет+ коробка','3344TT-1',252.01,'confirmed',NULL);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courier`
--

DROP TABLE IF EXISTS `courier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courier` (
  `car_number` varchar(8) NOT NULL,
  `car_producer` varchar(45) NOT NULL,
  `car_model` varchar(45) NOT NULL,
  `car_photo` varchar(100) NOT NULL,
  `driver_phone` varchar(13) NOT NULL,
  `driver_name` varchar(50) NOT NULL,
  `driver_email` varchar(50) NOT NULL,
  `max_cargo` int(11) NOT NULL,
  `km_tax` double NOT NULL,
  `status` enum('active','blocked') NOT NULL DEFAULT 'active',
  PRIMARY KEY (`car_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier`
--

LOCK TABLES `courier` WRITE;
/*!40000 ALTER TABLE `courier` DISABLE KEYS */;
INSERT INTO `courier` VALUES ('1212AX-2','Volkswagen','T5','Transporter.jpg','+375334454545','Nikolai','driver@dr.com',2000,0.26,'active'),('1442AX-7','Dacia','Logan','Dacia.jpg','+375443444494','Vladimir','vladimir@fcq.com',800,0.21,'active'),('2232AX-7','Renault','Dokker','Dokker.JPG','+375293586944','Nikita','ren_dr@fcq.com',800,0.2,'active'),('3344TT-1','Volvo','XC-60','xc-60.jpg','+375336520128','VOVAN','tareltos@gmail.com',600,0.28,'active'),('3421MM-7','Peugeout','Boxer','Boxer.jpg','+375444448877','Andrey','andrey@fcq.com',2100,0.3,'blocked'),('3456XX-1','Volkswagen','Polo','Polo.jpg','+375294444499','Ivan','ivan@fcq.com',800,0.23,'active'),('6753XC-4','Ford','Transit','Transit.jpg','+375293434499','Ivan','ivan@fcq.com',1900,0.26,'active'),('7667AM-7','Volkswagen','Crafter','Crafter.JPG','+375449998877','Maksim','maksim@fcq.com',2300,0.32,'active');
/*!40000 ALTER TABLE `courier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `email` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` enum('admin','manager','customer') CHARACTER SET dec8 NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `phone` varchar(13) NOT NULL,
  `status` enum('active','blocked') NOT NULL DEFAULT 'active',
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('calibry@tut.by','pass161n','customer','Vitali','Tarelko','+375443434366','active'),('customer1@mail.ru','pass160n','customer','Serg','Novik','+375336520128','blocked'),('darya.tarelko@mail.com','pass102n','admin','Darya','Sheshko','+375336520128','blocked'),('dayco@dw.com','pass9n','customer','Elizabet','Voron','+375443434366','blocked'),('hr@mail.ru','pass84n','customer','Vitali','Tarelko','+222222222222','active'),('tareltos@gmail.com','password','admin','Vitali','Tarelko','+375298340889','active'),('tareltos@mail.ru','pass18n','manager','Vladimir','Gogoleva','+375336520128','active');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-20  0:57:10
