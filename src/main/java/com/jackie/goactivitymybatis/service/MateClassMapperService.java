package com.jackie.goactivitymybatis.service;

import com.jackie.goactivitymybatis.entity.MateClassMapper;
import com.jackie.goactivitymybatis.mapper.MateClassMapperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-23
 */
@Service
public class MateClassMapperService {
    @Autowired
    private MateClassMapperMapper mateClassMapperMapper;

    public void test(MateClassMapper mateClassMapper){
        mateClassMapperMapper.insert(mateClassMapper);
    }
}
