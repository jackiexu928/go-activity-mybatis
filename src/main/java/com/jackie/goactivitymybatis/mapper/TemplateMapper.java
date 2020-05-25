package com.jackie.goactivitymybatis.mapper;

import com.jackie.goactivitymybatis.entity.Template;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-24
 */

/**
 * `ID` bigint(128) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 *   `template_type` int(2) NOT NULL DEFAULT '2' COMMENT '模版类型，1-公共，2-私有',
 *   `open_id` varchar(32) DEFAULT NULL COMMENT 'openId',
 *   `name` varchar(32) NOT NULL COMMENT '模版名称',
 *   `type` int(2) NOT NULL DEFAULT '7' COMMENT '种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他',
 *   `detail` text COMMENT '模版内容',
 *   `create_id` varchar(32) NOT NULL COMMENT '创建用户id',
 *   `create_time` datetime NOT NULL COMMENT '数据创建时间',
 *   `update_id` varchar(32) NOT NULL COMMENT '最后修改ID',
 *   `update_time` datetime NOT NULL COMMENT '数据最后修改时间',
 */
@Mapper
public interface TemplateMapper {

    @Select("select * from template where template_type = #{template} order by type")
    List<Template> findListByTemplateType(Integer templateType);

    @Select("select * from template where template_type = 2 and open_id = #{openId} order by type")
    List<Template> findListByOpenId(String openId);

    @Insert("insert into template(template_type, open_id, name, type, detail, create_id, create_time, update_id, update_time) " +
            "values(#{templateType},#{openId},#{name},#{type},#{detail},#{createId},#{createTime},#{updateId},#{updateTime},)")
    int insert(Template template);

    @Select("select * from template where id = #{id}")
    Template findById(Long id);

    @Delete("delete from template where id = #{id}")
    int deleteById(Long id);

}
