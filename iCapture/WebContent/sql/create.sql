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