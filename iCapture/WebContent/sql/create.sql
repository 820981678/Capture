-- ----------------------------
-- bbs_lists 主论坛下分论坛列表
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `bbs_lists`;
CREATE TABLE `bbs_lists` (
  `id` bigint(20) NOT NULL auto_increment,
  `groupid` bigint(20) NOT NULL,
  `name` varchar(256) NOT NULL COMMENT 'xx',
  `url` varchar(512) default NULL COMMENT '起始地址',
  `title` varchar(1024) default NULL COMMENT '论坛标题',
  `status` smallint(6) default '0',
  `main_iframe` smallint(6) default '0' COMMENT '页上是否有frame',
  `scount` int(11) default '0',
  `last_date` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `bbs_url` (`url`(40)),
  KEY `bbs_groupid` (`groupid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='主论坛下分论坛列表';

-- ----------------------------
-- common_pages 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `common_pages`;
CREATE TABLE `common_pages` (
  `id` bigint(20) NOT NULL auto_increment,
  `groupid` mediumint(9) default NULL,
  `topicid` bigint(20) default NULL COMMENT 'topicid',
  `time_end` datetime default NULL,
  `stauts` smallint(6) default '0',
  `item0` char(128) default NULL COMMENT '标题',
  `item1` char(64) default NULL COMMENT '作者',
  `item2` char(32) default NULL COMMENT '时间',
  `item3` text COMMENT '内容',
  `group_groupid` bigint(20) default NULL COMMENT '保存分组表的id',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=gbk;

-- ----------------------------
-- commontolabel 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `commontolabel`;
CREATE TABLE `commontolabel` (
  `common_id` bigint(20) NOT NULL COMMENT '保存common_pages表的id',
  `label_id` bigint(20) NOT NULL COMMENT '保存label表的id'
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='标签表和common_pages的中间关联表';

-- ----------------------------
-- group 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(32) NOT NULL COMMENT '分组名称',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gbk COMMENT='用于保存用户自定义的分组';

-- ----------------------------
-- label 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `label`;
CREATE TABLE `label` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(32) NOT NULL COMMENT '标签名称',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk COMMENT='用于保存用户自定义的标签';

-- ----------------------------
-- tableinfo 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `tableinfo`;
CREATE TABLE `tableinfo` (
  `id` mediumint(9) NOT NULL auto_increment,
  `tablename` char(128) NOT NULL,
  `columnname` char(128) NOT NULL,
  `name` char(128) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- tblkeypages 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `tblkeypages`;
-- CREATE TABLE `tblkeypages` (
  `id` bigint(20) NOT NULL auto_increment,
  `keyid` bigint(20) default NULL COMMENT '关键字ID,',
  `groupid` bigint(20) default NULL COMMENT '论坛组',
  `topicid` bigint(20) default NULL COMMENT '对应页面',
  `count1` int(11) default NULL COMMENT '关键字匹配次数',
  `wtype` int(11) default NULL COMMENT '正面还是负面 ,从关键词中来，可以修改,1 正面，2负面,0不定',
  `pdate` char(21) default NULL COMMENT '发布日期',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- tblkeyword 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `tblkeyword`;
CREATE TABLE `tblkeyword` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` char(255) default NULL COMMENT '关键字定义',
  `wtype` int(11) default NULL COMMENT ' 是正面还是负面的，1 正面，2负面,0不定',
  `stype` int(11) default NULL COMMENT '关键字处理方式，0 是全词匹配，1是分隔后全部匹配，2是分隔后匹配任何一个',
  `idate` char(21) default NULL COMMENT '创建的时间',
  `status` int(11) default NULL COMMENT '状态，1 是无效，0是有效',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- topic_groups 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `topic_groups`;
CREATE TABLE `topic_groups` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(256) NOT NULL COMMENT '分组名称，如 XX论坛XX片版',
  `topic_name` char(64) NOT NULL COMMENT '主要是英文，用于建立数据表用。如 linuxeden_fedora',
  `url` varchar(512) default NULL COMMENT '起始地址',
  `resource_dir` char(128) default NULL COMMENT '图片存放地址',
  `topic_level` smallint(6) default '3' COMMENT '下载层数 1是只下载链接, 2下载内容 3下载附加内容',
  `main_iframe` smallint(6) default '0' COMMENT '主页是否有 frame?, 0 没有，1有',
  `topic_iframe` smallint(6) default '0' COMMENT '主题中是否有 frame 0 没有，1有',
  `max_topics` int(11) default '-1' COMMENT '最大下载主题数，-1表示无限制，0不下载',
  `max_reply` int(11) default '0' COMMENT '下载最大回复页数 -1表示无限制，0不下载',
  `max_resource` int(11) default '0' COMMENT '下载最多资源数,-1表示无限制，0不下载',
  `scripts_iframe` char(128) default NULL COMMENT '用于检查 frame 的脚本文件名',
  `scripts_topic` char(128) default NULL COMMENT '用于检查首页中主题的脚本文件名',
  `scripts_content` char(128) default NULL COMMENT '用于处理主题的脚本文件名,第一页',
  `scripts_links` char(128) default NULL COMMENT '用于检查主题页中要使用的链接的脚本文件名',
  `scripts_reply` char(128) default NULL COMMENT '用于处理回复的脚本文件名，分页',
  `scripts_pages` char(128) default NULL COMMENT '用于检查回复链接的脚本文件名',
  `max_tasks` smallint(6) default '1' COMMENT '最大线程数',
  `max_depth` smallint(6) default '1' COMMENT '最大下载深度',
  `method` char(16) default NULL,
  `param` char(128) default NULL,
  `sessionid` char(64) default NULL,
  `need_down` smallint(6) default '1' COMMENT '0，不下载，1下载，2用下载服务下载',
  `status` smallint(6) default '0',
  `last_date` datetime default NULL,
  `topic_type` smallint(6) default '0' COMMENT '0 ,新闻 1 论坛 2 论坛有bbs_list',
  `update_interval` char(32) default NULL,
  `date_pos` tinyint(4) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- topic_lists 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `topic_lists`;
CREATE TABLE `topic_lists` (
  `id` bigint(20) NOT NULL auto_increment,
  `groupid` bigint(20) NOT NULL COMMENT '论坛起始',
  `bbs_id` bigint(20) default '0' COMMENT '分论坛ID',
  `url` varchar(512) default NULL,
  `title` varchar(1024) default NULL,
  `topic_level` smallint(6) default '3' COMMENT '下载层数,从 topic_group中写入',
  `create_time` datetime default NULL,
  `finish_time` datetime default NULL,
  `depth` int(11) default '1' COMMENT '当前深度',
  `status` tinyint(4) default '1' COMMENT '与下载层数相对，分别是1标题,2内容,3 附加内容',
  `handle_status` tinyint(4) default '0' COMMENT '本主题数据是否已经处理过，对于数据转存用',
  `scount` int(11) default '0',
  `url_type` smallint(6) default '0' COMMENT '1 代表是需要下载主页',
  `url_status` smallint(6) default '1' COMMENT ' 下载层数,用于存储当前指定的类型,status 中值从此处参考',
  PRIMARY KEY  (`id`),
  KEY `topic_lists_bbsid` (`bbs_id`),
  KEY `topic_lists_groupid` (`groupid`),
  KEY `topic_lists_url` (`url`(40))
) ENGINE=MyISAM AUTO_INCREMENT=385 DEFAULT CHARSET=utf8;

-- ----------------------------
-- topic_resources 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `topic_resources`;
CREATE TABLE `topic_resources` (
  `id` bigint(20) NOT NULL auto_increment,
  `topicid` bigint(20) NOT NULL,
  `url` varchar(512) default NULL,
  `title` varchar(1024) default NULL,
  `res_type` smallint(6) default '0' COMMENT '资源类型 0 是页面资源，1下载资源,...',
  `create_time` datetime default NULL,
  `finish_time` datetime default NULL,
  `status` tinyint(4) default '0',
  `scount` int(11) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- users 
-- Author: huxiaohuan 2014-11-26
-- ----------------------------
-- DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` char(32) default NULL,
  `password` char(32) default NULL,
  `status` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;