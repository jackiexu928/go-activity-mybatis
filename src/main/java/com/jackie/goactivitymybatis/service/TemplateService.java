package com.jackie.goactivitymybatis.service;

import com.alibaba.fastjson.JSON;
import com.jackie.goactivitymybatis.common.enums.GoActivityCodeEnum;
import com.jackie.goactivitymybatis.domain.query.BaseVoidQuery;
import com.jackie.goactivitymybatis.domain.request.BaseIdReqDTO;
import com.jackie.goactivitymybatis.domain.request.TemplateAddReqDTO;
import com.jackie.goactivitymybatis.domain.response.AccountLoginRespDTO;
import com.jackie.goactivitymybatis.domain.response.TemplateRespDTO;
import com.jackie.goactivitymybatis.entity.ActivityDetail;
import com.jackie.goactivitymybatis.entity.Template;
import com.jackie.goactivitymybatis.exception.GoActivityException;
import com.jackie.goactivitymybatis.mapper.TemplateMapper;
import com.jackie.goactivitymybatis.process.AbstractService;
import com.jackie.goactivitymybatis.process.Context;
import com.jackie.goactivitymybatis.util.ListUtil;
import com.jackie.goactivitymybatis.util.TrackHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-24
 */
@Service
public class TemplateService extends AbstractService {
    @Autowired
    private TemplateMapper templateMapper;

    public Context<BaseVoidQuery, List<TemplateRespDTO>> getTemplateList(BaseVoidQuery query){
        Context<BaseVoidQuery, List<TemplateRespDTO>> context = new Context<>();
        //获取登陆信息
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        //先查询公有的模版，按type排序
        List<Template> publicList = templateMapper.findListByTemplateType(1);
        //查询私有模版，无序
        List<Template> privateList = templateMapper.findListByOpenId(accountLoginRespDTO.getOpenId());
        List<TemplateRespDTO> respList = new ArrayList<>();
        respList.addAll(JSON.parseArray(JSON.toJSONString(publicList), TemplateRespDTO.class));
        if (ListUtil.isNotEmpty(privateList)){
            respList.addAll(JSON.parseArray(JSON.toJSONString(privateList), TemplateRespDTO.class));
        }
        context.setResult(respList);
        return context;
    }

    public Context<TemplateAddReqDTO, Void> addTemplate(TemplateAddReqDTO reqDTO){
        Context<TemplateAddReqDTO, Void> context = new Context<>();
        Date now = new Date();
        //获取登陆信息
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Template template = new Template();
        template.setOpenId(accountLoginRespDTO.getOpenId());
        template.setTemplateType(2);
        template.setType(reqDTO.getType());
        template.setName(reqDTO.getName());
        template.setCreateId(accountLoginRespDTO.getOpenId());
        template.setUpdateId(accountLoginRespDTO.getOpenId());
        template.setCreateTime(now);
        template.setUpdateTime(now);
        if (!(StringUtils.isEmpty(reqDTO.getTheme()) && StringUtils.isEmpty(reqDTO.getRemark())
                && null == reqDTO.getLimitNum() && null == reqDTO.getCost())){
            ActivityDetail detail = new ActivityDetail();
            detail.setTheme(reqDTO.getTheme());
            detail.setLimitNum(reqDTO.getLimitNum());
            detail.setCost(reqDTO.getCost());
            detail.setRemark(reqDTO.getRemark());
            template.setDetail(JSON.toJSONString(detail));
        }
        templateMapper.insert(template);
        return context;
    }

    public Context<BaseIdReqDTO, Void> deleteTemplate(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, Void> context = new Context<>();
        Template template = templateMapper.findById(reqDTO.getId());
        if (null == template){
            throw new GoActivityException(GoActivityCodeEnum.NO_RECORD);
        }
        templateMapper.deleteById(reqDTO.getId());
        return context;
    }

    public Context<BaseIdReqDTO, Template> getTemplateById(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, Template> context = new Context<>();
        Template template = templateMapper.findById(reqDTO.getId());
        if (null == template){
            throw new GoActivityException(GoActivityCodeEnum.NO_RECORD);
        }
        context.setResult(template);
        return context;
    }
}
