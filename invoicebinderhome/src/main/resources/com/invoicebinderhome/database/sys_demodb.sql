CREATE DATABASE  IF NOT EXISTS `sys_demodb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sys_demodb`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sys_demodb
-- ------------------------------------------------------
-- Server version	5.7.17-0ubuntu0.16.04.1

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `countrycode` varchar(255) DEFAULT NULL,
  `postcode` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'8 Huntingwood Drive',NULL,'Sydney','AU','2000','NSW'),(2,'80 Blairgowrie Avenue',NULL,'Sydney','AU','2000','NSW'),(3,'60 Beach Street',NULL,'North Sydney','AU','2060','NSW'),(4,'71 Insignia Way',NULL,'North Sydney','AU','2060','NSW'),(5,'43 Manchester Road',NULL,'Moore Park','AU','2021','NSW'),(6,'3 Yangan Drive',NULL,'Kingsgrove','AU','1480','NSW'),(7,'19 Baker Street',NULL,'Queens Park','AU','2022','NSW'),(8,'87 Fitzroy Street',NULL,'Bellevue Hill','AU','2023','NSW'),(9,'40 Cornish Street',NULL,'Sydney','AU','2000','NSW'),(10,'16 Lapko Road',NULL,'Sydney','AU','2000','NSW'),(11,'98 Quoin Road',NULL,'Bronte','AU','2024','NSW'),(12,'25 Ocean Pde',NULL,'Bondi Beach','AU','2026','NSW'),(13,'38 Begley Street',NULL,'Sydney','AU','2000','NSW'),(14,'71 Insignia Way',NULL,'Edgecliff','AU','2027','NSW'),(15,'19 Grandis Road',NULL,'Randwick','AU','2031','NSW'),(16,'8 Mt Berryman Road',NULL,'Randwick','AU','2031','NSW'),(17,'97 Austin Road',NULL,'Clovelly','AU','2031','NSW'),(18,'44 Boughtman Street',NULL,'Maroubra','AU','2035','NSW'),(19,'19 Moruya Road',NULL,'Maroubra','AU','2035','NSW'),(20,'56 Cubbine Road',NULL,'Bronte','AU','2024','NSW'),(21,'83 South Molle Boulevard',NULL,'Clovelly','AU','2031','NSW'),(22,'80 Blairgowrie Avenue',NULL,'Eastgardens','AU','2036','NSW'),(23,'60 Beach Street',NULL,'Hillsdale','AU','2036','NSW'),(24,'71 Insignia Way',NULL,'Sydney','AU','2000','NSW'),(25,'43 Manchester Road',NULL,'Hillsdale','AU','2036','NSW'),(26,'3 Yangan Drive',NULL,'Glebe','AU','2037','NSW'),(27,'19 Baker Street',NULL,'Sydney','AU','2000','NSW'),(28,'87 Fitzroy Street',NULL,'Glebe','AU','2037','NSW'),(29,'40 Cornish Street',NULL,'Sydney','AU','2000','NSW'),(30,'16 Lapko Road',NULL,'Clovelly','AU','2031','NSW'),(31,'98 Quoin Road',NULL,'Annandale','AU','2038','NSW'),(32,'25 Ocean Pde',NULL,'Sydenham','AU','2044','NSW'),(33,'38 Begley Street',NULL,'Sydenham','AU','2044','NSW'),(34,'71 Insignia Way',NULL,'Haberfield','AU','2045','NSW'),(35,'19 Grandis Road',NULL,'Sydney','AU','2000','NSW'),(36,'8 Mt Berryman Road',NULL,'Haberfield','AU','2045','NSW'),(37,'97 Austin Road',NULL,'Sydney','AU','2000','NSW'),(38,'44 Boughtman Street',NULL,'Lidcombe','AU','2141','NSW'),(39,'19 Moruya Road',NULL,'Five Dock','AU','2046','NSW'),(40,'56 Cubbine Road',NULL,'Haberfield','AU','2045','NSW'),(41,'83 South Molle Boulevard',NULL,'Glebe','AU','2037','NSW'),(42,'80 Blairgowrie Avenue',NULL,'Strathfield','AU','2135','NSW'),(43,'60 Beach Street',NULL,'Sydney','AU','2000','NSW'),(44,'71 Insignia Way',NULL,'Strathfield','AU','2135','NSW'),(45,'43 Manchester Road',NULL,'Burwood','AU','2134','NSW'),(46,'3 Yangan Drive',NULL,'Sydney','AU','2000','NSW'),(47,'19 Baker Street',NULL,'Burwood','AU','2134','NSW'),(48,'87 Fitzroy Street',NULL,'Lidcombe','AU','2141','NSW'),(49,'40 Cornish Street',NULL,'Berala','AU','2141','NSW'),(50,'16 Lapko Road',NULL,'Granville','AU','2142','NSW'),(51,'98 Quoin Road',NULL,'Westmead','AU','2145','NSW'),(52,'25 Ocean Pde',NULL,'Westmead','AU','2145','NSW'),(53,'38 Begley Street',NULL,'Blacktown','AU','2148','NSW'),(54,'71 Insignia Way',NULL,'Blacktown','AU','2148','NSW'),(55,'19 Grandis Road',NULL,'Blacktown','AU','2148','NSW'),(56,'8 Mt Berryman Road',NULL,'Parramatta','AU','2150','NSW'),(57,'97 Austin Road',NULL,'Sydney','AU','2000','NSW'),(58,'44 Boughtman Street',NULL,'Parramatta','AU','2150','NSW'),(59,'19 Moruya Road',NULL,'Blacktown','AU','2148','NSW'),(60,'56 Cubbine Road',NULL,'Northmead','AU','2152','NSW'),(61,'83 South Molle Boulevard',NULL,'Beaumont Hills','AU','2155','NSW'),(62,'80 Blairgowrie Avenue',NULL,'Glenorie','AU','2157','NSW'),(63,'60 Beach Street',NULL,'Sydney','AU','2000','NSW'),(64,'71 Insignia Way',NULL,'Sydney','AU','2000','NSW'),(65,'43 Manchester Road',NULL,'Sydney','AU','2000','NSW'),(66,'3 Yangan Drive',NULL,'Sydney','AU','2000','NSW'),(67,'19 Baker Street',NULL,'Sydney','AU','2000','NSW'),(68,'87 Fitzroy Street',NULL,'Glenorie','AU','2157','NSW'),(69,'40 Cornish Street',NULL,'Sydney','AU','2000','NSW'),(70,'16 Lapko Road',NULL,'Chester Hill','AU','2162','NSW'),(71,'98 Quoin Road',NULL,'Sefton','AU','2162','NSW'),(72,'25 Ocean Pde',NULL,'Villawood','AU','2163','NSW'),(73,'38 Begley Street',NULL,'Smithfield','AU','2164','NSW'),(74,'71 Insignia Way',NULL,'Sydney','AU','2000','NSW'),(75,'19 Grandis Road',NULL,'Cabramatta','AU','2166','NSW'),(76,'8 Mt Berryman Road',NULL,'Smithfield','AU','2164','NSW'),(77,'97 Austin Road',NULL,'Sydney','AU','2000','NSW'),(78,'44 Boughtman Street',NULL,'Cabramatta','AU','2166','NSW'),(79,'19 Moruya Road',NULL,'Beaumont Hills','AU','2155','NSW'),(80,'56 Cubbine Road',NULL,'Sydney','AU','2000','NSW'),(81,'83 South Molle Boulevard',NULL,'Lansvale','AU','2166','NSW'),(82,'80 Blairgowrie Avenue',NULL,'Green Valley','AU','2168','NSW'),(83,'60 Beach Street',NULL,'Sydney','AU','2000','NSW'),(84,'71 Insignia Way',NULL,'Green Valley','AU','2168','NSW'),(85,'43 Manchester Road',NULL,'Sydney','AU','2000','NSW'),(86,'3 Yangan Drive',NULL,'Casula','AU','2170','NSW'),(87,'19 Baker Street',NULL,'Sydney','AU','2000','NSW'),(88,'87 Fitzroy Street',NULL,'Glenorie','AU','2157','NSW'),(89,'40 Cornish Street',NULL,'Beaumont Hills','AU','2155','NSW'),(90,'16 Lapko Road',NULL,'Sydney','AU','2000','NSW'),(91,'98 Quoin Road',NULL,'Casula','AU','2170','NSW'),(92,'25 Ocean Pde',NULL,'Arcadia','AU','2159','NSW'),(93,'38 Begley Street',NULL,'Galston','AU','2159','NSW'),(94,'71 Insignia Way',NULL,'Guildford','AU','2161','NSW'),(95,'19 Grandis Road',NULL,'Blacktown','AU','2148','NSW'),(96,'8 Mt Berryman Road',NULL,'Baulkham Hills','AU','2153','NSW'),(97,'97 Austin Road',NULL,'Sydney','AU','2000','NSW'),(98,'44 Boughtman Street',NULL,'Baulkham Hills','AU','2153','NSW'),(99,'19 Moruya Road',NULL,'Sydney','AU','2000','NSW'),(100,'56 Cubbine Road',NULL,'Kellyville','AU','2155','NSW');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `autonum`
--

