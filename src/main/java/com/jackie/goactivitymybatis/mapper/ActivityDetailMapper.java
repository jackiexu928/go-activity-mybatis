package com.jackie.goactivitymybatis.mapper;

import com.jackie.goactivitymybatis.entity.ActivityDetail;
import org.apache.ibatis.annotations.*;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-25
 */

/**
 * `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 *   `template_id` bigint(128) DEFAULT NULL COMMENT '模版id',
 *   `type` int(2) NOT NULL DEFAULT '7' COMMENT '种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他',
 *   `theme` varchar(32) NOT NULL COMMENT '主题',
 *   `start_time` datetime NOT NULL COMMENT '活动开始时间',
 *   `start_week` varchar(16) NOT NULL COMMENT '开始时间星期',
 *   `end_join_time` datetime NOT NULL COMMENT '活动报名截止时间',
 *   `end_join_week` varchar(16) NOT NULL COMMENT '报名截止时间星期',
 *   `limit_num` int(3) NOT NULL DEFAULT '-1' COMMENT '人数限制',
 *   `join_num` int(3) NOT NULL DEFAULT '1' COMMENT '参加人数',
 *   `cost` int(2) NOT NULL COMMENT '费用，1-免费，2-aa，3-自费，4-其他',
 *   `cost_remark` varchar(128) DEFAULT NULL COMMENT '费用说明',
 *   `address` varchar(256) NOT NULL COMMENT '地点',
 *   `address_name` varchar(256) NOT NULL COMMENT '地点名称',
 *   `latitude` double(6,4) NOT NULL COMMENT '纬度',
 *   `longitude` double(10,7) NOT NULL COMMENT '经度',
 *   `duty` int(2) NOT NULL COMMENT '免责，0-未勾选（前端需校验）1-已勾选',
 *   `remark` varchar(256) NOT NULL COMMENT '活动说明',
 *   `valid_flag` int(2) NOT NULL DEFAULT '1' COMMENT '是否有效，0-已删除，1-有效',
 *   `create_id` varchar(32) NOT NULL COMMENT '创建用户id',
 *   `create_time` datetime NOT NULL COMMENT '数据创建时间',
 *   `update_id` varchar(32) NOT NULL COMMENT '最后修改ID',
 *   `update_time` datetime NOT NULL COMMENT '数据最后修改时间',
 */
@Mapper
public interface ActivityDetailMapper {
    @Insert("insert into activity_detail(template_id, type, theme, start_time, start_week, end_join_time, end_join_week," +
            "limit_num, join_num, cost, cost_remark, address, address_name, latitude, longitude, duty, remark," +
            "valid_flag, create_id, create_time, update_id, update_time) values(#{templateId}, #{type}, #{theme}," +
            "#{startTime}, #{startWeek}, #{endJoinTime}, #{endJoinWeek}, #{limitNum}, #{joinNum}, #{cost}," +
            "#{costRemark}, #{address}, #{addressName}, #{latitude}, #{longitude}, #{duty}, #{remark}, #{validFlag}, " +
            "#{createId}, #{createTime}, #{updateId}, #{updateTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Long insert(ActivityDetail activityDetail);

    @Select("select * from activity_detail where id = #{id}")
    ActivityDetail findById(Long id);

    @Update("update activity_detail set valid_flag = 0, update_id = #{updateId}, update_time = #{updateTime} where id = #{id}")
    int deleteById(ActivityDetail activityDetail);

}
