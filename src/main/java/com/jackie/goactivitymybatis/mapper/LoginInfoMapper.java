package com.jackie.goactivitymybatis.mapper;

import com.jackie.goactivitymybatis.entity.LoginInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-24
 */
@Mapper
public interface LoginInfoMapper {

    @Insert("insert into login_info(open_id, nick_name, login_time) values(#{openId},#{nickName},#{loginTime})")
    int insert(LoginInfo loginInfo);
}