DROP TABLE IF EXISTS `autonum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autonum` (
  `id` varchar(255) NOT NULL,
  `numPrefix` varchar(255) DEFAULT NULL,
  `numSuffix` varchar(255) DEFAULT NULL,
  `numValue` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autonum`
--

LOCK TABLES `autonum` WRITE;
/*!40000 ALTER TABLE `autonum` DISABLE KEYS */;
INSERT INTO `autonum` VALUES ('INVOICE','INV','',22),('PAYMENT','REF','',10001);
/*!40000 ALTER TABLE `autonum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(255) DEFAULT NULL,
  `country_iso_code` varchar(255) DEFAULT NULL,
  `post_code` varchar(255) DEFAULT NULL,
  `state_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'AR','A','POZO CERCADO (EL CHORRO (F), DPTO. RIVADAVIA (S))','3636'),(2,'AR','A','LAS SALADAS','4123'),(3,'AR','A','EL BRETE','4126'),(4,'AR','A','BRETE','4126'),(5,'AR','A','CEIBAL','4126'),(6,'AR','A','EL CUIBAL','4126'),(7,'AR','A','EL SUNCHAL','4126'),(8,'AR','A','OVEJERO','4126'),(9,'AR','A','LA ASUNCION','4126'),(10,'AR','A','BARADERO','4126'),(11,'AR','A','SAN PEDRO DE ARANDA','4126'),(12,'AR','A','MIRAFLORES (TALA, DPTO. CANDELARIA)','4126'),(13,'AR','A','SALAZAR','4126'),(14,'AR','A','LA MARAVILLA','4126'),(15,'AR','A','RUIZ DE LOS LLANOS','4126'),(16,'AR','A','EL JARDIN','4126'),(17,'AR','A','ALEM','4126'),(18,'AR','A','CANDELARIA','4126'),(19,'AR','A','LOS MOGOTES','4126'),(20,'AR','A','TALA','4126');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `clientStatus` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `contactId` bigint(20) DEFAULT NULL,
  `emailId` bigint(20) DEFAULT NULL,
  `addressId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKAF12F3CB51A2FA3F` (`contactId`),
  KEY `FKAF12F3CB5FFB4337` (`emailId`),
  KEY `FKAF12F3CB5C4AA8BB` (`addressId`),
  KEY `FKAF12F3CB5AA28CA6` (`userId`),
  CONSTRAINT `FKAF12F3CB51A2FA3F` FOREIGN KEY (`contactId`) REFERENCES `contact` (`id`),
  CONSTRAINT `FKAF12F3CB5AA28CA6` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `FKAF12F3CB5C4AA8BB` FOREIGN KEY (`addressId`) REFERENCES `address` (`id`),
  CONSTRAINT `FKAF12F3CB5FFB4337` FOREIGN KEY (`emailId`) REFERENCES `email` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'ACTIVE','April','Sanders',1,1,1,NULL),(2,'ACTIVE','Piper','Borthwick',2,2,2,NULL),(3,'ACTIVE','Kaitlyn','Alsop',3,3,3,NULL),(4,'ACTIVE','Christian','Cann',4,4,4,NULL),(5,'ACTIVE','Olivia','Arthur',5,5,5,NULL),(6,'ACTIVE','Lily','Kaylock',6,6,6,NULL),(7,'ACTIVE','Tyler','Bracy',7,7,7,NULL),(8,'ACTIVE','Jessica','Lovely',8,8,8,NULL),(9,'ACTIVE','Lachlan','Biaggini',9,9,9,NULL),(10,'ACTIVE','Piper','Tedbury',10,10,10,NULL),(11,'ACTIVE','Ava','McKivat',11,11,11,NULL),(12,'ACTIVE','Antoine','Whitehead',12,12,12,NULL),(13,'ACTIVE','Camille','Bush',13,13,13,NULL),(14,'ACTIVE','Bryan','Braga',14,14,14,NULL),(15,'ACTIVE','Fred','Sohn',15,15,15,NULL),(16,'ACTIVE','Sarah','Dunlap',16,16,16,NULL),(17,'ACTIVE','James','Rose',17,17,17,NULL),(18,'ACTIVE','Lorie','Bryan',18,18,18,NULL),(19,'ACTIVE','Tiera','Porter',19,19,19,NULL),(20,'ACTIVE','Ella','Hooks',20,20,20,NULL),(21,'ACTIVE','Rafael','Lotts',21,21,21,NULL),(22,'ACTIVE','Judy','Gagne',22,22,22,NULL),(23,'ACTIVE','Amanda','Wilson',23,23,23,NULL),(24,'ACTIVE','Brenda','Kohler',24,24,24,NULL),(25,'ACTIVE','Geoffrey','Rowley',25,25,25,NULL),(26,'ACTIVE','Charles','Fielding',26,26,26,NULL),(27,'ACTIVE','Annie','Hinton',27,27,27,NULL),(28,'ACTIVE','Robert','Holiman',28,28,28,NULL),(29,'ACTIVE','Gracie','Biaggini',29,29,29,NULL),(30,'ACTIVE','Kelly','Williams',30,30,30,NULL),(31,'ACTIVE','Elizabeth','York',31,31,31,NULL),(32,'ACTIVE','David','Baily',32,32,32,NULL),(33,'ACTIVE','Christopher','Delaney',33,33,33,NULL),(34,'ACTIVE','Joseph','Matthews',34,34,34,NULL),(35,'ACTIVE','Lisa','Sweeney',35,35,35,NULL),(36,'ACTIVE','Nancy','Underwood',36,36,36,NULL),(37,'ACTIVE','Marisol','Turner',37,37,37,NULL),(38,'ACTIVE','Geneva','Marsh',38,38,38,NULL),(39,'ACTIVE','Gregory','Jimenez',39,39,39,NULL),(40,'ACTIVE','Edmond','Cawthon',40,40,40,NULL),(41,'ACTIVE','Bernard','Nichols',41,41,41,NULL),(42,'ACTIVE','Joseph','Byron',42,42,42,NULL),(43,'ACTIVE','Kathryn','Hunt',43,43,43,NULL),(44,'ACTIVE','James','Marlow',44,44,44,NULL),(45,'ACTIVE','Gene','Wyatt',45,45,45,NULL),(46,'ACTIVE','Bret','Gonzalez',46,46,46,NULL),(47,'ACTIVE','Daniel','Burr',47,47,47,NULL),(48,'ACTIVE','Adam','Taylor',48,48,48,NULL),(49,'ACTIVE','Donald','Valenzuela',49,49,49,NULL),(50,'ACTIVE','Emily','Patterson',50,50,50,NULL),(51,'ACTIVE','Brenda','Barker',51,51,51,NULL),(52,'ACTIVE','Cory','Whitehead',52,52,52,NULL),(53,'ACTIVE','Thomas','Bush',53,53,53,NULL),(54,'ACTIVE','Derek','Braga',54,54,54,NULL),(55,'ACTIVE','Adrian','Cline',55,55,55,NULL),(56,'ACTIVE','Cristopher','Dunlap',56,56,56,NULL),(57,'ACTIVE','Nelda','Rose',57,57,57,NULL),(58,'ACTIVE','Martin','Burns',58,58,58,NULL),(59,'ACTIVE','Bonnie','Porter',59,59,59,NULL),(60,'ACTIVE','Mary','Hooks',60,60,60,NULL),(61,'ACTIVE','Dennis','Gann',61,61,61,NULL),(62,'ACTIVE','Roger','Borthwick',62,62,62,NULL),(63,'ACTIVE','Pierre','Alsop',63,63,63,NULL),(64,'ACTIVE','Viola','Looney',64,64,64,NULL),(65,'ACTIVE','Charles','Arthur',65,65,65,NULL),(66,'ACTIVE','Henry','Kaylock',66,66,66,NULL),(67,'ACTIVE','Andrew','Bracy',67,67,67,NULL),(68,'ACTIVE','Jessy','Lovely',68,68,68,NULL),(69,'ACTIVE','Larry','Biaggini',69,69,69,NULL),(70,'ACTIVE','Peter','Tedbury',70,70,70,NULL),(71,'ACTIVE','Avita','McKivat',71,71,71,NULL),(72,'ACTIVE','Anthony','Whitehead',72,72,72,NULL),(73,'ACTIVE','Camillia','Bush',73,73,73,NULL),(74,'ACTIVE','Milton','Werner',74,74,74,NULL),(75,'ACTIVE','Angelina','Aranda',75,75,75,NULL),(76,'ACTIVE','Randall','Dunlap',76,76,76,NULL),(77,'ACTIVE','William','Rose',77,77,77,NULL),(78,'ACTIVE','Ollie','Bryan',78,78,78,NULL),(79,'ACTIVE','Leonard','Porter',79,79,79,NULL),(80,'ACTIVE','Lynn','Hooks',80,80,80,NULL),(81,'ACTIVE','Pamela','Sanders',81,81,81,NULL),(82,'ACTIVE','Caroline','Brooks',82,82,82,NULL),(83,'ACTIVE','Katherine','Alsop',83,83,83,NULL),(84,'ACTIVE','Rosa','Cann',84,84,84,NULL),(85,'ACTIVE','Henry','Arthur',85,85,85,NULL),(86,'ACTIVE','Juan','Kaylock',86,86,86,NULL),(87,'ACTIVE','Ida','Bracy',87,87,87,NULL),(88,'ACTIVE','Michael','Lovely',88,88,88,NULL),(89,'ACTIVE','Harold','Biaggini',89,89,89,NULL),(90,'ACTIVE','Maryellen','Tedbury',90,90,90,NULL),(91,'ACTIVE','Helen','McKivat',91,91,91,NULL),(92,'ACTIVE','Curtis','Whitehead',92,92,92,NULL),(93,'ACTIVE','Clark','Dugger',93,93,93,NULL),(94,'ACTIVE','Louis','Braga',94,94,94,NULL),(95,'ACTIVE','Wilma','Sohn',95,95,95,NULL),(96,'ACTIVE','Freddie','Dunlap',96,96,96,NULL),(97,'ACTIVE','Lindsey','Rose',97,97,97,NULL),(98,'ACTIVE','Veronica','Bryan',98,98,98,NULL),(99,'ACTIVE','Harold','Porter',99,99,99,NULL),(100,'ACTIVE','Mellisa','Hooks',100,100,100,NULL);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `configKey` varchar(255) DEFAULT NULL,
  `configSection` varchar(255) DEFAULT NULL,
  `dataType` varchar(255) DEFAULT NULL,
  `maxLength` int(11) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (1,'BUSINESSNAME','Business','String',100,'Demo Company'),(2,'BUSINESSABN','Business','String',11,'53004085616'),(3,'BUSINESSADDRESS1','Business','String',100,'101 Demo Place'),(4,'BUSINESSADDRESS2','Business','String',100,''),(5,'BUSINESSCITY','Business','String',50,'Sydney'),(6,'BUSINESSSTATE','Business','String',50,'NSW'),(7,'BUSINESSPOSTCODE','Business','String',50,'2000'),(8,'BUSINESSCOUNTRYCODE','Business','String',50,'AU'),(9,'BUSINESSLOGOURL','Business','String',50,'/images/logo.png'),(10,'BUSINESSPHONE','Business','String',10,'0299998888'),(11,'BUSINESSFAX','Business','String',10,'0299998888'),(12,'BUSINESSEMAIL','Business','String',50,'invoicebinder@gmail.com'),(13,'BUSINESSABNLABEL','Business','String',50,'ABN'),(14,'EMAILHOST','Email','String',50,'smtp.gmail.com'),(15,'EMAILPORT','Email','String',50,'587'),(16,'EMAILUSERNAME','Email','String',50,'invoicebinder@gmail.com'),(17,'EMAILPASSWORD','Email','String',50,'Topu Gopu 51'),(18,'EMAILFROMADDRESS','Email','String',50,'invoicebinder@gmail.com'),(19,'EMAILUSESECURETRANSPORT','Email','String',50,'TRUE'),(20,'EMAILINVOICETEMPLATE','Email','String',200,'Dear [ClientName],\nPlease find the invoice [InvoiceNo] for amount [InvoiceAmount] attached.\nRegards,\n[BusinessName]'),(21,'APPLANDINGPAGE','ApplicationSettings','String',50,'LOGINPAGE'),(22,'APPTITLE','ApplicationSettings','String',50,'Demo Company'),(23,'APPSLOGAN','ApplicationSettings','String',50,'Welcome To Demo Company'),(24,'CURRENCY','ApplicationSettings','String',10,'AUD'),(25,'TAXLABEL','ApplicationSettings','String',10,'TAX'),(26,'INVOICETEMPLATENAME','InvoiceTemplates','String',10,'Standard'), (27,'PAYPALEMAIL','Payments','String',50,'invoicebinder@gmail.com'),(28,'ALLOWONLINEPAYMENT','Payments','String',10,'True');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `faxNumber` varchar(255) DEFAULT NULL,
  `homePhone` varchar(255) DEFAULT NULL,
  `mobilePhone` varchar(255) DEFAULT NULL,
  `workPhone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'0293458888','0299995555','0401010101','0299995554'),(2,'0299995688','0299995688','0412234501','0299567657'),(3,'0299956788','0299995555','0401010401','0299991212'),(4,'0245698888','0299995555','0443310101','0299567657'),(5,'0299967888','0299995555','0404455101','0299991212'),(6,'0299567888','0299995555','0405640101','0299567657'),(7,'0245658888','0299995688','0401015601','0299991212'),(8,'0245698888','0299995555','0401404561','0299995554'),(9,'0256798888','0299995555','0445450101','0299991212'),(10,'0297898888','0299995688','0444510101','0299567657'),(11,'0299978999','0299995555','0466777101','0299995554'),(12,'0299998678','0299995688','0408880101','0299991212'),(13,'0299998233','0299567657','0499000101','0299995554'),(14,'0299992344','0299995688','0478780101','0299991212'),(15,'0299992344','0299995555','0408880101','0299995554'),(16,'0299998456','0299567657','0401015601','0299567657'),(17,'0299934555','0299995555','0401010101','0299991212'),(18,'0657567567','0299567657','0401015601','0299995554'),(19,'0299567657','0299995555','0408880101','0299991212'),(20,'0299998888','0299995555','0401015601','0299991212'),(21,'0299998456','0299995555','0408880101','0299995554'),(22,'0299567657','0299995555','0401015601','0299991212'),(23,'0299998888','0299998456','0401010101','0299991212'),(24,'0299998888','0299995555','0401015601','0299995554'),(25,'0299567657','0299995555','0408880101','0299991212'),(26,'0299998888','0299995555','0401015601','0299567657'),(27,'0299998888','0299995555','0408880101','0299991212'),(28,'0299998456','0299998456','0401015601','0299995554'),(29,'0299998888','0299995555','0401010101','0299991212'),(30,'0299998456','0299995688','0401010101','0299995554'),(31,'0299998456','0299995688','0401010101','0299991212'),(32,'0299998888','0299995555','0408880101','0299991212'),(33,'0299998456','0299567657','0401010101','0299991212'),(34,'0299998888','0299995555','0408880101','0299995554'),(35,'0299567657','0299567657','0401010101','0299995554'),(36,'0299567657','0299995555','0401010101','0299991212'),(37,'0299998456','0299995555','0401010101','0299567657'),(38,'0299998888','0299567657','0401015601','0299991212'),(39,'0299567657','0299567657','0401015601','0299995554'),(40,'0299998888','0299995555','0408880101','0299991212'),(41,'0299567657','0299567657','0401015601','0299991212'),(42,'0299998888','0299567657','0408880101','0299567657'),(43,'0299998888','0299995555','0401010101','0299995554'),(44,'0299567657','0299998456','0401010101','0299991212'),(45,'0299998888','0299995555','0408880101','0299995554'),(46,'0299998888','0299995555','0401015601','0299991212'),(47,'0299567657','0299998456','0401010101','0299995554'),(48,'0299998888','0299995555','0401010101','0299991212'),(49,'0299998888','0299995555','0401015601','0299567657'),(50,'0299998456','0299998456','0401010101','0299995554'),(51,'0299998888','0299995555','0408880101','0299991212'),(52,'0299567657','0299995555','0401010101','0299991212'),(53,'0299998888','0299567657','0401015601','0299567657'),(54,'0299567657','0299995555','0408880101','0299991212'),(55,'0299998888','0299995555','0401010101','0299567657'),(56,'0299998888','0299567657','0401010101','0299995554'),(57,'0299567657','0299995555','0401010101','0299991212'),(58,'0299998888','0299995555','0401010101','0299991212'),(59,'0299998888','0299995555','0401015601','0299567657'),(60,'0299998456','0299995555','0401010101','0299991212'),(61,'0299998888','0299995555','0401010101','0299567657'),(62,'0299998456','0299995555','0408880101','0299991212'),(63,'0299998888','0299995555','0401015601','0299991212'),(64,'0299998456','0299995555','0401010101','0299567657'),(65,'0299998888','0299995555','0401010101','0299991212'),(66,'0299998888','0299995555','0408880101','0299995554'),(67,'0299998888','0299995555','0408880101','0299991212'),(68,'0299998456','0299995555','0401015601','0299995554'),(69,'0299998456','0299998456','0401010101','0299991212'),(70,'0299998888','0299995555','0408880101','0299995554'),(71,'0299998456','0299998456','0408880101','0299991212'),(72,'0299998888','0299995555','0401010101','0299991212'),(73,'0299995344','0299995554','0401015601','0299995554'),(74,'0299995344','0299995555','0408880101','0299991212'),(75,'0299998888','0299998456','0401010101','0299991212'),(76,'0299995344','0299995554','0408880101','0299991212'),(77,'0299998888','0299995555','0401015601','0299991212'),(78,'0299998456','0299995554','0408880101','0299995554'),(79,'0299995554','0299995555','0401010101','0299991212'),(80,'0299995554','0299995555','0401015601','0299567657'),(81,'0299998888','0299995554','0408880101','0299991212'),(82,'0299995554','0299995554','0408880101','0299995554'),(83,'0299995344','0299995554','0401010101','0299991212'),(84,'0299998888','0299995554','0401015601','0299567657'),(85,'0299998888','0299998456','0408880101','0299991212'),(86,'0299995344','0299995344','0408880101','0299995554'),(87,'0299998888','0299934555','0408880101','0299995554'),(88,'0299998456','0235595555','0401015601','0299991212'),(89,'0299998888','0299995344','0401010101','0299991212'),(90,'0299995554','0299456455','0408880101','0299567657'),(91,'0299995554','0299993455','0408880101','0299995554'),(92,'0299998888','0299995554','0408880101','0299991212'),(93,'0299995554','0299996788','0401015601','0299991212'),(94,'0299995554','0299995677','0408880101','0299995554'),(95,'0299998888','0299768788','0401015601','0299991212'),(96,'0299998888','0299995344','0408880101','0299567657'),(97,'0299995554','0299995534','0401010101','0299991212'),(98,'0299998888','0234595555','0408880101','0299567657'),(99,'0299995554','0299923455','0401015601','0299995554'),(100,'0299995554','0291231255','0408880101','0299998456');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `continent_code` varchar(255) DEFAULT NULL,
  `continent_name` varchar(255) DEFAULT NULL,
  `country_iso_code` varchar(255) DEFAULT NULL,
  `country_name` varchar(255) DEFAULT NULL,
  `geoname_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=247 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'AS','Asia','TH','Thailand',1605651),(2,'AS','Asia','JP','Japan',1861060),(3,'AS','Asia','CN','China',1814991),(4,'OC','Oceania','AU','Australia',2077456),(5,'AS','Asia','IN','India',1269750),(6,'AS','Asia','MY','Malaysia',1733045),(7,'AS','Asia','KR','South Korea',1835841),(8,'AS','Asia','TW','Taiwan',1668284),(9,'AS','Asia','HK','Hong Kong',1819730),(10,'AS','Asia','PH','Philippines',1694008),(11,'AS','Asia','VN','Vietnam',1562822),(12,'EU','Europe','FR','France',3017382),(13,'EU','Europe','DE','Germany',2921044),(14,'AS','Asia','IL','Israel',294640),(15,'EU','Europe','SE','Sweden',2661886),(16,'EU','Europe','IT','Italy',3175395),(17,'EU','Europe','GR','Greece',390903),(18,'EU','Europe','NL','Netherlands',2750405),(19,'EU','Europe','ES','Spain',2510769),(20,'EU','Europe','AT','Austria',2782113),(21,'EU','Europe','GB','United Kingdom',2635167),(22,'EU','Europe','BE','Belgium',2802361),(23,'EU','Europe','CH','Switzerland',2658434),(24,'AS','Asia','AE','United Arab Emirates',290557),(25,'EU','Europe','RU','Russia',2017370),(26,'AS','Asia','KZ','Kazakhstan',1522867),(27,'EU','Europe','DK','Denmark',2623032),(28,'EU','Europe','PT','Portugal',2264397),(29,'AS','Asia','SA','Saudi Arabia',102358),(30,'AS','Asia','IR','Iran',130758),(31,'EU','Europe','NO','Norway',3144096),(32,'NA','North America','US','United States',6252001),(33,'NA','North America','MX','Mexico',3996063),(34,'AS','Asia','SY','Syria',163843),(35,'EU','Europe','UA','Ukraine',690791),(36,'EU','Europe','CY','Cyprus',146669),(37,'EU','Europe','CZ','Czechia',3077311),(38,'AS','Asia','IQ','Iraq',99237),(39,'EU','Europe','RO','Romania',798549),(40,'AS','Asia','TR','Turkey',298795),(41,'AS','Asia','LB','Lebanon',272103),(42,'EU','Europe','HU','Hungary',719819),(43,'AS','Asia','GE','Georgia',614540),(44,'AS','Asia','AF','Afghanistan',1149361),(45,'SA','South America','BR','Brazil',3469034),(46,'AS','Asia','AZ','Azerbaijan',587116),(47,'AS','Asia','PS','Palestine',6254930),(48,'EU','Europe','LT','Lithuania',597427),(49,'AS','Asia','OM','Oman',286963),(50,'EU','Europe','RS','Serbia',6290252),(51,'EU','Europe','SK','Slovakia',3057568),(52,'EU','Europe','FI','Finland',660013),(53,'EU','Europe','IS','Iceland',2629691),(54,'EU','Europe','BG','Bulgaria',732800),(55,'EU','Europe','SI','Slovenia',3190538),(56,'EU','Europe','MD','Moldova',617790),(57,'EU','Europe','MK','Macedonia',718075),(58,'EU','Europe','LI','Liechtenstein',3042058),(59,'EU','Europe','JE','Jersey',3042142),(60,'EU','Europe','PL','Poland',798544),(61,'EU','Europe','HR','Croatia',3202326),(62,'EU','Europe','BA','Bosnia and Herzegovina',3277605),(63,'EU','Europe','EE','Estonia',453733),(64,'EU','Europe','LV','Latvia',458258),(65,'AS','Asia','JO','Jordan',248816),(66,'AS','Asia','KG','Kyrgyzstan',1527747),(67,'AF','Africa','RE','RÃ©union',935317),(68,'AF','Africa','YT','Mayotte',1024031),(69,'EU','Europe','IE','Ireland',2963597),(70,'EU','Europe','IM','Isle of Man',3042225),(71,'AF','Africa','LY','Libya',2215636),(72,'EU','Europe','LU','Luxembourg',2960313),(73,'AS','Asia','AM','Armenia',174982),(74,'NA','North America','VG','British Virgin Islands',3577718),(75,'AS','Asia','YE','Yemen',69543),(76,'EU','Europe','BY','Belarus',630336),(77,'EU','Europe','GI','Gibraltar',2411586),(78,'AF','Africa','KE','Kenya',192950),(79,'SA','South America','CL','Chile',3895114),(80,'NA','North America','GP','Guadeloupe',3579143),(81,'NA','North America','MQ','Martinique',3570311),(82,'SA','South America','GF','French Guiana',3381670),(83,'AF','Africa','SC','Seychelles',241170),(84,'NA','North America','CR','Costa Rica',3624060),(85,'NA','North America','DO','Dominican Republic',3508796),(86,'NA','North America','CA','Canada',6251999),(87,'NA','North America','PR','Puerto Rico',4566966),(88,'NA','North America','VI','U.S. Virgin Islands',4796775),(89,'OC','Oceania','NZ','New Zealand',2186224),(90,'AS','Asia','SG','Singapore',1880251),(91,'AS','Asia','ID','Indonesia',1643084),(92,'AS','Asia','NP','Nepal',1282988),(93,'OC','Oceania','PG','Papua New Guinea',2088628),(94,'AS','Asia','PK','Pakistan',1168579),(95,'NA','North America','PA','Panama',3703430),(96,'NA','North America','BB','Barbados',3374084),(97,'NA','North America','BS','Bahamas',3572887),(98,'NA','North America','LC','Saint Lucia',3576468),(99,'SA','South America','AR','Argentina',3865483),(100,'AS','Asia','BD','Bangladesh',1210997),(101,'OC','Oceania','TK','Tokelau',4031074),(102,'AS','Asia','MO','Macao',1821275),(103,'AS','Asia','KH','Cambodia',1831722),(104,'AS','Asia','MV','Maldives',1282028),(105,'OC','Oceania','NC','New Caledonia',2139685),(106,'OC','Oceania','FJ','Fiji',2205218),(107,'AS','Asia','MN','Mongolia',2029969),(108,'OC','Oceania','WF','Wallis and Futuna',4034749),(109,'AS','Asia','QA','Qatar',289688),(110,'EU','Europe','AL','Albania',783754),(111,'AS','Asia','UZ','Uzbekistan',1512440),(112,'AS','Asia','KW','Kuwait',285570),(113,'EU','Europe','ME','Montenegro',3194884),(114,'NA','North America','BZ','Belize',3582678),(115,'AS','Asia','KP','North Korea',1873107),(116,'EU','Europe','VA','Vatican City',3164670),(117,'AN','Antarctica','AQ','Antarctica',6697173),(118,'SA','South America','PE','Peru',3932488),(119,'NA','North America','BM','Bermuda',3573345),(120,'NA','North America','CW','CuraÃ§ao',7626836),(121,'SA','South America','CO','Colombia',3686110),(122,'SA','South America','VE','Venezuela',3625428),(123,'SA','South America','EC','Ecuador',3658394),(124,'AF','Africa','ZA','South Africa',953987),(125,'SA','South America','BO','Bolivia',3923057),(126,'EU','Europe','GG','Guernsey',3042362),(127,'EU','Europe','MT','Malta',2562770),(128,'AS','Asia','TJ','Tajikistan',1220409),(129,'AS','Asia','BH','Bahrain',290291),(130,'AF','Africa','NG','Nigeria',2328926),(131,'AF','Africa','EG','Egypt',357994),(132,'AF','Africa','ZW','Zimbabwe',878675),(133,'AF','Africa','LR','Liberia',2275384),(134,'AF','Africa','GH','Ghana',2300660),(135,'AF','Africa','TZ','Tanzania',149590),(136,'AF','Africa','ZM','Zambia',895949),(137,'AF','Africa','NA','Namibia',3355338),(138,'AF','Africa','MG','Madagascar',1062947),(139,'AF','Africa','AO','Angola',3351879),(140,'AF','Africa','CI','Ivory Coast',2287781),(141,'AF','Africa','SD','Sudan',366755),(142,'AF','Africa','UG','Uganda',226074),(143,'AF','Africa','CM','Cameroon',2233387),(144,'AF','Africa','MW','Malawi',927384),(145,'AF','Africa','GA','Gabon',2400553),(146,'AF','Africa','ML','Mali',2453866),(147,'AF','Africa','BJ','Benin',2395170),(148,'AF','Africa','TD','Chad',2434508),(149,'AF','Africa','BW','Botswana',933860),(150,'AF','Africa','CV','Cape Verde',3374766),(151,'AF','Africa','RW','Rwanda',49518),(152,'AF','Africa','CG','Republic of the Congo',2260494),(153,'AF','Africa','MZ','Mozambique',1036973),(154,'AF','Africa','GM','Gambia',2413451),(155,'AF','Africa','LS','Lesotho',932692),(156,'AF','Africa','MU','Mauritius',934292),(157,'AF','Africa','MA','Morocco',2542007),(158,'AF','Africa','DZ','Algeria',2589581),(159,'AF','Africa','GN','Guinea',2420477),(160,'AF','Africa','CD','Congo',203312),(161,'AF','Africa','SZ','Swaziland',934841),(162,'AF','Africa','BF','Burkina Faso',2361809),(163,'AF','Africa','SL','Sierra Leone',2403846),(164,'AF','Africa','SO','Somalia',51537),(165,'AF','Africa','NE','Niger',2440476),(166,'AF','Africa','CF','Central African Republic',239880),(167,'AF','Africa','TG','Togo',2363686),(168,'AF','Africa','BI','Burundi',433561),(169,'AF','Africa','GQ','Equatorial Guinea',2309096),(170,'AF','Africa','SS','South Sudan',7909807),(171,'AF','Africa','SN','Senegal',2245662),(172,'AF','Africa','MR','Mauritania',2378080),(173,'AF','Africa','DJ','Djibouti',223816),(174,'AF','Africa','KM','Comoros',921929),(175,'AF','Africa','TN','Tunisia',2464461),(176,'NA','North America','GL','Greenland',3425505),(177,'EU','Europe','XK','Kosovo',831053),(178,'NA','North America','KY','Cayman Islands',3580718),(179,'NA','North America','JM','Jamaica',3489940),(180,'NA','North America','GT','Guatemala',3595528),(181,'OC','Oceania','MH','Marshall Islands',2080185),(182,'NA','North America','AW','Aruba',3577279),(183,'EU','Europe','MC','Monaco',2993457),(184,'NA','North America','AI','Anguilla',3573511),(185,'NA','North America','KN','Saint Kitts and Nevis',3575174),(186,'NA','North America','GD','Grenada',3580239),(187,'SA','South America','PY','Paraguay',3437598),(188,'NA','North America','MS','Montserrat',3578097),(189,'NA','North America','TC','Turks and Caicos Islands',3576916),(190,'NA','North America','AG','Antigua and Barbuda',3576396),(191,'OC','Oceania','TV','Tuvalu',2110297),(192,'OC','Oceania','PF','French Polynesia',4030656),(193,'OC','Oceania','SB','Solomon Islands',2103350),(194,'OC','Oceania','VU','Vanuatu',2134431),(195,'AF','Africa','ER','Eritrea',338010),(196,'NA','North America','TT','Trinidad and Tobago',3573591),(197,'EU','Europe','AD','Andorra',3041565),(198,'AF','Africa','SH','Saint Helena',3370751),(199,'NA','North America','SV','El Salvador',3585968),(200,'NA','North America','HN','Honduras',3608932),(201,'SA','South America','UY','Uruguay',3439705),(202,'AS','Asia','LK','Sri Lanka',1227603),(203,'AS','Asia','CX','Christmas Island',2078138),(204,'AS','Asia','IO','British Indian Ocean Territory',1282588),(205,'OC','Oceania','WS','Samoa',4034894),(206,'SA','South America','SR','Suriname',3382998),(207,'OC','Oceania','CK','Cook Islands',1899402),(208,'OC','Oceania','KI','Kiribati',4030945),(209,'OC','Oceania','NU','Niue',4036232),(210,'OC','Oceania','TO','Tonga',4032283),(211,'AN','Antarctica','TF','French Southern Territories',1546748),(212,'OC','Oceania','NF','Norfolk Island',2155115),(213,'AS','Asia','BN','Brunei',1820814),(214,'AS','Asia','TM','Turkmenistan',1218197),(215,'OC','Oceania','PN','Pitcairn Islands',4030699),(216,'EU','Europe','SM','San Marino',3168068),(217,'EU','Europe','AX','Ã…land',661882),(218,'EU','Europe','FO','Faroe Islands',2622320),(219,'EU','Europe','SJ','Svalbard and Jan Mayen',607072),(220,'AS','Asia','CC','Cocos [Keeling] Islands',1547376),(221,'OC','Oceania','NR','Nauru',2110425),(222,'OC','Oceania','UM','U.S. Minor Outlying Islands',5854968),(223,'NA','North America','SX','Sint Maarten',7609695),(224,'AF','Africa','GW','Guinea-Bissau',2372248),(225,'OC','Oceania','PW','Palau',1559582),(226,'OC','Oceania','AS','American Samoa',5880801),(227,'AS','Asia','BT','Bhutan',1252634),(228,'NA','North America','MF','Saint Martin',3578421),(229,'NA','North America','VC','Saint Vincent and the Grenadines',3577815),(230,'NA','North America','PM','Saint Pierre and Miquelon',3424932),(231,'NA','North America','BL','Saint BarthÃ©lemy',3578476),(232,'NA','North America','DM','Dominica',3575830),(233,'AF','Africa','ST','SÃ£o TomÃ© and PrÃ­ncipe',2410758),(234,'SA','South America','FK','Falkland Islands',3474414),(235,'OC','Oceania','MP','Northern Mariana Islands',4041468),(236,'OC','Oceania','TL','East Timor',1966436),(237,'NA','North America','BQ','Bonaire',7626844),(238,'OC','Oceania','GU','Guam',4043988),(239,'OC','Oceania','FM','Micronesia',2081918),(240,'SA','South America','GY','Guyana',3378535),(241,'NA','North America','NI','Nicaragua',3617476),(242,'AS','Asia','MM','Myanmar [Burma]',1327865),(243,'NA','North America','HT','Haiti',3723988),(244,'AS','Asia','LA','Laos',1655842),(245,'NA','North America','CU','Cuba',3562981),(246,'AF','Africa','ET','Ethiopia',337996);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency`
