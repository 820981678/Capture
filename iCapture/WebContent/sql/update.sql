## 创建label和common_pages表的中间表
DROP TABLE IF EXISTS `commonToLabel`;
CREATE TABLE `commonToLabel`(
	`common_id` BIGINT NOT NULL COMMENT '保存common_pages表的id',
	`label_id` BIGINT NOT NULL COMMENT '保存label表的id'
) COMMENT '标签表和common_pages的中间关联表';

## 创建标签表
DROP TABLE IF EXISTS `label`;
CREATE TABLE `label`(
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(32) NOT NULL COMMENT '标签名称',
	PRIMARY KEY (`ID`)
) COMMENT '用于保存用户自定义的标签';
ALTER TABLE `label` ADD UNIQUE(`name`);

## 创建分组表
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`(
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(32) NOT NULL COMMENT '分组名称',
	PRIMARY KEY (`ID`)
) COMMENT '用于保存用户自定义的分组';

## 为common_pages添加分组字段
ALTER TABLE common_pages ADD COLUMN group_groupid BIGINT DEFAULT null COMMENT '保存分组表的id';

## 为common_pages添加是否查看字段
ALTER TABLE common_pages ADD COLUMN ISSEE SMALLINT DEFAULT 0 COMMENT '记录该条新闻是否已经查看,0未查看,1已查看';

## 为关键字添加 预定义分组字段
ALTER TABLE tblkeyword ADD COLUMN GROUPID SMALLINT COMMENT '该关键字的默认分组';