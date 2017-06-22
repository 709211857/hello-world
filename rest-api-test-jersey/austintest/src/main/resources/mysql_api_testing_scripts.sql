/*
Navicat MySQL Data Transfer

Source Server         : www
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : api

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-06-22 16:10:27
*/
DROP database IF EXISTS `api`;
CREATE database api;
USE api;
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('1111');

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for block_rel
-- ----------------------------
DROP TABLE IF EXISTS `block_rel`;
CREATE TABLE `block_rel` (
  `requestor` varchar(255) NOT NULL,
  `target` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of block_rel
-- ----------------------------
INSERT INTO `block_rel` VALUES ('andy@qq.com', 'john@example.com', '');

-- ----------------------------
-- Table structure for friend_rel
-- ----------------------------
DROP TABLE IF EXISTS `friend_rel`;
CREATE TABLE `friend_rel` (
  `person1_email` varchar(20) NOT NULL,
  `person2_email` varchar(20) NOT NULL,
  `isblock` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend_rel
-- ----------------------------
INSERT INTO `friend_rel` VALUES ('andy@qq.com', 'john@example.com', '');
INSERT INTO `friend_rel` VALUES ('andy@qq.com', 'john1@example.com', '');
INSERT INTO `friend_rel` VALUES ('cc@example.com', 'andy@qq.com', '');
INSERT INTO `friend_rel` VALUES ('ccc@qq.com', 'john@example.com', '');
INSERT INTO `friend_rel` VALUES ('ccc@qq.com', 'austin@example.com', '\0');

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  UNIQUE KEY `email_unique` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('andy@example.com', 'andy');
INSERT INTO `person` VALUES ('john@example.com', 'john');

-- ----------------------------
-- Table structure for update_rel
-- ----------------------------
DROP TABLE IF EXISTS `update_rel`;
CREATE TABLE `update_rel` (
  `requestor` varchar(255) NOT NULL,
  `target` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of update_rel
-- ----------------------------
INSERT INTO `update_rel` VALUES ('andy@qq.com', 'peter@example.com', '');
INSERT INTO `update_rel` VALUES ('dann@example.com', 'andy@qq.com', '');
INSERT INTO `update_rel` VALUES ('andy@qq.com', 'john@ww.com', '');
