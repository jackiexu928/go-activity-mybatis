package com.jackie.goactivitymybatis.service;

import com.alibaba.fastjson.JSON;
import com.jackie.goactivitymybatis.common.constant.RedisConstants;
import com.jackie.goactivitymybatis.domain.request.LoginReqDTO;
import com.jackie.goactivitymybatis.domain.response.LoginTokenRespDTO;
import com.jackie.goactivitymybatis.entity.Account;
import com.jackie.goactivitymybatis.entity.LoginInfo;
import com.jackie.goactivitymybatis.mapper.AccountMapper;
import com.jackie.goactivitymybatis.mapper.LoginInfoMapper;
import com.jackie.goactivitymybatis.process.AbstractService;
import com.jackie.goactivitymybatis.process.Context;
import com.jackie.goactivitymybatis.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-23
 */
@Service
public class AccountService extends AbstractService {
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private LoginInfoMapper loginInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public Context<LoginReqDTO, LoginTokenRespDTO> login(LoginReqDTO reqDTO){
        Context<LoginReqDTO, LoginTokenRespDTO> context = new Context<>();
        Date now = new Date();
        //获取token
        String token;
        //避免用户短时间内频繁登陆登出操作
        //根据openId查询redis判断是否短时间内已登陆过，如果已登陆过，则返回redis中的token，否则新增
        Object cache = redisTemplate.opsForValue().get(RedisConstants.GO_ACTIVITY_USER_TOKEN + reqDTO.getOpenId());
        //String cache = redisTemplate.opsForValue().get().toString();
        if (cache == null){
            token = UuidUtil.getUUidNoSplit();
        } else {
            token = cache.toString();
        }
        //查询帐户表中是否存在，存在则修改，不存在则添加
        Account account = accountMapper.selectByOpenId(reqDTO.getOpenId());
        if (account == null) {
            account = JSON.parseObject(JSON.toJSONString(reqDTO), Account.class);
            account.setOpenId(reqDTO.getOpenId());
            account.setLoginNum(1);
            account.setCreateTime(now);
            account.setUpdateTime(now);
            accountMapper.insert(account);
        } else {
            account.setNickName(reqDTO.getNickName());
            account.setAvatarUrl(reqDTO.getAvatarUrl());
            account.setGender(reqDTO.getGender());
            account.setCountry(reqDTO.getCountry());
            account.setProvince(reqDTO.getProvince());
            account.setCity(reqDTO.getCity());
            account.setLanguage(reqDTO.getLanguage());
            account.setLoginNum(account.getLoginNum() + 1);
            account.setUpdateTime(now);
            accountMapper.updateById(account);
        }
        //登陆记录表添加登陆记录
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setOpenId(reqDTO.getOpenId());
        loginInfo.setNickName(reqDTO.getNickName());
        loginInfo.setLoginTime(now);
        loginInfoMapper.insert(loginInfo);
        //将token存入redis，登陆有效期24小时
        redisTemplate.opsForValue().set(RedisConstants.GO_ACTIVITY_USER_TOKEN + reqDTO.getOpenId(), token, 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(RedisConstants.GO_ACTIVITY_USER_USER_INFO + reqDTO.getOpenId(), JSON.toJSONString(account), 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(RedisConstants.GO_ACTIVITY_USER_PREFIX + token, reqDTO.getOpenId(), 24, TimeUnit.HOURS);
        LoginTokenRespDTO respDTO = new LoginTokenRespDTO();
        respDTO.setToken(token);
        context.setResult(respDTO);
        return context;
    }

}
