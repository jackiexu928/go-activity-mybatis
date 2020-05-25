package com.jackie.goactivitymybatis.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-19
 */
@Data
public class Message extends BaseEntity {
    private Long id;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 用户ID
     */
    private String openId;
    /**
     * 是否公开，0-仅自己可见，1-公开
     * 查询时，会优先展示自己的留言
     */
    private Integer open;
    /**
     * 留言内容
     */
    private String content;
    /**
     * 0-失效，1-有效
     */
    private Integer validFlag;
}
