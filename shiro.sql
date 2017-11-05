CREATE DATABASE IF NOT EXISTS `shiro` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `shiro`;

# int(10)存储相应的时间戳：占用资源少，查询速度快。在表示时间字段上建立索引，那么使用 INT 索引效率要高
# Timestamp类型：1970-01-01 00:00:00~2038-01-09 03:14:07。跟随设置的时区变化而变化
# DateTime类型：1000-01-01 00:00:00 ~ 9999-12-31 23:59:59。保存的是绝对值不会变化，允许用户计划 2038 年以后的事情

DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `dept_id` int(11) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_name` varchar(100) NOT NULL COMMENT '部门名称',
  `dept_desc` varchar(300) DEFAULT NULL COMMENT '描述'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='部门表';
INSERT INTO `dept`(`dept_id`,`dept_name`,`dept_desc`) VALUES (1,'产品部','cpb');

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(128) NOT NULL COMMENT '登录账号',
  `password` varchar(32) NOT NULL COMMENT '登录密码',
  `nickname` varchar(20) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '用户邮箱',
  `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '用户手机号',
  `gender` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '用户性别（1:男, 0:女, -1:未知）',
  `description` varchar(300) NOT NULL DEFAULT '' COMMENT '用户描述',
  `avatar` varchar(300) NOT NULL DEFAULT 'avatar.png' COMMENT '头像',
  `dept_id` int(11) UNSIGNED DEFAULT NULL COMMENT '部门主键',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `user_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态（1:有效,0:禁止登录）'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户表';
INSERT INTO `user`(`username`,`password`) VALUES ('admin','123456');
INSERT INTO `user`(`user_id`,`username`,`password`,`user_state`,`create_time`,`description`,`avatar`,`dept_id`) VALUES (1,'admin',1,1,'2017-09-14 15:02:17','','avatar.png',1);

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '角色描述',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `role_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '角色状态（1:启用, 0:禁用）'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色表';
INSERT INTO `role`(`role_name`,`role_desc`) VALUES ('user', '普通用户');
INSERT INTO `role`(`role_name`,`role_desc`) VALUES ('admin', '管理员');
INSERT INTO `role`(`role_name`,`role_desc`) VALUES ('super_admin', '超级管理员');

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` int(11) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission_name` varchar(50) NOT NULL COMMENT '权限名称',
  `permission_type` tinyint(4) NOT NULL COMMENT '权限资源类型（1:菜单,2:按钮）',
  `url` varchar(255) DEFAULT NULL COMMENT '资源url地址',
  `percode` varchar(50) DEFAULT NULL COMMENT '权限代码字符串',
  `pid` int(11) UNSIGNED NOT NULL COMMENT '父级资源ID',
  `pids` varchar(128) DEFAULT NULL COMMENT '父结点ID列表串',
  `sort` int(11) DEFAULT 0 COMMENT '排序号',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `deep` int(11) DEFAULT NULL COMMENT '深度',
  `permission_state` char(1) DEFAULT NULL COMMENT '是否可用（1:可用，0:不可用）'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限表';
INSERT INTO `permission`(`permission_id`,`permission_name`,`pid`,`url`,`icon`,`sort`,`deep`,`percode`) VALUES (1,'查询系统设置','10',NULL,NULL,0,3,'010600');

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` int(11) UNSIGNED NOT NULL COMMENT '角色ID',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `userrole_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否可用（0:可用，1:不可用）',
  CONSTRAINT `ur_fk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `ur_fk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';
INSERT INTO `user_role`(`id`,`user_id`,`role_id`) VALUES (1,1,1);

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) UNSIGNED NOT NULL COMMENT '角色ID',
  `permission_id` int(11) UNSIGNED NOT NULL COMMENT '菜单ID',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `rolepermission_state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否可用（0:可用，1:不可用）',
  CONSTRAINT `rp_fk_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`),
  CONSTRAINT `rp_fk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='角色菜单关联表/角色权限表';
INSERT INTO `role_permission`(`id`,`role_id`,`permission_id`) VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4),(5,5,5);

DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `id` int(11) UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sysKey` varchar(50) NOT NULL COMMENT 'KEY',
  `sysName` varchar(50) NOT NULL COMMENT '名称',
  `sysValue` varchar(300) DEFAULT NULL COMMENT '值',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `sysDesc` varchar(300) DEFAULT NULL COMMENT '说明'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统设置表';
INSERT INTO `setting`(`id`,`sysKey`,`sysName`,`sysValue`,`sort`,`sysDesc`) VALUES ('1','systemName','系统名称','AdminLTE-admin',0,NULL),('2','systemSubName','系统简称','AA',1,NULL),('3','bottomCopyright','许可说明','Copyright © 2017 米粒电商. All rights reserved.',2,NULL);

GRANT ALL PRIVILEGES ON shiro.* To 'shiro_user'@'localhost' IDENTIFIED BY 'shiro';

# https://github.com/zhougaojun618/AdminLTE-admin/blob/master/src/main/resources/sql/AdminLTE-admin.sql
# https://github.com/u014427391/jeeplatform/blob/master/db/jeeplatform.sql