--

LOCK TABLES `currency` WRITE;
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
INSERT INTO `currency` VALUES (1,'AFN','Afghanistan','Afghanistan Afghani'),(2,'ALL','Albania','Albanian Lek'),(3,'DZD','Algeria','Algerian Dinar'),(4,'USD','American Samoa','US Dollar'),(5,'EUR','Andorra','Euro'),(6,'AOA','Angola','Angolan Kwanza'),(7,'XCD','Anguilla','East Caribbean Dollar'),(8,'XCD','Antarctica','East Caribbean Dollar'),(9,'XCD','Antigua and Barbuda','East Caribbean Dollar'),(10,'ARS','Argentina','Argentine Peso'),(11,'AMD','Armenia','Armenian Dram'),(12,'AWG','Aruba','Aruban Guilder'),(13,'AUD','Australia','Australian Dollar'),(14,'EUR','Austria','Euro'),(15,'AZN','Azerbaijan','Azerbaijan New Manat'),(16,'BSD','Bahamas','Bahamian Dollar'),(17,'BHD','Bahrain','Bahraini Dinar'),(18,'BDT','Bangladesh','Bangladeshi Taka'),(19,'BBD','Barbados','Barbados Dollar'),(20,'BYR','Belarus','Belarussian Ruble'),(21,'EUR','Belgium','Euro'),(22,'BZD','Belize','Belize Dollar'),(23,'XOF','Benin','CFA Franc BCEAO'),(24,'BMD','Bermuda','Bermudian Dollar'),(25,'BTN','Bhutan','Bhutan Ngultrum'),(26,'BOB','Bolivia','Boliviano'),(27,'BAM','Bosnia-Herzegovina','Marka'),(28,'BWP','Botswana','Botswana Pula'),(29,'NOK','Bouvet Island','Norwegian Krone'),(30,'BRL','Brazil','Brazilian Real'),(31,'USD','British Indian Ocean Territory','US Dollar'),(32,'BND','Brunei Darussalam','Brunei Dollar'),(33,'BGN','Bulgaria','Bulgarian Lev'),(34,'XOF','Burkina Faso','CFA Franc BCEAO'),(35,'BIF','Burundi','Burundi Franc'),(36,'KHR','Cambodia','Kampuchean Riel'),(37,'XAF','Cameroon','CFA Franc BEAC'),(38,'CAD','Canada','Canadian Dollar'),(39,'CVE','Cape Verde','Cape Verde Escudo'),(40,'KYD','Cayman Islands','Cayman Islands Dollar'),(41,'XAF','Central African Republic','CFA Franc BEAC'),(42,'XAF','Chad','CFA Franc BEAC'),(43,'CLP','Chile','Chilean Peso'),(44,'CNY','China','Yuan Renminbi'),(45,'AUD','Christmas Island','Australian Dollar'),(46,'AUD','Cocos (Keeling) Islands','Australian Dollar'),(47,'COP','Colombia','Colombian Peso'),(48,'KMF','Comoros','Comoros Franc'),(49,'XAF','Congo',',CFA Franc BEAC'),(50,'CDF','Congo, Dem. Republic','Francs'),(51,'NZD','Cook Islands','New Zealand Dollar'),(52,'CRC','Costa Rica','Costa Rican Colon'),(53,'HRK','Croatia','Croatian Kuna'),(54,'CUP','Cuba','Cuban Peso'),(55,'EUR','Cyprus','Euro'),(56,'CZK','Czech Rep.','Czech Koruna'),(57,'DKK','Denmark','Danish Krone'),(58,'DJF','Djibouti','Djibouti Franc'),(59,'XCD','Dominica','East Caribbean Dollar'),(60,'DOP','Dominican Republic','Dominican Peso'),(61,'ECS','Ecuador','Ecuador Sucre'),(62,'EGP','Egypt','Egyptian Pound'),(63,'SVC','El Salvador','El Salvador Colon'),(64,'XAF','Equatorial Guinea','CFA Franc BEAC'),(65,'ERN','Eritrea','Eritrean Nakfa'),(66,'EUR','Estonia','Euro'),(67,'ETB','Ethiopia','Ethiopian Birr'),(68,'EUR','European Union','Euro'),(69,'FKP','Falkland Islands (Malvinas)','Falkland Islands Pound'),(70,'DKK','Faroe Islands','Danish Krone'),(71,'FJD','Fiji','Fiji Dollar'),(72,'EUR','Finland','Euro'),(73,'EUR','France','Euro'),(74,'EUR','French Guiana','Euro'),(75,'EUR','French Southern Territories','Euro'),(76,'XAF','Gabon','CFA Franc BEAC'),(77,'GMD','Gambia','Gambian Dalasi'),(78,'GEL','Georgia','Georgian Lari'),(79,'EUR','Germany','Euro'),(80,'GHS','Ghana','Ghanaian Cedi'),(81,'GIP','Gibraltar','Gibraltar Pound'),(82,'GBP','Great Britain','Pound Sterling'),(83,'EUR','Greece','Euro'),(84,'DKK','Greenland','Danish Krone'),(85,'XCD','Grenada','East Carribean Dollar'),(86,'EUR','Guadeloupe (French)','Euro'),(87,'USD','Guam (USA)','US Dollar'),(88,'QTQ','Guatemala','Guatemalan Quetzal'),(89,'GGP','Guernsey','Pound Sterling'),(90,'GNF','Guinea','Guinea Franc'),(91,'GWP','Guinea Bissau','Guinea-Bissau Peso'),(92,'GYD','Guyana','Guyana Dollar'),(93,'HTG','Haiti','Haitian Gourde'),(94,'AUD','Heard Island and McDonald Islands','Australian Dollar'),(95,'HNL','Honduras','Honduran Lempira'),(96,'HKD','Hong Kong','Hong Kong Dollar'),(97,'HUF','Hungary','Hungarian Forint'),(98,'ISK','Iceland','Iceland Krona'),(99,'INR','India','Indian Rupee'),(100,'IDR','Indonesia','Indonesian Rupiah'),(101,'IRR','Iran','Iranian Rial'),(102,'IQD','Iraq','Iraqi Dinar'),(103,'EUR','Ireland','Euro'),(104,'GBP','Isle of Man','Pound Sterling'),(105,'ILS','Israel','Israeli New Shekel'),(106,'EUR','Italy','Euro'),(107,'XOF','Ivory Coast','CFA Franc BCEAO'),(108,'JMD','Jamaica','Jamaican Dollar'),(109,'JPY','Japan','Japanese Yen'),(110,'GBP','Jersey','Pound Sterling'),(111,'JOD','Jordan','Jordanian Dinar'),(112,'KZT','Kazakhstan','Kazakhstan Tenge'),(113,'KES','Kenya','Kenyan Shilling'),(114,'AUD','Kiribati','Australian Dollar'),(115,'KPW','Korea-North','North Korean Won'),(116,'KRW','Korea-South','Korean Won'),(117,'KWD','Kuwait','Kuwaiti Dinar'),(118,'KGS','Kyrgyzstan','Som'),(119,'LAK','Laos','Lao Kip'),(120,'LVL','Latvia','Latvian Lats'),(121,'LBP','Lebanon','Lebanese Pound'),(122,'LSL','Lesotho','Lesotho Loti'),(123,'LRD','Liberia','Liberian Dollar'),(124,'LYD','Libya','Libyan Dinar'),(125,'CHF','Liechtenstein','Swiss Franc'),(126,'LTL','Lithuania','Lithuanian Litas'),(127,'EUR','Luxembourg','Euro'),(128,'MOP','Macau','Macau Pataca'),(129,'MKD','Macedonia','Denar'),(130,'MGF','Madagascar','Malagasy Franc'),(131,'MWK','Malawi','Malawi Kwacha'),(132,'MYR','Malaysia','Malaysian Ringgit'),(133,'MVR','Maldives','Maldive Rufiyaa'),(134,'XOF','Mali','CFA Franc BCEAO'),(135,'EUR','Malta','Euro'),(136,'USD','Marshall Islands','US Dollar'),(137,'EUR','Martinique (French)','Euro'),(138,'MRO','Mauritania','Mauritanian Ouguiya'),(139,'MUR','Mauritius','Mauritius Rupee'),(140,'EUR','Mayotte','Euro'),(141,'MXN','Mexico','Mexican Nuevo Peso'),(142,'USD','Micronesia','US Dollar'),(143,'MDL','Moldova','Moldovan Leu'),(144,'EUR','Monaco','Euro'),(145,'MNT','Mongolia','Mongolian Tugrik'),(146,'EUR','Montenegro','Euro'),(147,'XCD','Montserrat','East Caribbean Dollar'),(148,'MAD','Morocco','Moroccan Dirham'),(149,'MZN','Mozambique','Mozambique Metical'),(150,'MMK','Myanmar','Myanmar Kyat'),(151,'NAD','Namibia','Namibian Dollar'),(152,'AUD','Nauru','Australian Dollar'),(153,'NPR','Nepal','Nepalese Rupee'),(154,'EUR','Netherlands','Euro'),(155,'ANG','Netherlands Antilles','Netherlands Antillean Guilder'),(156,'XPF','New Caledonia (French)','CFP Franc'),(157,'NZD','New Zealand','New Zealand Dollar'),(158,'NIO','Nicaragua','Nicaraguan Cordoba Oro'),(159,'XOF','Niger','CFA Franc BCEAO'),(160,'NGN','Nigeria','Nigerian Naira'),(161,'NZD','Niue','New Zealand Dollar'),(162,'AUD','Norfolk Island','Australian Dollar'),(163,'USD','Northern Mariana Islands','US Dollar'),(164,'NOK','Norway','Norwegian Krone'),(165,'OMR','Oman','Omani Rial'),(166,'PKR','Pakistan','Pakistan Rupee'),(167,'USD','Palau','US Dollar'),(168,'PAB','Panama','Panamanian Balboa'),(169,'PGK','Papua New Guinea','Papua New Guinea Kina'),(170,'PYG','Paraguay','Paraguay Guarani'),(171,'PEN','Peru','Peruvian Nuevo Sol'),(172,'PHP','Philippines','Philippine Peso'),(173,'NZD','Pitcairn Island','New Zealand Dollar'),(174,'PLN','Poland','Polish Zloty'),(175,'XPF','Polynesia (French)','CFP Franc'),(176,'EUR','Portugal','Euro'),(177,'USD','Puerto Rico','US Dollar'),(178,'QAR','Qatar','Qatari Rial'),(179,'EUR','Reunion (French)','Euro'),(180,'RON','Romania','Romanian New Leu'),(181,'RUB','Russia','Russian Ruble'),(182,'RWF','Rwanda','Rwanda Franc'),(183,'SHP','Saint Helena','St. Helena Pound'),(184,'XCD','Saint Kitts & Nevis Anguilla','East Caribbean Dollar'),(185,'XCD','Saint Lucia','East Caribbean Dollar'),(186,'EUR','Saint Pierre and Miquelon','Euro'),(187,'XCD','Saint Vincent & Grenadines','East Caribbean Dollar'),(188,'WST','Samoa','Samoan Tala'),(189,'EUR','San Marino','Euro'),(190,'STD','Sao Tome and Principe','Dobra'),(191,'SAR','Saudi Arabia','Saudi Riyal'),(192,'XOF','Senegal','CFA Franc BCEAO'),(193,'RSD','Serbia','Dinar'),(194,'SCR','Seychelles','Seychelles Rupee'),(195,'SLL','Sierra Leone','Sierra Leone Leone'),(196,'SGD','Singapore','Singapore Dollar'),(197,'EUR','Slovakia','Euro'),(198,'EUR','Slovenia','Euro'),(199,'SBD','Solomon Islands','Solomon Islands Dollar'),(200,'SOS','Somalia','Somali Shilling'),(201,'ZAR','South Africa','South African Rand'),(202,'GBP','South Georgia & South Sandwich Islands','Pound Sterling'),(203,'SSP','South Sudan','South Sudan Pound'),(204,'EUR','Spain','Euro'),(205,'LKR','Sri Lanka','Sri Lanka Rupee'),(206,'SDG','Sudan','Sudanese Pound'),(207,'SRD','Suriname','Surinam Dollar'),(208,'NOK','Svalbard and Jan Mayen Islands','Norwegian Krone'),(209,'SZL','Swaziland','Swaziland Lilangeni'),(210,'SEK','Sweden','Swedish Krona'),(211,'CHF','Switzerland','Swiss Franc'),(212,'SYP','Syria','Syrian Pound'),(213,'TWD','Taiwan','Taiwan Dollar'),(214,'TJS','Tajikistan','Tajik Somoni'),(215,'TZS','Tanzania','Tanzanian Shilling'),(216,'THB','Thailand','Thai Baht'),(217,'XOF','Togo','CFA Franc BCEAO'),(218,'NZD','Tokelau','New Zealand Dollar'),(219,'TOP','Tonga','Tongan Paanga'),(220,'TTD','Trinidad and Tobago','Trinidad and Tobago Dollar'),(221,'TND','Tunisia','Tunisian Dollar'),(222,'TRY','Turkey','Turkish Lira'),(223,'TMT','Turkmenistan','Manat'),(224,'USD','Turks and Caicos Islands','US Dollar'),(225,'AUD','Tuvalu','Australian Dollar'),(226,'GBP','U.K.','Pound Sterling'),(227,'UGX','Uganda','Uganda Shilling'),(228,'UAH','Ukraine','Ukraine Hryvnia'),(229,'AED','United Arab Emirates','Arab Emirates Dirham'),(230,'UYU','Uruguay','Uruguayan Peso'),(231,'USD','USA','US Dollar'),(232,'USD','USA Minor Outlying Islands','US Dollar'),(233,'UZS','Uzbekistan','Uzbekistan Sum'),(234,'VUV','Vanuatu','Vanuatu Vatu'),(235,'EUR','Vatican','Euro'),(236,'VEF','Venezuela','Venezuelan Bolivar'),(237,'VND','Vietnam','Vietnamese Dong'),(238,'USD','Virgin Islands (British)','US Dollar'),(239,'USD','Virgin Islands (USA)','US Dollar'),(240,'XPF','Wallis and Futuna Islands','CFP Franc'),(241,'MAD','Western Sahara','Moroccan Dirham'),(242,'YER','Yemen','Yemeni Rial'),(243,'ZMW','Zambia','Zambian Kwacha'),(244,'ZWD','Zimbabwe','Zimbabwe Dollar');
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emailAddress` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email`
--

