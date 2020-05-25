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
    `create_time` datetime NOT NULL COMMENT '数据创建时间',
    `update_time` datetime NOT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `uk_open_id` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='帐户表';

CREATE TABLE `login_info` (
    `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `open_id` VARCHAR(32) NOT NULL COMMENT 'openId',
    `nick_name` varchar(32) NOT NULL COMMENT '用户昵称',
    `login_time` datetime NOT NULL COMMENT '登陆时间',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='登陆记录表';

CREATE TABLE `template` (
    `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `template_type` int(2) NOT NULL DEFAULT '2' COMMENT '模版类型，1-公共，2-私有',
    `open_id` VARCHAR(32) DEFAULT NULL COMMENT 'openId',
    `name` varchar(32) NOT NULL COMMENT '模版名称',
    `type` int(2) NOT NULL DEFAULT '7' COMMENT '种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他',
    `detail` text COMMENT '模版内容',
    `create_id` VARCHAR(32) NOT NULL COMMENT '创建用户id',
    `create_time` datetime NOT NULL COMMENT '数据创建时间',
    `update_id` VARCHAR(32) NOT NULL COMMENT '最后修改ID',
    `update_time` datetime NOT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='活动模版表';

CREATE TABLE `activity_detail` (
    `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `template_id` bigint(128) DEFAULT NULL COMMENT '模版id',
    `type` int(2) NOT NULL DEFAULT '7' COMMENT '种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他',
    `theme` VARCHAR(32) NOT NULL COMMENT '主题',
    `start_time` datetime NOT NULL COMMENT '活动开始时间',
    `start_week` VARCHAR(16) NOT NULL COMMENT '开始时间星期',
    `end_join_time` datetime NOT NULL COMMENT '活动报名截止时间',
    `end_join_week` VARCHAR(16) NOT NULL COMMENT '报名截止时间星期',
    `open_id` VARCHAR(32) DEFAULT NULL COMMENT 'openId',
    `limit_num` int(3) NOT NULL DEFAULT '-1' COMMENT '人数限制',
    `join_num` int(3) NOT NULL DEFAULT '1' COMMENT '参加人数',
    `cost` int(2) NOT NULL COMMENT '费用，1-免费，2-aa，3-自费，4-其他',
    `cost_remark` varchar(128) DEFAULT NULL COMMENT '费用说明',
    `address` varchar(256) NOT NULL COMMENT '地点',
    `address_name` varchar(256) NOT NULL COMMENT '地点名称',
    `latitude` double(6,4) NOT NULL COMMENT '纬度',
    `longitude` double(10,7) NOT NULL COMMENT '经度',
    `duty` int(2) NOT NULL COMMENT '免责，0-未勾选（前端需校验）1-已勾选',
    `remark` varchar(256) NOT NULL COMMENT '活动说明',
    `valid_flag` int(2) NOT NULL DEFAULT '1' COMMENT '是否有效，0-已删除，1-有效',
    `create_id` VARCHAR(32) NOT NULL COMMENT '创建用户id',
    `create_time` datetime NOT NULL COMMENT '数据创建时间',
    `update_id` VARCHAR(32) NOT NULL COMMENT '最后修改ID',
    `update_time` datetime NOT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='活动表';

CREATE TABLE `activity_record` (
    `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `open_id` VARCHAR(32) NOT NULL COMMENT 'openId',
    `activity_id` bigint(128) NOT NULL COMMENT '活动ID',
    `activity_type` int(2) NOT NULL DEFAULT '7' COMMENT '种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他',
    `type` int(2) NOT NULL COMMENT '参与类型，1-组织者，2-参与者',
    `create_id` VARCHAR(32) NOT NULL COMMENT '创建用户id',
    `create_time` datetime NOT NULL COMMENT '数据创建时间',
    `update_id` VARCHAR(32) NOT NULL COMMENT '最后修改ID',
    `update_time` datetime NOT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户活动记录表';

CREATE TABLE `message` (
    `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `open_id` VARCHAR(32) NOT NULL COMMENT 'openId',
    `activity_id` bigint(128) NOT NULL COMMENT '活动ID',
    `open` int(2) NOT NULL DEFAULT '1' COMMENT '是否公开，0-仅自己可见，1-公开',
    `content` VARCHAR(256) NOT NULL COMMENT '留言内容',
    `valid_flag` int(2) NOT NULL DEFAULT '1' COMMENT '是否有效，0-已删除，1-有效',
    `create_id` VARCHAR(32) NOT NULL COMMENT '创建用户id',
    `create_time` datetime NOT NULL COMMENT '数据创建时间',
    `update_id` VARCHAR(32) NOT NULL COMMENT '最后修改ID',
    `update_time` datetime NOT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='留言表';
