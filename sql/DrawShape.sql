/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.35-enterprise-commercial-advanced : Database - drawshape
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`drawshape` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `drawshape`;

/*Table structure for table `t_graph` */

DROP TABLE IF EXISTS `t_graph`;

CREATE TABLE `t_graph` (
  `id` varchar(32) NOT NULL COMMENT '图纸id',
  `name` varchar(32) NOT NULL COMMENT '图纸名称',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_graph` */

insert  into `t_graph`(`id`,`name`,`createTime`,`updateTime`) values ('3c840e5091634a13aca1bb85830c503e','2','2021-05-28 19:39:30','2021-05-28 19:39:30'),('74d44f6f71e34b66b98fd040946e3067','1','2021-05-28 18:51:37','2021-06-09 18:35:37'),('7e436af7fa4b4b4a9be62a64d10f9c03','测试','2021-05-28 13:09:31','2021-05-28 13:09:31');

/*Table structure for table `t_shape` */

DROP TABLE IF EXISTS `t_shape`;

CREATE TABLE `t_shape` (
  `id` varchar(32) NOT NULL COMMENT '图形id',
  `parentId` varchar(32) DEFAULT NULL COMMENT '对应父节点的id',
  `shapeType` tinyint(4) DEFAULT NULL COMMENT '图形类别，枚举',
  `radius` int(11) DEFAULT NULL COMMENT '圆形半径',
  `endx` int(11) DEFAULT NULL COMMENT '线段终点横坐标',
  `endy` int(11) DEFAULT NULL COMMENT '线段终点纵坐标',
  `width` int(11) DEFAULT NULL COMMENT '矩形宽度',
  `height` int(11) DEFAULT NULL COMMENT '矩形高度',
  `graphId` varchar(32) DEFAULT NULL COMMENT '图形所在图纸id',
  PRIMARY KEY (`id`),
  KEY `graphId` (`graphId`),
  CONSTRAINT `t_shape_ibfk_1` FOREIGN KEY (`id`) REFERENCES `t_shapebase` (`id`),
  CONSTRAINT `t_shape_ibfk_2` FOREIGN KEY (`graphId`) REFERENCES `t_graph` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_shape` */

insert  into `t_shape`(`id`,`parentId`,`shapeType`,`radius`,`endx`,`endy`,`width`,`height`,`graphId`) values ('1d0f57fc3b864358a489d82fd4828379',NULL,4,0,0,0,0,0,'3c840e5091634a13aca1bb85830c503e'),('3146e3e864c24a3191e9161c9be2597b','f8c6cb667c7e43e580b087782f6e6685',2,0,0,0,0,0,'3c840e5091634a13aca1bb85830c503e'),('352d01d91648441dbb38efe535dabc2d','d98adab27f054af399a9c3d49afb0938',0,71,0,0,0,0,'7e436af7fa4b4b4a9be62a64d10f9c03'),('3e0200ff2b914cc2ac7fd42fabfc6dc6',NULL,0,127,0,0,0,0,'74d44f6f71e34b66b98fd040946e3067'),('42ed3313e55e4ea69874785b8b51843c','f8c6cb667c7e43e580b087782f6e6685',1,0,809,298,0,0,'3c840e5091634a13aca1bb85830c503e'),('827d152f64584d3196b7464e24249a1c','1d0f57fc3b864358a489d82fd4828379',3,0,0,0,220,240,'3c840e5091634a13aca1bb85830c503e'),('89d82f2d9a5c4918989e99270fbfcf3a',NULL,4,0,0,0,0,0,'7e436af7fa4b4b4a9be62a64d10f9c03'),('9083e078bba4469f983542a1d7feeb96','89d82f2d9a5c4918989e99270fbfcf3a',1,0,-23,370,0,0,'7e436af7fa4b4b4a9be62a64d10f9c03'),('d98adab27f054af399a9c3d49afb0938',NULL,4,0,0,0,0,0,'7e436af7fa4b4b4a9be62a64d10f9c03'),('d9ea0dd8b89a479b9c95125c2050b730','e5cf582e38004720a713e9f6c620df74',0,93,0,0,0,0,'3c840e5091634a13aca1bb85830c503e'),('e2d5f0fdba5a418db9b7eb6c34fefee4','d98adab27f054af399a9c3d49afb0938',2,0,0,0,0,0,'7e436af7fa4b4b4a9be62a64d10f9c03'),('e3f0340a87944c9698e76055e32cee33','89d82f2d9a5c4918989e99270fbfcf3a',3,0,0,0,78,368,'7e436af7fa4b4b4a9be62a64d10f9c03'),('e5cf582e38004720a713e9f6c620df74','1d0f57fc3b864358a489d82fd4828379',4,0,0,0,0,0,'3c840e5091634a13aca1bb85830c503e'),('ef2c8e3ca6bf4f6eb78a727c65a30b05',NULL,3,0,0,0,469,473,'74d44f6f71e34b66b98fd040946e3067'),('f8c6cb667c7e43e580b087782f6e6685','e5cf582e38004720a713e9f6c620df74',4,0,0,0,0,0,'3c840e5091634a13aca1bb85830c503e'),('f95cbadf38594160867dac4c5865a9e2',NULL,2,0,0,0,0,0,'74d44f6f71e34b66b98fd040946e3067'),('ff10ecdf0e474c29b36d35efa75a760a',NULL,1,0,969,669,0,0,'74d44f6f71e34b66b98fd040946e3067');

/*Table structure for table `t_shapebase` */

DROP TABLE IF EXISTS `t_shapebase`;

CREATE TABLE `t_shapebase` (
  `id` varchar(32) NOT NULL COMMENT '图形基类id',
  `name` varchar(32) NOT NULL COMMENT '图形名称',
  `startX` int(11) DEFAULT NULL COMMENT '图形起点横坐标',
  `startY` int(11) DEFAULT NULL COMMENT '图形起点纵坐标',
  `checked` tinyint(1) DEFAULT NULL COMMENT '选中标记',
  `filled` tinyint(1) DEFAULT NULL COMMENT '填充标记',
  `lineColor` tinyint(4) DEFAULT NULL COMMENT '线条颜色，数字枚举',
  `fillColor` tinyint(4) DEFAULT NULL COMMENT '填充颜色，数字枚举',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_shapebase` */

insert  into `t_shapebase`(`id`,`name`,`startX`,`startY`,`checked`,`filled`,`lineColor`,`fillColor`) values ('1d0f57fc3b864358a489d82fd4828379','组合对象',0,0,1,0,3,9),('3146e3e864c24a3191e9161c9be2597b','自定义按钮',1095,342,1,0,3,9),('352d01d91648441dbb38efe535dabc2d','圆',1021,271,0,1,6,8),('3e0200ff2b914cc2ac7fd42fabfc6dc6','圆',1337,461,0,0,3,9),('42ed3313e55e4ea69874785b8b51843c','线段',1444,308,1,0,3,9),('827d152f64584d3196b7464e24249a1c','矩形',434,158,1,0,3,9),('89d82f2d9a5c4918989e99270fbfcf3a','组合对象',0,0,0,0,3,9),('9083e078bba4469f983542a1d7feeb96','线段',848,104,0,1,1,4),('d98adab27f054af399a9c3d49afb0938','组合对象',0,0,0,0,3,9),('d9ea0dd8b89a479b9c95125c2050b730','圆',767,475,1,0,3,9),('e2d5f0fdba5a418db9b7eb6c34fefee4','自定义按钮',1070,578,0,1,6,8),('e3f0340a87944c9698e76055e32cee33','矩形',393,333,0,1,1,4),('e5cf582e38004720a713e9f6c620df74','组合对象',0,0,1,0,3,9),('ef2c8e3ca6bf4f6eb78a727c65a30b05','矩形',109,77,0,0,3,9),('f8c6cb667c7e43e580b087782f6e6685','组合对象',0,0,1,0,3,9),('f95cbadf38594160867dac4c5865a9e2','自定义按钮',962,76,0,0,3,9),('ff10ecdf0e474c29b36d35efa75a760a','线段',1015,641,0,0,3,9);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