LOCK TABLES `email` WRITE;
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
INSERT INTO `email` VALUES (1,'invoicebinder@gmail.com'),(2,'stone@meekness.com'),(3,'JoyceJMattocks@teleworm.us'),(4,'ca-tech@dps.centrin.net.id'),(5,'trinanda_lestyowati@telkomsel.co.id'),(6,'asst_dos@astonrasuna.com'),(7,'amartabali@dps.centrin.net.id'),(8,'achatv@cbn.net.id'),(9,'bali@tuguhotels.com'),(10,'baliminimalist@yahoo.com'),(11,'bliss@thebale.com'),(12,'adhidharma@denpasar.wasantara.net.id'),(13,'centralreservation@ramayanahotel.com'),(14,'apribadi@balimandira.com'),(15,'cdagenhart@ifc.org'),(16,'dana_supriyanto@interconti.com'),(17,'dos@novotelbali.com'),(18,'daniel@hotelpadma.com'),(19,'daniel@balibless.com'),(20,'djoko_p@jayakartahotelsresorts.com'),(21,'expdepot@indosat.net.id'),(22,'feby.adamsyah@idn.xerox.com'),(23,'christian_rizal@interconti.com'),(24,'singgih93@mailcity.com'),(25,'idonk_gebhoy@yahoo.com'),(26,'info@houseofbali.com'),(27,'kyohana@toureast.net'),(28,'sales@nusaduahotel.com'),(29,'jayakarta@mataram.wasantara.net.id'),(30,'mapindo@indo.net.id'),(31,'sm@ramayanahotel.com'),(32,'anekabeach@dps.centrin.net.id'),(33,'yogya@jayakartahotelsresorts.com'),(34,'garudawisatajaya@indo.net.id'),(35,'ketut@kbatur.com'),(36,'bondps@bonansatours.com'),(37,'witamgr@dps.centrin.net.id'),(38,'dtedja@indosat.net.id'),(39,'info@stpbali.ac.id'),(40,'baliprestigeho@dps.centrin.net.id'),(41,'pamilu@mas-travel.com'),(42,'amandabl@indosat.net.id'),(43,'marketing@csdwholiday.com'),(44,'luha89@yahoo.com'),(45,'indahsuluh2002@yahoo.com.sg'),(46,'imz1991@yahoo.com'),(47,'gus_war81@yahoo.com'),(48,'kf034@indosat.net.id'),(49,'800produkwil@posindonesia.co.id'),(50,'kontak.synergi@yahoo.com'),(51,'oekaoeka@yahoo.com'),(52,'fitrianti@hotmail.com'),(53,'meylina310@yahoo.com'),(54,'h4ntoro@yahoo.com'),(55,'novi_enbe@yahoo.com'),(56,'dila_dewata@yahoo.co.id'),(57,'tiena_asfary@yahoo.co.id'),(58,'da_lawoffice@yahoo.com'),(59,'rini@ncsecurities.biz'),(60,'sudarnoto_hakim@yahoo.com'),(61,'wastioke@yahoo.com'),(62,'leebahri@yahoo.com'),(63,'lia_kiara97@yahoo.com'),(64,'rido@weddingku.com'),(65,'b_astuti@telkomsel.co.id'),(66,'garudawisata@indo.net.id'),(67,'grfurniture@yahoo.com'),(68,'gosyen2000@hotmail.com'),(69,'hvhfood@indosat.net.id'),(70,'hr@astonbali.com'),(71,'hary@wibisono-family.com'),(72,'fadlycakp@yahoo.com'),(73,'ida_sampurniah@telkomsel.co.id'),(74,'muslim-pariwisata-bali@yahoogroups.com'),(75,'harisnira@yahoo.com'),(76,'sales@houseofbali.com'),(77,'baim_ron@yahoo.com'),(78,'ilhambali222@yahoo.com'),(79,'bungjon@gmail.com'),(80,'diar@bdg.centrin.net.id'),(81,'elmienruge@hotmail.com'),(82,'galaxygarden2006@yahoo.com'),(83,'gorisata@indosat.net.id'),(84,'maulitasarihani@yahoo.com'),(85,'hamiluddakwah@gmail.com.au'),(86,'bounty@indo.net.id'),(87,'michi@ritzcarlton-bali.com'),(88,'orridor@dps.centrin.net.id'),(89,'ngumina@hotmail.com'),(90,'made@mas-travel.com'),(91,'evi@mas-travel.com'),(92,'wibawa@mas-travel.com'),(93,'saihubaly@yahoo.co.id'),(94,'swa_candra@yahoo.com'),(95,'picapica@denpasar.wasantara.net.id'),(96,'griyasantrian@santrian.com'),(97,'yuni6671@gmail.com'),(98,'phbalichef@indo.net.id'),(99,'vendra@keratonjimbaranresort.com'),(100,'bali@pansea.com'),(101,'sales@legianbeachbali.com');
/*!40000 ALTER TABLE `email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expense`
--

DROP TABLE IF EXISTS `expense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expense` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `expenseDate` date DEFAULT NULL,
  `expenseType` int(11) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense`
--

LOCK TABLES `expense` WRITE;
/*!40000 ALTER TABLE `expense` DISABLE KEYS */;
INSERT INTO `expense` VALUES (1,340.00,'2017-01-01',0,'purchased equipment'),(2,300.00,'2017-01-10',1,'purchased computer'),(3,240.00,'2016-12-15',1,'purchased furniture'),(4,260.00,'2016-10-15',1,'tech upgrade'),(5,300.00,'2016-11-10',1,'purchased computer'),(6,240.00,'2017-02-15',1,'purchased furniture'),(7,260.00,'2017-03-15',1,'tech upgrade'),(8,110.00,'2017-04-15',1,'tech upgrade'),(9,160.00,'2017-03-15',1,'tech upgrade');
/*!40000 ALTER TABLE `expense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr10` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `attr4` varchar(255) DEFAULT NULL,
  `attr5` varchar(255) DEFAULT NULL,
  `attr6` varchar(255) DEFAULT NULL,
  `attr7` varchar(255) DEFAULT NULL,
  `attr8` varchar(255) DEFAULT NULL,
  `attr9` varchar(255) DEFAULT NULL,
  `authToken` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `groupName` varchar(255) DEFAULT NULL,
  `invoiceDate` date DEFAULT NULL,
  `invoiceNumber` varchar(255) DEFAULT NULL,
  `invoiceStatus` varchar(255) DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `purchOrdNumber` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `clientId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_invoice_1` (`invoiceNumber`),
  KEY `FK74D6432DC45B8EE6` (`clientId`),
  CONSTRAINT `FK74D6432DC45B8EE6` FOREIGN KEY (`clientId`) REFERENCES `client` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (1,230.84,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298bc10-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-01-01',NULL,'2017-01-01','INV1','DRAFT','2017-01-02','PO2121','S',1),(2,252.89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298bf26-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-01-10',NULL,'2017-01-01','INV2','PAID','2017-01-10','PO2122','S',2),(3,241.84,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298c16a-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-01-01',NULL,'2017-01-01','INV3','PAID','2017-01-02','PO2123','S',3),(4,417.78,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298c3fe-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-12-10',NULL,'2017-12-01','INV4','PAID','2017-12-12','PO2124','S',4),(5,54.95,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298c516-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-12-10',NULL,'2017-12-01','INV5','PAID','2017-12-10','PO2125','S',5),(6,417.78,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298c5f2-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-11-10',NULL,'2017-11-10','INV6','PAID','2017-11-12','PO2126','S',6),(7,241.84,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298c7be-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-02-10',NULL,'2017-02-10','INV7','PAID','2017-02-10','PO2127','S',7),(8,54.95,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298c890-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-04-10',NULL,'2017-04-10','INV8','PAID','2017-04-11','PO2128','S',8),(9,252.89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298cbc4-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2014-06-10',NULL,'2017-06-11','INV9','PAID','2017-06-14','PO2129','S',2),(10,252.89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298ccd2-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-02-10',NULL,'2017-03-01','INV10','DRAFT','2017-03-01','PO2130','S',2),(11,252.89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298cf16-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-06-28',NULL,'2017-06-28','INV11','PAID','2017-06-29','PO2131','S',2),(12,242.89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298cff2-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-06-27',NULL,'2017-06-27','INV12','DRAFT','2017-07-27','PO2132','S',2),(13,252.89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298d0ba-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-06-27',NULL,'2017-06-27','INV13','DRAFT','2017-07-27','PO2133','S',2),(14,252.89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298d2f4-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-06-27',NULL,'2017-06-27','INV14','DRAFT','2017-07-27','PO2134','S',2),(15,252.89,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298d3bc-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-02-27',NULL,'2017-02-27','INV15','DRAFT','2017-02-27','PO2135','S',2),(16,417.78,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298d5d8-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-03-10',NULL,'2017-03-10','INV16','PAID','2017-03-12','PO2136','S',6),(17,417.78,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298d6aa-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-06-10',NULL,'2017-06-10','INV17','PAID','2017-06-12','PO2137','S',6),(18,417.78,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298d77c-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-06-10',NULL,'2017-06-10','INV18','PAID','2017-06-12','PO2138','S',6),(19,417.78,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298d9b6-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-07-10',NULL,'2017-07-10','INV19','PAID','2017-07-12','PO2139','S',6),(20,417.78,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298da92-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-08-10',NULL,'2017-08-10','INV20','PAID','2017-08-12','PO2140','S',6),(21,417.78,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'7298dcea-5cc9-11e7-907b-a6006ad3dba0','thank you for your business','2017-09-10',NULL,'2017-09-10','INV21','PAID','2017-09-12','PO2141','S',6);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoiceitem`
--

DROP TABLE IF EXISTS `invoiceitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoiceitem` (
  `invoice_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  KEY `FKCCA85FC076B86147` (`item_id`),
  KEY `FKCCA85FC09641F58B` (`invoice_id`),
  CONSTRAINT `FKCCA85FC076B86147` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `FKCCA85FC09641F58B` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoiceitem`
--

LOCK TABLES `invoiceitem` WRITE;
/*!40000 ALTER TABLE `invoiceitem` DISABLE KEYS */;
INSERT INTO `invoiceitem` VALUES (1,1),(1,2),(1,3),(2,4),(2,5),(3,6),(3,7),(3,8),(4,9),(4,10),(4,11),(5,12),(6,13),(6,14),(6,15),(7,16),(7,17),(7,18),(8,19),(9,20),(9,21),(10,22),(10,23),(11,24),(11,25),(12,26),(12,27),(13,28),(13,29),(14,30),(14,31),(15,32),(15,33),(16,34),(16,35),(16,36),(17,37),(17,38),(17,39),(18,40),(18,41),(18,42),(19,43),(19,44),(19,45),(20,46),(20,47),(20,48),(21,49),(21,50),(21,51);
/*!40000 ALTER TABLE `invoiceitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `Qty` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,1,'Living Doll','Living Doll',49.95),(2,1,'Sunny Girl','Sunny Girl',59.95),(3,1,'Espirit','Espirit',99.95),(4,1,'Espirit','Espirit',99.95),(5,1,'Martini','Martini',129.95),(6,1,'Espirit','Espirit',99.95),(7,1,'Firefly','Firefly',59.95),(8,1,'Sunny Girl','Sunny Girl',59.95),(9,2,'Espirit','Espirit',99.95),(10,1,'Martini','Martini',129.95),(11,1,'Living Doll','Living Doll',49.95),(12,1,'Living Doll','Living Doll',49.95),(13,2,'Espirit','Espirit',99.95),(14,1,'Martini','Martini',129.95),(15,1,'Living Doll','Living Doll',49.95),(16,1,'Espirit','Espirit',99.95),(17,1,'Firefly','Firefly',59.95),(18,1,'Sunny Girl','Sunny Girl',59.95),(19,1,'Living Doll','Living Doll',49.95),(20,1,'Espirit','Espirit',99.95),(21,1,'Martini','Martini',129.95),(22,1,'Espirit','Espirit',99.95),(23,1,'Martini','Martini',129.95),(24,1,'Espirit','Espirit',99.95),(25,1,'Martini','Martini',129.95),(26,1,'Espirit','Espirit',99.95),(27,1,'Martini','Martini',129.95),(28,1,'Espirit','Espirit',99.95),(29,1,'Martini','Martini',129.95),(30,1,'Espirit','Espirit',99.95),(31,1,'Martini','Martini',129.95),(32,1,'Espirit','Espirit',99.95),(33,1,'Martini','Martini',129.95),(34,2,'Espirit','Espirit',99.95),(35,1,'Martini','Martini',129.95),(36,1,'Living Doll','Living Doll',49.95),(37,2,'Espirit','Espirit',99.95),(38,1,'Martini','Martini',129.95),(39,1,'Living Doll','Living Doll',49.95),(40,2,'Espirit','Espirit',99.95),(41,1,'Martini','Martini',129.95),(42,1,'Living Doll','Living Doll',49.95),(43,2,'Espirit','Espirit',99.95),(44,1,'Martini','Martini',129.95),(45,1,'Living Doll','Living Doll',49.95),(46,2,'Espirit','Espirit',99.95),(47,1,'Martini','Martini',129.95),(48,1,'Living Doll','Living Doll',49.95),(49,2,'Espirit','Espirit',99.95),(50,1,'Martini','Martini',129.95),(51,1,'Living Doll','Living Doll',49.95);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `currencyCode` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `paymentStatus` varchar(255) DEFAULT NULL,
  `paymentType` varchar(255) DEFAULT NULL,
  `providerRef` varchar(255) DEFAULT NULL,
  `referenceNo` varchar(255) DEFAULT NULL,
  `clientId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD11C3206C45B8EE6` (`clientId`),
  CONSTRAINT `FKD11C3206C45B8EE6` FOREIGN KEY (`clientId`) REFERENCES `client` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (1,100.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12122',1),(2,130.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12123',2),(3,110.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12122',3),(4,1300.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12122',4),(5,12.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12122',5),(6,1630.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12122',6),(7,1430.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12122',7),(8,1330.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12122',8),(9,1320.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12122',9),(10,1110.00,'AUD',NULL,'2015-12-31','PAID','CASH',NULL,'12122',10);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unitprice` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'A soft flowing maxi dress with ruffles and a high low hem, it\'s wonderful for those warm days when you want to be cool yet feel fantastic.','Living Doll',49.95),(2,' Horizontal stripes, longer length and side splits, we are going to be great friends.','Sunny Girl',59.95),(3,'Drift through the warmer months without a care in the world in your Border Paisley Strap Maxi Dress from Esprit.','Espirit',99.95),(4,'Brought to you by Jersey Girl, the Lolita V Neck Maxi Dress is a slinky piece, deliciously garnished with flowers fresh from a country garden.','Jersey Girl',159.00),(5,'Way down yonder there is a lovely lady in a long flowing dress with a soft knitted scarf around her neck.','Firefly',59.95),(6,'Think slinky, feminine styling of 40s icons in evening gowns at dazzling soirÃ©es. Wear your best jewels, killer heels and a mega watt smile.','Martini',129.95),(7,'The Lil Lots Dress by Ladakh will have you feeling flirty and sweet. Just add some little flats to finish off your look.','Ladhakh',89.95),(8,'The Pippa Silk Dress by Naudic is the epitome of elegance. Classic black drapes beautfully over your body, skimming you and providing a foolproof blank canvas for your outfit.','Naudic',119.95),(9,'Be bold in the Papercut Dress by Sass. This eye catching dress will look stunning with black heels and a bight blazer.','Sass',69.95),(10,'The comfortable and easy flow of the fabric in the Swing Layer Dress is flattering and feminine. Express your individuality by adding colourful, interesting accessories.','Neemah',69.95);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'SYSTEM'),(2,'ADMIN'),(3,'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unitprice` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sitetemplatedata`
--

DROP TABLE IF EXISTS `sitetemplatedata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sitetemplatedata` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `TemplateID` varchar(255) DEFAULT NULL,
  `dataKey` varchar(255) DEFAULT NULL,
  `dataValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sitetemplatedata`
--

LOCK TABLES `sitetemplatedata` WRITE;
/*!40000 ALTER TABLE `sitetemplatedata` DISABLE KEYS */;
/*!40000 ALTER TABLE `sitetemplatedata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country_iso_code` varchar(255) DEFAULT NULL,
  `state_code` varchar(255) DEFAULT NULL,
  `state_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES (1,'AR','V','TIERRA DEL FUEGO'),(2,'AR','L','LA PAMPA'),(3,'AR','A','SALTA'),(4,'AR','R','RIO NEGRO'),(5,'AR','G','SANTIAGO DEL ESTERO'),(6,'AR','W','CORRIENTES'),(7,'AR','M','MENDOZA'),(8,'AR','B','BUENOS AIRES'),(9,'AR','S','SANTA FE'),(10,'AR','H','CHACO'),(11,'AR','X','CORDOBA'),(12,'AR','N','MISIONES'),(13,'AR','D','SAN LUIS'),(14,'AR','T','TUCUMAN'),(15,'AR','J','SAN JUAN'),(16,'AR','Y','JUJUY'),(17,'AR','P','FORMOSA'),(18,'AR','E','ENTRE RIOS'),(19,'AR','U','CHUBUT'),(20,'AR','K','CATAMARCA'),(21,'AR','Z','SANTA CRUZ'),(22,'AR','Q','NEUQUEN'),(23,'AR','F','LA RIOJA'),(24,'AS','60','As'),(25,'AT','5','Salzburg'),(26,'AT','1','Burgenland'),(27,'AT','6','Steiermark'),(28,'AT','2','Kaernten'),(29,'AT','7','Tirol'),(30,'AT','8','Vorarlberg'),(31,'AU','SA','South Australia'),(32,'AU','ACT','Australian Capital Territory'),(33,'AU','TAS','Tasmania'),(34,'AU','NSW','New South Wales'),(35,'AU','VIC','Victoria'),(36,'AU','NT','Northern Territory'),(37,'AU','WA','Western Australia'),(38,'AU','QLD','Queensland'),(39,'BE','BRU','Bruxelles-Capitale'),(40,'BE','VLG','Vlaanderen'),(41,'BE','WAL','Wallonie'),(42,'BR','23','Rio Grande do Sul'),(43,'BR','13','Maranhao'),(44,'BR','28','Sergipe'),(45,'BR','18','Parana'),(46,'BR','24','Rondonia'),(47,'BR','14','Mato Grosso'),(48,'BR','29','Goias'),(49,'BR','20','Piaui'),(50,'BR','25','Roraima'),(51,'BR','15','Minas Gerais'),(52,'BR','30','Pernambuco'),(53,'BR','21','Rio de Janeiro'),(54,'BR','26','Santa Catarina'),(55,'BR','16','Para'),(56,'BR','31','Tocantins'),(57,'BR','22','Rio Grande do Norte'),(58,'BR','11','Mato Grosso do Sul'),(59,'BR','27','Sao Paulo'),(60,'BR','17','Paraiba'),(61,'CA','AB','Alberta'),(62,'CA','NS','Nova Scotia'),(63,'CA','SK','Saskatchewan'),(64,'CA','BC','British Columbia'),(65,'CA','NU','Nunavut Territory'),(66,'CA','YT','Yukon'),(67,'CA','MB','Manitoba'),(68,'CA','ON','Ontario'),(69,'CA','NB','New Brunswick'),(70,'CA','PE','Prince Edward Island'),(71,'CA','NL','Newfoundland and Labrador'),(72,'CA','QC','Quebec'),(73,'CH','TI','Cantone Ticino'),(74,'CH','LU','Kanton Luzern'),(75,'CH','AR','Kanton Appenzell Ausserrhoden'),(76,'CH','SH','Kanton Schaffhausen'),(77,'CH','UR','Kanton Uri'),(78,'CH','BE','Kanton Bern'),(79,'CH','SO','Kanton Solothurn'),(80,'CH','GL','Kanton Glarus'),(81,'CH','VD','Canton de Vaud'),(82,'CH','NW','Kanton Nidwalden'),(83,'CH','BL','Kanton Basel-Landschaft'),(84,'CH','SZ','Kanton Schwyz'),(85,'CH','AG','Kanton Aargau'),(86,'CH','VS','Canton du Valais'),(87,'CH','OW','Kanton Obwalden'),(88,'CH','BS','Kanton Basel-Stadt'),(89,'CH','TG','Kanton Thurgau'),(90,'CH','JU','Canton du Jura'),(91,'CH','AI','Kanton Appenzell Innerrhoden'),(92,'CH','ZG','Kanton Zug'),(93,'CH','SG','Kanton St. Gallen'),(94,'CH','FR','Canton de Fribourg'),(95,'DE','RP','Rheinland-Pfalz'),(96,'DE','HE','Hessen'),(97,'DE','SL','Saarland'),(98,'DE','BB','Brandenburg'),(99,'DE','HH','Hamburg'),(100,'DE','SN','Sachsen'),(101,'DE','MV','Mecklenburg-Vorpommern'),(102,'DE','ST','Sachsen-Anhalt'),(103,'DE','BY','Bayern'),(104,'DE','NI','Niedersachsen'),(105,'DE','HB','Bremen'),(106,'DK','1081','Region Nordjylland'),(107,'DK','1082','Region Midtjylland'),(108,'DK','1083','Region Syddanmark'),(109,'DK','1084','Region Hovedstaden'),(110,'DZ','42','Tipaza'),(111,'DZ','47','Ghardaia'),(112,'DZ','33','Illizi'),(113,'DZ','38','Tissemsilt'),(114,'DZ','43','Mila'),(115,'DZ','10','Bouira'),(116,'DZ','48','Relizane'),(117,'DZ','34','Bordj-Bou-Arreridj'),(118,'DZ','39','El-Oued'),(119,'DZ','44','Ain-Defla'),(120,'DZ','12','Tebessa'),(121,'DZ','35','Boumerdes'),(122,'DZ','40','Khenchela'),(123,'DZ','45','Naama'),(124,'DZ','19','Setif'),(125,'DZ','36','El-Taref'),(126,'DZ','41','Souk-Ahras'),(127,'DZ','46','Ain-Temouchent'),(128,'DZ','32','El-Bayadh'),(129,'DZ','37','Tindouf'),(130,'ES','RI','La Rioja'),(131,'ES','CT','Cataluna'),(132,'ES','MD','Madrid'),(133,'ES','CE','Ceuta'),(134,'ES','VC','Comunidad Valenciana'),(135,'ES','EX','Extremadura'),(136,'ES','ML','Melilla'),(137,'ES','CL','Castilla - Leon'),(138,'ES','GA','Galicia'),(139,'ES','AN','Andalucia'),(140,'ES','NC','Navarra'),(141,'ES','CM','Castilla - La Mancha'),(142,'ES','IB','Baleares'),(143,'ES','AS','Asturias'),(144,'ES','PV','Pais Vasco'),(145,'ES','CN','Canarias'),(146,'ES','MC','Murcia'),(147,'ES','CB','Cantabria'),(148,'FR','A5','Corse'),(149,'FR','C1','Alsace'),(150,'FR','B1','Limousin'),(151,'FR','A1','Bourgogne'),(152,'FR','B6','Picardie'),(153,'FR','B2','Lorraine'),(154,'FR','A2','Bretagne'),(155,'FR','B7','Poitou-Charentes'),(156,'FR','A7','Haute-Normandie'),(157,'FR','97','Aquitaine'),(158,'FR','A3','Centre'),(159,'FR','98','Auvergne'),(160,'FR','B4','Nord-Pas-de-Calais'),(161,'FR','A4','Champagne-Ardenne'),(162,'FR','A9','Languedoc-Roussillon'),(163,'FR','99','Basse-Normandie'),(164,'FR','B5','Pays de la Loire'),(165,'GB','ENG','England'),(166,'GB','NIR','Northern Ireland'),(167,'GB','SCT','Scotland'),(168,'GB','WLS','Wales'),(169,'GF','GF','Guyane'),(170,'GP','GP','Guadeloupe'),(171,'GU','66','Gu'),(172,'GU','GU','Guam'),(173,'HU','BA','Baranya'),(174,'HU','TO','Tolna'),(175,'HU','VA','Vas'),(176,'HU','BU','Budapest'),(177,'HU','ZA','Zala'),(178,'IN','OR','Orisa'),(179,'IN','JH','Jharkhand'),(180,'IN','AP','Andhra Pradesh'),(181,'IN','TR','Tripura'),(182,'IN','MM','Maharashtra'),(183,'IN','DN','Dadar and Nagar Haveli'),(184,'IN','PB','Punjab'),(185,'IN','JK','Jammu and Kashmir'),(186,'IN','BR','Bihar'),(187,'IN','UL','Uttaranchal'),(188,'IN','MN','Manipur'),(189,'IN','GJ','Gujarat'),(190,'IN','PY','Pondicherry'),(191,'IN','KA','Karnataka'),(192,'IN','CH','Chandigarh'),(193,'IN','UP','Uttar Pradesh'),(194,'IN','MP','Madhya Pradesh'),(195,'IN','HP','Himachal Pradesh'),(196,'IN','RJ','Rajasthan'),(197,'IN','KL','Kerala'),(198,'IN','DD','Daman and Diu'),(199,'IN','WB','West Bengal'),(200,'IN','MZ','Mizoram'),(201,'IN','HR','Haryana'),(202,'IN','TN','Tamil Nadu'),(203,'IN','LD','Lakshadweep'),(204,'IN','DL','New Delhi'),(205,'IT','CI','Calabria'),(206,'IT','VN','Veneto'),(207,'IT','LZ','Lazio'),(208,'IT','SD','Sardegna'),(209,'IT','ER','Emilia-Romagna'),(210,'IT','MH','Marche'),(211,'IT','TC','Toscana'),(212,'IT','FV','Friuli-Venezia Giulia'),(213,'IT','PM','Piemonte'),(214,'IT','TT','Trentino-Alto Adige'),(215,'IT','LG','Liguria'),(216,'IT','PU','Puglia'),(217,'IT','UM','Umbria'),(218,'IT','LM','Lombardia'),(219,'IT','SC','Sicilia'),(220,'LI','7009','Gamprin'),(221,'LI','7005','Schaan'),(222,'LI','7010','Ruggell'),(223,'LI','7001','Vaduz'),(224,'LI','7006','Planken'),(225,'LI','7011','Schellenberg'),(226,'LI','7002','Triesen'),(227,'LI','7007','Eschen'),(228,'LI','7003','Balzers'),(229,'LI','7008','Mauren'),(230,'LI','7004','Triesenberg'),(231,'MH','68','Mh'),(232,'MP','69','Mp'),(233,'MQ','MQ','Martinique'),(234,'MX','CAM','Campeche'),(235,'MX','NAY','Nayarit'),(236,'MX','TAB','Tabasco'),(237,'MX','DIF','Distrito Federal'),(238,'MX','ROO','Quintana Roo'),(239,'MX','ZAC','Zacatecas'),(240,'MX','JAL','Jalisco'),(241,'MX','CHH','Colima'),(242,'MX','NLE','Nuevo Leon'),(243,'MX','TAM','Tamaulipas'),(244,'MX','DUR','Durango'),(245,'MX','AGU','Aguascalientes'),(246,'MX','SIN','Sinaloa'),(247,'MX','MEX','Mexico'),(248,'MX','CHP','Coahuila De Zaragoza'),(249,'MX','OAX','Oaxaca'),(250,'MX','TLA','Tlaxcala'),(251,'MX','GRO','Guerrero'),(252,'MX','BCN','Baja California'),(253,'MX','MIC','Michoacan De Ocampo'),(254,'MX','SLP','San Luis Potosi'),(255,'MX','COA','Chiapas'),(256,'MX','PUE','Puebla'),(257,'MX','VER','Veracruz Llave'),(258,'MX','GUA','Guanajuato'),(259,'MX','BCS','Baja California Sur'),(260,'MX','MOR','Morelos'),(261,'MX','SON','Sonora'),(262,'MX','COL','Chihuahua'),(263,'MX','QUE','Queretaro De Arteaga'),(264,'MX','YUC','Yucatan'),(265,'MX','HID','Hidalgo'),(266,'MY','SBH','Sabah'),(267,'MY','KUL','Kuala Lumpur'),(268,'MY','PJY','Putrajaya'),(269,'MY','SGR','Selangor'),(270,'MY','LBN','Labuan'),(271,'MY','PLS','Perlis'),(272,'MY','JHR','Johor'),(273,'MY','SRW','Sarawak'),(274,'MY','MLK','Melaka'),(275,'MY','PNG','Pulau Pinang'),(276,'MY','KDH','Kedah'),(277,'MY','TRG','Terengganu'),(278,'MY','NSN','Negeri Sembilan'),(279,'MY','PRK','Perak'),(280,'MY','KTN','Kelantan'),(281,'MY','PHG','Pahang'),(282,'PM','97502','Saint-Pierre'),(283,'PM','97501','Miquelon-Langlade'),(284,'PR','72','Pr'),(285,'PR','PR','Puerto Rico'),(286,'PT','49','Ilha do Corvo'),(287,'RE','0','Reunion (general)'),(288,'SE','I','Gotland'),(289,'SE','BD','Norrbotten'),(290,'SE','C','Uppsala'),(291,'TH','65','Phitsanulok'),(292,'TH','54','Phrae'),(293,'TH','92','Trang'),(294,'TH','76','Phetchabun'),(295,'TH','61','Uthai Thani'),(296,'TH','50','Chiang Mai'),(297,'TH','84','Surat Thani'),(298,'TH','70','Ratchaburi'),(299,'TH','55','Nan'),(300,'TH','94','Pattani'),(301,'TH','78','Phetchaburi'),(302,'TH','62','Kamphaeng Phet'),(303,'TH','51','Lamphun'),(304,'TH','85','Ranong'),(305,'TH','73','Nakhon Pathom'),(306,'TH','56','Phayao'),(307,'TH','95','Yala'),(308,'TH','81','Krabi'),(309,'TH','63','Tak'),(310,'TH','52','Lampang'),(311,'TH','90','Songkhla'),(312,'TH','74','Samut Sakhon'),(313,'TH','57','Chiang Rai'),(314,'TH','96','Narathiwat'),(315,'TH','82','Phang Nga'),(316,'TH','64','Sukhothai'),(317,'TH','53','Uttaradit'),(318,'TH','91','Satun'),(319,'TH','75','Samut Songkhram'),(320,'TH','58','Mae Hong Son'),(321,'TH','83','Phuket'),(322,'TR','9','Aydin'),(323,'TR','77','Yalova'),(324,'TR','2','Adiyaman'),(325,'TR','6','Ankara'),(326,'TR','79','Kilis'),(327,'TR','3','Afyonkarahisar'),(328,'TR','67','Zonguldak'),(329,'TR','8','Artvin'),(330,'TR','7','Antalya'),(331,'TR','80','Osmaniye'),(332,'TR','5','Amasya'),(333,'TR','71','Kirikkale'),(334,'TR','1','Adana'),(335,'US','OK','Oklahoma'),(336,'US','MS','Mississippi'),(337,'US','IL','Illinois'),(338,'US','VT','Vermont'),(339,'US','CA','California'),(340,'US','NM','New Mexico'),(341,'US','MA','Massachusetts'),(342,'US','PA','Pennsylvania'),(343,'US','MT','Montana'),(344,'US','IN','Indiana'),(345,'US','WI','Wisconsin'),(346,'US','CO','Colorado'),(347,'US','NV','Nevada'),(348,'US','ME','Maine'),(349,'US','HI','Hawaii'),(350,'US','AK','Alaska'),(351,'US','ND','North Dakota'),(352,'US','KS','Kansas'),(353,'US','WV','West Virginia'),(354,'US','DC','District of Columbia'),(355,'US','NY','New York'),(356,'US','MI','Michigan'),(357,'US','IA','Iowa'),(358,'US','TX','Texas'),(359,'US','AL','Alabama'),(360,'US','NH','New Hampshire'),(361,'US','KY','Kentucky'),(362,'US','WY','Wyoming'),(363,'US','DE','Delaware'),(364,'US','OH','Ohio'),(365,'US','MO','Missouri'),(366,'US','ID','Idaho'),(367,'US','UT','Utah'),(368,'US','AZ','Arizona'),(369,'US','NJ','New Jersey'),(370,'US','LA','Louisiana'),(371,'US','FL','Florida'),(372,'YT','97607','Dembeni'),(373,'YT','97613','MTsangamouji'),(374,'YT','97603','Bandrele'),(375,'YT','97614','Ouangani'),(376,'YT','97610','Koungou'),(377,'YT','97616','Sada'),(378,'YT','97605','Chiconi'),(379,'YT','97611','Mamoudzou'),(380,'YT','97617','Tsingoni'),(381,'YT','97606','Chirongui'),(382,'YT','97612','Mtsamboro'),(383,'YT','97602','Bandraboua');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `logintimestamp` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `userStatus` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `primaryEmailId` bigint(20) DEFAULT NULL,
  `roleId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36EBCBA454CEF5` (`primaryEmailId`),
  KEY `FK36EBCB554D373C` (`roleId`),
  CONSTRAINT `FK36EBCB554D373C` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`),
  CONSTRAINT `FK36EBCBA454CEF5` FOREIGN KEY (`primaryEmailId`) REFERENCES `email` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Demo','Person','2017-03-17 22:45:23','password','ACTIVE','demo',1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'sys_demodb'
--
/*!50003 DROP PROCEDURE IF EXISTS `get_rptIncomeAndExpense` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_rptIncomeAndExpense`(
	IN in_startmonth INTEGER,
	IN in_startyear INTEGER,
	IN in_totalmonths INTEGER
)
BEGIN
	DECLARE v_idx INTEGER DEFAULT 0;
	DECLARE v_month INTEGER DEFAULT in_startmonth;
	DECLARE v_year INTEGER DEFAULT in_startyear;
	
	DROP TEMPORARY TABLE IF EXISTS tblResults;
	CREATE TEMPORARY TABLE IF NOT EXISTS tblResults  (
		invoiceAmt DECIMAL(19,2),
		expenseAmt DECIMAL(19,2),
		monthVal INTEGER,
		yearVal INTEGER
	);


	WHILE v_idx < in_totalmonths DO
		INSERT INTO tblResults 
		SELECT 
			(SELECT COALESCE(sum(invoice.amount),0) FROM invoice 
			WHERE MONTH(invoice.paymentDate) = v_month 
			AND YEAR(invoice.paymentDate) = v_year) as totalincome,

			(SELECT COALESCE(sum(expense.amount),0) FROM expense 
			WHERE MONTH(expense.expenseDate) = v_month 
			AND YEAR(expense.expenseDate) = v_year) as totalexpense,
			v_month as month,
			v_year as year;
			
		-- rollover the month, year
		SET v_month = v_month + 1;
		SET v_idx = v_idx + 1;
		IF v_month = 13 THEN
			SET v_month = 1;
			SET v_year = v_year + 1;
		END IF;
	END WHILE;
	
	SELECT *
	FROM tblResults;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_rptSalesAndPayments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_VALUE_ON_ZERO' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_rptSalesAndPayments`(
	IN in_startmonth INTEGER,
	IN in_startyear INTEGER,
	IN in_totalmonths INTEGER
)
BEGIN
	DECLARE v_idx INTEGER DEFAULT 0;
	DECLARE v_month INTEGER DEFAULT in_startmonth;
	DECLARE v_year INTEGER DEFAULT in_startyear;
	
	DROP TEMPORARY TABLE IF EXISTS tblResults;
	CREATE TEMPORARY TABLE IF NOT EXISTS tblResults  (
		salesAmt DECIMAL(19,2),
		paidAmt DECIMAL(19,2),
		monthVal INTEGER,
		yearVal INTEGER
	);


	WHILE v_idx < in_totalmonths DO
		INSERT INTO tblResults 
		SELECT 
			(SELECT COALESCE(sum(invoice.amount),0) FROM invoice 
			WHERE MONTH(invoice.invoiceDate) = v_month 
			AND YEAR(invoice.invoiceDate) = v_year) as totalsales,

			(SELECT COALESCE(sum(invoice.amount),0) FROM invoice 
			WHERE MONTH(invoice.invoiceDate) = v_month 
			AND YEAR(invoice.invoiceDate) = v_year
			AND invoice.invoiceStatus = 'PAID') as totalpaid,
			v_month as month,
			v_year as year;
			
		-- rollover the month, year
		SET v_month = v_month + 1;
		SET v_idx = v_idx + 1;
		IF v_month = 13 THEN
			SET v_month = 1;
			SET v_year = v_year + 1;
		END IF;
	END WHILE;
	
	SELECT *
	FROM tblResults;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-17 22:55:41
