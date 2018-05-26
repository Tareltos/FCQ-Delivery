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
INSERT INTO `account` VALUES ('1111222233334444',12,19,201,360,'VITALI','NOVIK'),('6666666688888888',1,19,666,890.4,'SERG','NOVIK');
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (1,'romanov@fcq.com','Витебск, Володарского 74б ','Петриков, Королева 7, 14','2018-06-02',400,'Пианино','6697AS-4',270,'waiting',NULL),(2,'romanov@fcq.com','Витебск, Володарского 74б ','Могилев, Гурского 2, 317','2018-06-21',1233,'Небольшой пакет+ коробка',NULL,0,'new',NULL),(3,'romanov@fcq.com','Минск, Независимости 43, 12','Петриков, Королева 7, 14','2018-05-31',1200,'Мебель','3344TT-1',90,'canceled','Дорого'),(4,'romanov@fcq.com','Мозырь, Интернациональная 150','Петриков, Королева 7, 14','2018-05-30',100,'Небольшой пакет+ коробка','2332TT-1',23.41,'delivered',NULL),(5,'romanov@fcq.com','Мозырь, Интернациональная 150','Могилев, Гурского 2, 317','2018-07-31',200,'Двигатель и коробка передач, позвонить за 30 мин.','3388TT-7',105,'waiting',NULL),(27,'customer1@mail.ru','Мозырь, Интернациональная 150','Гродно, Космонавтов 45А','2018-08-03',1500,'Автозапчасти',NULL,0,'new',NULL),(28,'customer1@mail.ru','Гродно, Молодежная 12','Брест, Нововиленская 33','2018-06-16',1000,'5 бочек масла','3344TT-1',120,'confirmed',NULL),(29,'customer1@mail.ru','Брест, Котловца 66','Петриков, Королева 7, 14','2018-05-30',200,'Цемент, позвонить за 15 минут.','2323TS-4',98.01,'waiting',NULL),(30,'customer1@mail.ru','Витебск, Володарского 74б ','Могилев, Гурского 2, 317','2018-06-01',400,'Пианино',NULL,0,'new',NULL);
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
INSERT INTO `courier` VALUES ('2323TS-4','Renault','Dokker','Dokker.JPG','+375336520128','Максим','tareltos@gmail.com',890,0.28,'active'),('2332TT-1','Volkswagen','Polo','Polo.jpg','+375336520128','Виталий','tareltos@gmail.com',600,0.26,'active'),('3344TT-1','Volkswagen','Crafter','Crafter.JPG','+375332345677','Павел','pavel@fcq.com',2200,0.3,'active'),('3388TT-7','Ford','Transit','Transit.jpg','+375336520100','Виталий','tareltos@gmail.com',1700,0.3,'active'),('3454VT-1','Volvo','XC-60','xc-60.jpg','+375443434366','Дмитрий','dmitri@fcq.com',1020,0.35,'blocked'),('5555TT-1','Peugeot','Boxer','Boxer.jpg','+375336520100','Николай','nikolay@fcq.com',1500,0.28,'active'),('6697AS-4','Volkswagen','Transporter','Transporter.jpg','+222222222222','Виталий','tareltos@mail.ru',1700,0.3,'active'),('8800VB-1','Dacia','Logan(универсал)','Dacia.jpg','+375336520128','Виталий','tareltos@gmail.com',800,0.26,'blocked');
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
INSERT INTO `user` VALUES ('customer1@mail.ru','pass108n','customer','Татьяна','Самсоник','+375336520128','active'),('darya.tarelko@mail.com','pass146n','admin','Darya','Sheshko','+375336520128','blocked'),('romanov@fcq.com','pass119n','customer','Vasili','Romanov','+375332345678','active'),('tareltos@gmail.com','password','admin','Vitali','Tarelko','+375297340877','active'),('tareltos@mail.ru','pass62n','manager','Igor','Boiko','+375332322121','active');
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

-- Dump completed on 2018-05-26 23:23:24
