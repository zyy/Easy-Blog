CREATE DATABASE IF NOT EXISTS `blog` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `blog`;

CREATE TABLE `user` (
`id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
`username` varchar(50) NOT NULL COMMENT '用户名',
`sex` tinyint(1) NULL COMMENT '性别',
`password` varchar(200) NOT NULL COMMENT '密码',
`phone` varchar(50) NULL COMMENT '电话',
`email` varchar(100) NULL COMMENT '电子邮箱',
`name` varchar(50) NOT NULL COMMENT '姓名',
`website` varchar(200) NULL COMMENT '网站',
`isadmin` tinyint(1) NOT NULL COMMENT '是否是管理员',
PRIMARY KEY (`id`) )COMMENT='用户';

CREATE TABLE `post` (
`id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
`title` varchar(255) NOT NULL COMMENT '标题',
`content` longtext NOT NULL COMMENT '内容',
`user_id` int(10) NULL COMMENT '作者',
`post_date` datetime NULL COMMENT '发表时间',
`category_id` int(10) NULL COMMENT '类别',
PRIMARY KEY (`id`) )COMMENT='文章';

CREATE TABLE `comment` (
`id` int(10) UNSIGNED NULL AUTO_INCREMENT COMMENT 'ID',
`comment` longtext NOT NULL COMMENT '回复内容',
`post_id` int(10) NULL COMMENT '文章',
`user_id` int NULL COMMENT '回复人',
`comment_date` date NOT NULL COMMENT '回复时间',
PRIMARY KEY (`id`) )COMMENT='回复';

CREATE TABLE `tag` (
`id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
`name` varchar(100) NOT NULL COMMENT '名称',
`count` decimal(10,0) NOT NULL DEFAULT 0 COMMENT '计数',
PRIMARY KEY (`id`) )COMMENT='标签';

CREATE TABLE `category` (
`id` int(10) NULL AUTO_INCREMENT COMMENT 'ID',
`name` varchar(100) NULL COMMENT '名称',
PRIMARY KEY (`id`) )COMMENT='分类';

CREATE TABLE `posttag` (
`id` int(10) NULL AUTO_INCREMENT COMMENT 'ID',
`post_id` int(10) NULL COMMENT '文章',
`tag_id` int(10) NULL COMMENT '标签',
PRIMARY KEY (`id`) )COMMENT='文章标签关联';

ALTER TABLE `post` ADD CONSTRAINT `fk_post_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
ALTER TABLE `post` ADD CONSTRAINT `fk_post_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);
ALTER TABLE `comment` ADD CONSTRAINT `fk_comment_post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`);
ALTER TABLE `comment` ADD CONSTRAINT `fk_comment_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
