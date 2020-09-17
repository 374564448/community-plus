/*
Navicat MySQL Data Transfer

Source Server         : community-plus-docker-mysql
Source Server Version : 80018
Source Host           : 192.168.72.130:3306
Source Database       : community_notification_center

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-09-17 19:11:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notifier_id` int(11) NOT NULL COMMENT '发出通知的人的id',
  `receiver_id` int(11) NOT NULL COMMENT '接收通知的人的id',
  `outer_id` int(11) NOT NULL COMMENT '通知的内容的id （问题id 、评论id 等，通过type来区分）',
  `type` varchar(20) NOT NULL COMMENT '通知的类型（回复了你的评论、评论了你的文章、点赞了你等等）',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（已读1 / 未读0）',
  `notifier_name` varchar(20) NOT NULL COMMENT '发出通知的人的名字（减少多次查询数据库）',
  `outer_title` varchar(100) NOT NULL DEFAULT '' COMMENT '被通知的内容(文章标题、评论内容等)',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '内容',
  `create_time` bigint(20) NOT NULL COMMENT '发出通知的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
