db_test
//
DROP TABLE IF EXISTS `tb_test`;
CREATE TABLE `tb_test` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id`  bigint comment 'user_id',
  `amount` decimal(10,2) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';