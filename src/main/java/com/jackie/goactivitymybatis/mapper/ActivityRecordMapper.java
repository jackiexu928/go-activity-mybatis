package com.jackie.goactivitymybatis.mapper;

import com.jackie.goactivitymybatis.entity.ActivityRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-25
 */

/**
 * `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 *   `open_id` varchar(32) NOT NULL COMMENT 'openId',
 *   `activity_id` bigint(128) NOT NULL COMMENT '活动ID',
 *   `activity_type` int(2) NOT NULL DEFAULT '7' COMMENT '种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他',
 *   `type` int(2) NOT NULL COMMENT '参与类型，1-组织者，2-参与者',
 *   `create_id` varchar(32) NOT NULL COMMENT '创建用户id',
 *   `create_time` datetime NOT NULL COMMENT '数据创建时间',
 *   `update_id` varchar(32) NOT NULL COMMENT '最后修改ID',
 *   `update_time` datetime NOT NULL COMMENT '数据最后修改时间',
 */
@Mapper
public interface ActivityRecordMapper {
    @Insert("insert into activity_record(open_id, activity_id, activity_type, type, create_id, create_time, update_id," +
            "update_time) values(#{openId}, #{activityId}, #{activityType}, #{type}, #{createId}, #{createTime}," +
            "#{updateId}, #{updateTime})")
    int insert(ActivityRecord activityRecord);

    @Select("<script>" +
            "select a.* from activity_record a join activity_detail b on a.activity_id = b.id " +
            "where a.open_id = #{openId}" +
            "<if test='willOrDone == 1'>" +
            "and (b.start_time &gt; #{now} or b.start_time = #{now})" +
            "</if>" +
            "<if test='willOrDone == 2'>" +
            "and b.start_time &lt; #{now}" +
            "</if>" +
            "order by a.create_time desc" +
            "</script>")
    List<ActivityRecord> findByOpenIdAndTime(String openId, Integer willOrDone, Date now);

    @Select("select * from activity_record where activity_id = #{id} order by create_time")
    List<ActivityRecord> findByActivityId(Long id);
}
