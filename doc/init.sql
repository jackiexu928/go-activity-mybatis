CREATE TABLE `account` (
    `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `open_id` VARCHAR(32) NOT NULL COMMENT 'openId',
    `nick_name` varchar(32) NOT NULL COMMENT '用户昵称',
    `avatar_url` varchar(256) DEFAULT NULL COMMENT '头像地址',
    `gender` smallint(2) DEFAULT '0' COMMENT '性别：0-未知，1-男，2-女',
    `country` varchar(64) DEFAULT NULL COMMENT '国家',
    `province` varchar(128) DEFAULT NULL COMMENT '省',
    `city` varchar(128) DEFAULT NULL COMMENT '市',
    `language` varchar(32) DEFAULT NULL COMMENT '语言',
    `login_num` int(10) DEFAULT NULL,
    `CREATE_TIME` datetime NOT NULL COMMENT '数据创建时间',
    `UPDATE_TIME` datetime NOT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `uk_open_id` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='季度填报计划表';