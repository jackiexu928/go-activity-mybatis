package com.jackie.goactivitymybatis.entity;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-17
 */
@Data
public class ActivityRecord extends BaseEntity{
    private Long id;
    /**
     * 用户openId
     */
    private String openId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他，默认为其他
     */
    private Integer activityType;
    /**
     * 参与类型，1-组织者，2-参与者
     */
    private Integer type;
}
