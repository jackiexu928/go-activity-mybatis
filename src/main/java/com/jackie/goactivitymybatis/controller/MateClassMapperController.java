package com.jackie.goactivitymybatis.controller;

import com.jackie.goactivitymybatis.entity.MateClassMapper;
import com.jackie.goactivitymybatis.service.MateClassMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-23
 */
@RestController
public class MateClassMapperController {
    @Autowired
    private MateClassMapperService mateClassMapperService;

    @GetMapping("test")
    public String test(){
        MateClassMapper mateClassMapper = new MateClassMapper();
        mateClassMapper.setSchoolId(1L);
        mateClassMapper.setClassId(2L);
        mateClassMapper.setMateId(3L);
        mateClassMapper.setMateType(4);
        mateClassMapper.setValidFlag(1);
        mateClassMapperService.test(mateClassMapper);
        return "success";
    }
}
