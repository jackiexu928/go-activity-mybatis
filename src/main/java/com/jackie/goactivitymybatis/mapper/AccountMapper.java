package com.jackie.goactivitymybatis.mapper;

import com.jackie.goactivitymybatis.entity.Account;
import org.apache.ibatis.annotations.*;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-23
 */
@Mapper
public interface AccountMapper {
    @Select("select * from account where open_id = #{openId}")
    Account selectByOpenId(String openId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into account " +
            "(open_id, nick_name, avatar_url, gender, country, province, city, language, login_num, create_time, update_time) " +
            "values(#{openId}, #{nickName}, #{avatarUrl}, #{gender}, #{country}, #{province}, #{city}, #{language}, " +
            "#{loginNum}, #{createTime}, #{updateTime})")
    int insert(Account account);

    @Update("update account set nick_name = #{nickName}, avatar_url = #{avatarUrl}, gender = #{gender}, " +
            "country = #{country}, province = #{province}, city = #{city}, language = #{language}, login_num = #{loginNum}," +
            "create_time = #{createTime}, update_time = #{updateTime}" +
            "where id = #{id}")
    int updateById(Account account);
}
