/*
Navicat MySQL Data Transfer

Source Server         : community-plus-docker-mysql
Source Server Version : 80018
Source Host           : 192.168.72.130:3306
Source Database       : community_content_center

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-09-17 19:11:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '作者id',
  `category_id` int(11) NOT NULL,
  `title` varchar(50) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '内容',
  `tag` varchar(255) NOT NULL DEFAULT '' COMMENT '标签',
  `view_count` int(11) NOT NULL DEFAULT '0' COMMENT '阅读数',
  `like_count` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `comment_count` int(11) NOT NULL DEFAULT '0' COMMENT '评论数',
  `collection_count` int(11) NOT NULL DEFAULT '0' COMMENT '收藏数',
  `is_original` int(1) NOT NULL DEFAULT '1' COMMENT '是否原创: 1是，2否',
  `audit_status` varchar(10) NOT NULL DEFAULT '1' COMMENT '审核状态：NOT_YET: 待审核  PASS:审核通过 REJECT:审核不通过',
  `reason` varchar(200) NOT NULL DEFAULT '' COMMENT '审核通过/不通过的理由',
  `show_flag` int(1) NOT NULL DEFAULT '1' COMMENT '是否公开：1: 公开  2: 私密',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  `modify_time` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for article_cache_data_event_log
-- ----------------------------
DROP TABLE IF EXISTS `article_cache_data_event_log`;
CREATE TABLE `article_cache_data_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `event` varchar(20) NOT NULL DEFAULT '',
  `description` varchar(100) NOT NULL DEFAULT '',
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cache_data_to_db_event_log
-- ----------------------------
DROP TABLE IF EXISTS `cache_data_to_db_event_log`;
CREATE TABLE `cache_data_to_db_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_id` int(11) NOT NULL,
  `event` varchar(20) NOT NULL DEFAULT '',
  `description` varchar(100) NOT NULL DEFAULT '',
  `create_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `comment_list_id` int(11) NOT NULL COMMENT '评论列表的id：比如一级评论列表comment_list_id是该文章id,二级评论列表是对应一级评论的id(该一级评论下有多条二级评论,但二级评论的parentId不仅仅只是该一级评论,也可能是该一级评论下的其他二级评论)',
  `parent_id` int(11) NOT NULL COMMENT '该评论的父id: 文章id或者评论id,根据type来区分',
  `type` int(1) NOT NULL COMMENT '评论类型：1表示针对文章的评论，2表示针对评论的回复',
  `commentator_id` int(11) NOT NULL COMMENT '评论者的id',
  `content` longtext NOT NULL COMMENT '评论内容',
  `like_count` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `comment_count` int(11) NOT NULL DEFAULT '0' COMMENT '该文章或者该一级评论的评论数',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rocketmq_transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `rocketmq_transaction_log`;
CREATE TABLE `rocketmq_transaction_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_id` varchar(50) NOT NULL,
  `log` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
