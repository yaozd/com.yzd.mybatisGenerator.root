-- auto Generated on 2019-10-14 16:59:33 
-- DROP TABLE IF EXISTS `tb_resources`; 
CREATE TABLE tb_resources(
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `stage_id` BIGINT NOT NULL DEFAULT -1 COMMENT 'stageId',
    `host_group_id` BIGINT NOT NULL DEFAULT -1 COMMENT 'hostGroupId',
    `vip_id` BIGINT NOT NULL DEFAULT -1 COMMENT 'vipId',
    `name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'name',
    `ip` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'ip',
    `cpu` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'cpu',
    `resources_type` INTEGER(12) NOT NULL DEFAULT -1 COMMENT 'resourcesType',
    `ram` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'ram',
    `disk` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'disk',
    `gmt_modified` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'gmtModified',
    `gmt_create` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'gmtCreate',
    `gmt_is_deleted` INTEGER(12) NOT NULL DEFAULT -1 COMMENT 'gmtIsDeleted',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'tb_resources';
