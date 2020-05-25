package com.jackie.goactivitymybatis.mapper;

import com.jackie.goactivitymybatis.entity.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-25
 */
@Mapper
public interface MessageMapper {
    /**
     * `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
     *   `open_id` varchar(32) NOT NULL COMMENT 'openId',
     *   `activity_id` bigint(128) NOT NULL COMMENT '活动ID',
     *   `open` int(2) NOT NULL DEFAULT '1' COMMENT '是否公开，0-仅自己可见，1-公开',
     *   `content` varchar(256) NOT NULL COMMENT '留言内容',
     *   `valid_flag` int(2) NOT NULL DEFAULT '1' COMMENT '是否有效，0-已删除，1-有效',
     *   `create_id` varchar(32) NOT NULL COMMENT '创建用户id',
     *   `create_time` datetime NOT NULL COMMENT '数据创建时间',
     *   `update_id` varchar(32) NOT NULL COMMENT '最后修改ID',
     *   `update_time` datetime NOT NULL COMMENT '数据最后修改时间',
     * @param activityId
     * @return
     */
    @Select("select * from message where activity_id = #{activityId} and valid_flag = 1 order by create_time")
    List<Message> findByActivityId(Long activityId);

    @Insert("insert into message(open_id, activity_id, open, content, valid_flag, create_id, create_time, update_id," +
            "update_time) " +
            "values(#{openId}, #{activityId}, #{open}, #{content}, #{validFlag}, #{createId}, #{createTime}, " +
            "#{updateId}, #{updateTime})")
    int insert(Message message);

    @Select("select * from message where id = #{id}")
    Message findById(Long id);

    @Update("update message set valid_flag = 0, update_id = #{updateId}, update_time = #{updateTime} where id = #{id}")
    int deleteById(Message message);

    @Update("<script>" +
            "update message" +
            "<set>" +
                "<if test='open != null'>" +
                    "open = #{open}," +
                "</if>" +
                "<if test='content != null and content != \"\"'>" +
                    "content = #{content}," +
                "</if>" +
                "<if test='updateId != null'>" +
                    "update_id = #{updateId}," +
                "</if>" +
                "<if test='updateTime != null'>" +
                    "update_time = #{updateTime}," +
                "</if>" +
            "</set>" +
            "where id = #{id}" +
            "</script>")
    int updateBySelective(Message message);

    @Select("<script>" +
            "select * from message" +
            "where valid_flag = 1" +
            "<if test='id != null'>" +
                "and id = #{id}" +
            "</if>" +
            "<if test='openId != null'>" +
                "and open_id = #{openId}" +
            "</if>" +
            "<if test='activityId != null'>" +
                "and activity_id = #{activityId}" +
            "</if>" +
            "<if test='open != null'>" +
                "and open = #{open}" +
            "</if>" +
            "<if test='content != null and content != \"\"'>" +
                "and content like concat(concat('%',#{content}),'%')" +
            "</if>" +
            "order by create_time" +
            "</script>")
    List<Message> findBySelective(Message message);


}