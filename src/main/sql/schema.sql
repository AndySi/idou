/*
SQLyog Ultimate v9.30 
MySQL - 5.5.40 : Database - idou
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`idou` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `idou`;

/*Table structure for table `auth_function` */

DROP TABLE IF EXISTS `auth_function`;

CREATE TABLE `auth_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `serial_num` int(11) DEFAULT NULL COMMENT '序号',
  `accordion` int(11) DEFAULT NULL COMMENT '桌面是否显示为顶级菜单',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `auth_function` */

/*Table structure for table `auth_role` */

DROP TABLE IF EXISTS `auth_role`;

CREATE TABLE `auth_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `auth_role` */

/*Table structure for table `auth_role_function` */

DROP TABLE IF EXISTS `auth_role_function`;

CREATE TABLE `auth_role_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `function_id` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `auth_role_function` */

/*Table structure for table `auth_user` */

DROP TABLE IF EXISTS `auth_user`;

CREATE TABLE `auth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `pwd` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `auth_user` */

/*Table structure for table `auth_user_role` */

DROP TABLE IF EXISTS `auth_user_role`;

CREATE TABLE `auth_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `auth_user_role` */

/*Table structure for table `tb_seckill` */

DROP TABLE IF EXISTS `tb_seckill`;

CREATE TABLE `tb_seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '商品库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=MyISAM AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

/*Data for the table `tb_seckill` */

insert  into `tb_seckill`(`seckill_id`,`name`,`number`,`start_time`,`end_time`,`create_time`) values (1000,'1000元秒杀iphone7',10,'2016-10-17 11:50:37','2016-10-18 11:50:47','2016-10-16 11:50:54'),(1001,'100元秒杀iphone6S',13,'2016-10-17 11:50:37','2016-10-19 11:51:46','2016-10-16 11:51:53');

/*Table structure for table `tb_seckill_detail` */

DROP TABLE IF EXISTS `tb_seckill_detail`;

CREATE TABLE `tb_seckill_detail` (
  `seckill_id` bigint(20) NOT NULL,
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机',
  `state` tinyint(4) NOT NULL COMMENT '状态：-1-失效，0-成功',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `tb_seckill_detail` */

insert  into `tb_seckill_detail`(`seckill_id`,`user_phone`,`state`,`create_time`) values (1000,18573163183,0,'2016-10-18 12:45:09'),(1001,18573163183,0,'2016-10-18 15:52:18');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
