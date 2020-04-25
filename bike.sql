/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.0.67-community : Database - bike
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bike` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `bike`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `adminname` varchar(20) collate utf8_unicode_ci NOT NULL,
  `adminpass` varchar(20) collate utf8_unicode_ci default NULL,
  PRIMARY KEY  (`adminname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `admin` */

insert  into `admin`(`adminname`,`adminpass`) values ('admin','admin');

/*Table structure for table `balance` */

DROP TABLE IF EXISTS `balance`;

CREATE TABLE `balance` (
  `loginname` varchar(20) collate utf8_unicode_ci NOT NULL,
  `date` datetime default NULL,
  `iscoupon` int(4) default NULL,
  `pay` int(4) default NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `balance` */

insert  into `balance`(`loginname`,`date`,`iscoupon`,`pay`) values ('def','2020-04-24 21:10:24',0,1),('666','2020-04-24 21:15:41',0,1),('666','2020-04-24 21:26:30',0,1),('666','2020-04-24 21:27:12',2,0),('def','2020-04-24 21:28:13',5,0),('def','2020-04-24 21:29:20',0,1),('666','2020-04-24 21:30:27',1,0);

/*Table structure for table `bike` */

DROP TABLE IF EXISTS `bike`;

CREATE TABLE `bike` (
  `id` int(20) NOT NULL auto_increment,
  `bikename` varchar(20) collate utf8_unicode_ci default NULL,
  `status` int(1) default NULL,
  `bid` int(4) default NULL,
  `zhan` int(4) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `bike` */

insert  into `bike`(`id`,`bikename`,`status`,`bid`,`zhan`) values (1,'小红',1,1101,NULL),(2,'小绿',2,1102,NULL),(3,'小粉',1,1103,NULL),(4,'小黄',1,2201,NULL),(5,'小黑',1,2202,NULL),(6,'小紫',2,2203,NULL),(7,'小彩',1,6666,NULL),(8,'小狗',1,3301,NULL),(9,'小猫',1,3302,NULL),(10,'小猪',1,3303,NULL),(11,'小牛',3,3304,NULL),(12,'小虎',1,4001,NULL),(13,'小兔',3,4002,NULL),(14,'小鱼',1,4003,NULL),(15,'小花',1,4004,NULL),(16,'小草',1,5101,NULL),(17,'小兰',2,5102,NULL),(18,'小龟',1,5103,NULL),(19,'小树',1,5104,NULL),(20,'小鸟',1,5555,NULL);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `loginname` varchar(20) collate utf8_unicode_ci default NULL,
  `time` datetime default NULL,
  `comment` varchar(1000) collate utf8_unicode_ci default NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `comment` */

insert  into `comment`(`loginname`,`time`,`comment`) values ('def','2020-03-26 19:59:44','这破车是真的垃圾啊'),('def','2020-03-26 20:08:13','这系统谁设计的辣鸡玩意'),('筛子1','2020-03-11 21:11:29','这个网页设计的是真的垃圾啊'),('狗黑人','2020-03-11 21:12:31','java是真的难学啊'),('狗黑人','2020-03-27 15:03:35','这单车刹车都是坏的，差点没命'),('筛子1','2020-03-27 15:07:53','没啥好说的，凑个数'),('def','2020-03-27 16:35:19','测试1  来自def'),('def','2020-03-27 16:36:17','测试2 来自def'),('def','2020-03-27 16:52:36','测试3  来自def'),('狗黑人','2020-03-27 16:55:09','测试够黑人'),('刘晓慧2','2020-03-27 21:22:42','这车不错  '),('def','2020-04-22 20:21:47','这是谁设计的安卓app啊太垃圾了呕，辣鸡系统'),('def','2020-04-22 20:22:04','@jidudvebeododbdbdhddididjdhdhdjdjdjdjjdjddndndjdjdjjdjdjdjdjjddjdjdjdjjdkd'),('def','2020-04-22 20:23:02','666');

/*Table structure for table `coupon` */

DROP TABLE IF EXISTS `coupon`;

CREATE TABLE `coupon` (
  `loginname` varchar(20) collate utf8_unicode_ci NOT NULL,
  `one` int(2) default '0',
  `two` int(2) default '0',
  `three` int(2) default '0',
  `five` int(2) default '0',
  PRIMARY KEY  (`loginname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `coupon` */

insert  into `coupon`(`loginname`,`one`,`two`,`three`,`five`) values ('def',2,0,1,2),('刘晓慧2',0,0,0,0),('筛子1',0,0,0,0),('狗黑人',0,0,0,0),('666',0,0,0,0),('菜鸟先飞',0,0,0,0),('狗胡2',0,0,0,0);

/*Table structure for table `record` */

DROP TABLE IF EXISTS `record`;

CREATE TABLE `record` (
  `loginname` varchar(20) collate utf8_unicode_ci NOT NULL,
  `btime` datetime default NULL,
  `rtime` datetime default NULL,
  `time` int(20) default NULL,
  `bid` int(4) default NULL,
  `ispay` int(1) default '0'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `record` */

insert  into `record`(`loginname`,`btime`,`rtime`,`time`,`bid`,`ispay`) values ('筛子1','2020-04-05 18:41:05','2020-04-05 18:41:29',24,201,1),('def','2020-04-05 16:39:24','2020-04-05 16:39:33',9,103,1),('def','2020-04-05 21:43:38','2020-04-05 21:43:50',12,102,1),('def','2020-04-05 23:12:03','2020-04-05 23:12:18',15,6666,1),('def','2020-04-05 23:12:43','2020-04-05 23:13:03',20,203,1),('def','2020-04-05 23:17:53','2020-04-05 23:19:02',69,201,1),('def','2020-04-05 23:19:23','2020-04-08 16:57:41',236298,103,1),('刘晓慧2','2020-04-06 14:01:50','2020-04-06 14:02:07',17,203,1),('筛子1','2020-04-15 19:52:09','2020-04-15 19:52:31',22,1103,1),('def','2020-04-19 17:21:51','2020-04-19 17:24:07',136,1102,1),('def','2020-04-19 20:20:01','2020-04-19 20:20:43',42,2201,1),('def','2020-04-19 22:09:44','2020-04-19 22:09:57',13,1101,1),('def','2020-04-19 22:10:59','2020-04-19 22:11:03',4,1101,1),('刘晓慧2','2020-04-21 21:59:37','2020-04-21 22:00:29',52,6666,1),('刘晓慧2','2020-04-21 22:04:47','2020-04-21 22:05:02',15,6666,1),('def','2020-04-22 15:38:17','2020-04-22 19:40:19',14522,6666,1),('def','2020-04-23 15:18:57','2020-04-23 15:19:48',51,6666,1),('def','2020-04-23 17:33:29','2020-04-23 17:33:34',5,6666,1),('666','2020-04-23 18:17:46','2020-04-23 18:18:34',48,2201,1),('666','2020-04-23 19:58:40','2020-04-23 19:59:01',21,1101,1),('666','2020-04-23 20:01:05','2020-04-23 20:01:23',18,3303,1),('666','2020-04-23 20:09:52','2020-04-23 20:10:23',31,6666,1),('666','2020-04-23 20:11:12','2020-04-23 20:11:18',6,2201,1),('def','2020-04-24 16:52:12','2020-04-24 16:52:37',25,6666,1),('def','2020-04-24 20:07:22','2020-04-24 20:07:48',26,6666,1),('def','2020-04-24 16:33:20','2020-04-24 20:34:03',14443,6666,1),('def','2020-04-24 21:03:39','2020-04-24 21:03:42',3,6666,1),('def','2020-04-24 21:05:08','2020-04-24 21:05:13',5,6666,1),('def','2020-04-24 21:06:34','2020-04-24 21:06:38',4,6666,1),('def','2020-04-24 21:10:19','2020-04-24 21:10:21',2,6666,1),('def','2020-04-24 21:10:45','2020-04-24 21:10:50',5,6666,1),('666','2020-04-24 21:15:33','2020-04-24 21:15:35',2,6666,1),('666','2020-04-24 21:15:57','2020-04-24 21:15:59',2,6666,1),('666','2020-04-24 21:20:45','2020-04-24 21:20:46',1,6666,1),('666','2020-04-24 21:24:41','2020-04-24 21:24:42',1,6666,1),('666','2020-04-24 21:26:27','2020-04-24 21:26:28',1,6666,1),('666','2020-04-24 21:26:59','2020-04-24 21:27:01',2,5101,1),('def','2020-04-24 21:27:58','2020-04-24 21:28:05',7,1103,1),('def','2020-04-24 21:29:15','2020-04-24 21:29:17',2,2202,1),('666','2020-04-24 21:30:08','2020-04-24 21:30:20',12,1101,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(20) NOT NULL auto_increment,
  `loginname` varchar(20) collate utf8_unicode_ci default NULL,
  `loginpass` varchar(20) collate utf8_unicode_ci default NULL,
  `money` int(11) default '0',
  `status` int(4) default '0',
  `bid` int(4) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`loginname`,`loginpass`,`money`,`status`,`bid`) values (1,'def','123',35,0,0),(2,'刘晓慧2','123',17,0,0),(3,'筛子1','sdd',352,0,0),(4,'狗黑人','123',0,0,0),(5,'狗胡2','666',0,0,0),(6,'666','666',62,0,0),(62,'菜鸟先飞','123',0,0,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
