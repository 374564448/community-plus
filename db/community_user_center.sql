/*
Navicat MySQL Data Transfer

Source Server         : community-plus-docker-mysql
Source Server Version : 80018
Source Host           : 192.168.72.130:3306
Source Database       : community_user_center

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-09-17 19:11:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bonus_event_log
-- ----------------------------
DROP TABLE IF EXISTS `bonus_event_log`;
CREATE TABLE `bonus_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `value` int(11) NOT NULL DEFAULT '0' COMMENT '积分值',
  `event` varchar(20) NOT NULL DEFAULT '' COMMENT '事件',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(50) NOT NULL,
  `account_type` int(2) NOT NULL COMMENT '1表示账号密码登录，2表示GitHub账户登录,3表示微信登录',
  `password` varchar(50) NOT NULL DEFAULT '',
  `name` varchar(20) NOT NULL DEFAULT '',
  `avatar_url` varchar(255) NOT NULL DEFAULT '',
  `bio` varchar(255) NOT NULL DEFAULT '',
  `roles` varchar(100) NOT NULL DEFAULT '',
  `bonus` int(11) NOT NULL DEFAULT '0',
  `create_time` bigint(20) NOT NULL,
  `modify_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
