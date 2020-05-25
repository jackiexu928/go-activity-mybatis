package com.jackie.goactivitymybatis.entity;

import lombok.Data;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Description: 登陆记录
 *
 * @author xujj
 * @date 2020-05-09
 */
@Data
public class LoginInfo extends BaseEntity{
    private String id;
    private String openId;
    private String nickName;
    private Date loginTime;
}
