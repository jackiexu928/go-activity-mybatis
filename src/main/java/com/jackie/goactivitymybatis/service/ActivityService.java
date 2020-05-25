package com.jackie.goactivitymybatis.service;

import com.jackie.goactivitymybatis.common.enums.CostEnum;
import com.jackie.goactivitymybatis.common.enums.GoActivityCodeEnum;
import com.jackie.goactivitymybatis.domain.query.ActivityListQuery;
import com.jackie.goactivitymybatis.domain.request.ActivityAddReqDTO;
import com.jackie.goactivitymybatis.domain.request.BaseIdReqDTO;
import com.jackie.goactivitymybatis.domain.response.AccountLoginRespDTO;
import com.jackie.goactivitymybatis.domain.response.ActivityDetailRespDTO;
import com.jackie.goactivitymybatis.domain.response.ActivityRecordRespDTO;
import com.jackie.goactivitymybatis.domain.response.JoinAccountRespDTO;
import com.jackie.goactivitymybatis.entity.Account;
import com.jackie.goactivitymybatis.entity.ActivityDetail;
import com.jackie.goactivitymybatis.entity.ActivityRecord;
import com.jackie.goactivitymybatis.exception.GoActivityException;
import com.jackie.goactivitymybatis.mapper.AccountMapper;
import com.jackie.goactivitymybatis.mapper.ActivityDetailMapper;
import com.jackie.goactivitymybatis.mapper.ActivityRecordMapper;
import com.jackie.goactivitymybatis.process.AbstractService;
import com.jackie.goactivitymybatis.process.Context;
import com.jackie.goactivitymybatis.util.ListUtil;
import com.jackie.goactivitymybatis.util.TrackHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-25
 */
@Service
public class ActivityService extends AbstractService {
    @Autowired
    private ActivityDetailMapper activityDetailMapper;
    @Autowired
    private ActivityRecordMapper activityRecordMapper;
    @Autowired
    private AccountMapper accountMapper;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public Context<ActivityAddReqDTO, Void> addActivity(ActivityAddReqDTO reqDTO) {
        Context<ActivityAddReqDTO, Void> context = new Context<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        Date now = cal.getTime();
        //添加活动
        ActivityDetail activityDetail = new ActivityDetail();
        if (!StringUtils.isEmpty(reqDTO.getTemplateId())){
            activityDetail.setTemplateId(reqDTO.getTemplateId());
        }
        if (reqDTO.getType() != null){
            activityDetail.setType(reqDTO.getType());
        }
        activityDetail.setTheme(reqDTO.getTheme());
        try {
            activityDetail.setStartTime(simpleDateFormat.parse(year + "-" + reqDTO.getStartDate().substring(0, 6)
                    + reqDTO.getStartHour() + ":" + reqDTO.getStartMinute()));
            activityDetail.setEndJoinTime(simpleDateFormat.parse(year + "-" + reqDTO.getEndJoinDate().substring(0, 6)
                    + reqDTO.getEndJoinHour() + ":" + reqDTO.getEndJoinMinute()));
        } catch (Exception e){
            throw new GoActivityException(e);
        }
        activityDetail.setStartWeek(reqDTO.getStartDate().substring(6, 8));
        activityDetail.setEndJoinWeek(reqDTO.getEndJoinDate().substring(6, 8));
        if (reqDTO.getLimitNum() == null){
            activityDetail.setLimitNum(-1);
        } else {
            activityDetail.setLimitNum(reqDTO.getLimitNum());
        }
        activityDetail.setJoinNum(1);
        activityDetail.setCost(reqDTO.getCost());
        if (reqDTO.getCost() == 4){
            activityDetail.setCostRemark(reqDTO.getCostRemark());
        }
        activityDetail.setAddress(reqDTO.getAddress());
        activityDetail.setAddressName(reqDTO.getAddressName());
        activityDetail.setLatitude(reqDTO.getLatitude());
        activityDetail.setLongitude(reqDTO.getLongitude());
        activityDetail.setDuty(reqDTO.getDuty());
        if (!StringUtils.isEmpty(reqDTO.getRemark())){
            activityDetail.setRemark(reqDTO.getRemark());
        }
        activityDetail.setValidFlag(1);
        activityDetail.setCreateId(accountLoginRespDTO.getOpenId());
        activityDetail.setCreateTime(now);
        activityDetail.setUpdateId(accountLoginRespDTO.getOpenId());
        activityDetail.setUpdateTime(now);
        activityDetailMapper.insert(activityDetail);
        //添加活动记录，用户为组织者
        ActivityRecord activityRecord = new ActivityRecord();
        activityRecord.setOpenId(accountLoginRespDTO.getOpenId());
        activityRecord.setActivityId(activityDetail.getId());
        activityRecord.setActivityType(activityDetail.getType());
        activityRecord.setType(1);
        activityRecord.setCreateId(accountLoginRespDTO.getOpenId());
        activityRecord.setCreateTime(now);
        activityRecord.setUpdateId(accountLoginRespDTO.getOpenId());
        activityRecord.setUpdateTime(now);
        activityRecordMapper.insert(activityRecord);
        return context;
    }

    public Context<ActivityListQuery, List<ActivityRecordRespDTO>> getRecordList(ActivityListQuery query){
        Context<ActivityListQuery, List<ActivityRecordRespDTO>> context = new Context<>();
        List<ActivityRecordRespDTO> list = new ArrayList<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        List<ActivityRecord> recordList = activityRecordMapper
                .findByOpenIdAndTime(accountLoginRespDTO.getOpenId(), query.getWillOrDone(), new Date());
        if (ListUtil.isNotEmpty(recordList)){
            for (ActivityRecord record : recordList) {
                ActivityDetail activityDetail = activityDetailMapper.findById(record.getActivityId());
                ActivityRecordRespDTO respDTO = new ActivityRecordRespDTO();
                respDTO.setRecordId(record.getId());
                respDTO.setActivityId(record.getActivityId());
                respDTO.setActivityType(record.getActivityType());
                respDTO.setType(record.getType());
                respDTO.setTheme(activityDetail.getTheme());
                respDTO.setStartTime(simpleDateFormat.format(activityDetail.getStartTime())
                        + activityDetail.getStartWeek());
                respDTO.setAddressName(activityDetail.getAddressName());
                respDTO.setLimitNum(activityDetail.getLimitNum());
                respDTO.setJoinNum(activityDetail.getJoinNum());
                list.add(respDTO);
            }
        }
        context.setResult(list);
        return context;
    }

    public Context<BaseIdReqDTO, ActivityDetailRespDTO> getActivityDetail(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, ActivityDetailRespDTO> context = new Context<>();
        ActivityDetailRespDTO respDTO = new ActivityDetailRespDTO();
        Date now = new Date();
        //查询活动
        ActivityDetail activityDetail = activityDetailMapper.findById(reqDTO.getId());
        respDTO.setId(activityDetail.getId());
        respDTO.setTheme(activityDetail.getTheme());
        respDTO.setStartTime(simpleDateFormat.format(activityDetail.getStartTime())
                + activityDetail.getStartWeek());
        respDTO.setEndJoinTime(simpleDateFormat.format(activityDetail.getEndJoinTime())
                + activityDetail.getEndJoinWeek());
        respDTO.setAddressName(activityDetail.getAddressName());
        respDTO.setLatitude(activityDetail.getLatitude());
        respDTO.setLongitude(activityDetail.getLongitude());
        respDTO.setCostName(CostEnum.getNameByType(activityDetail.getCost()));
        respDTO.setCostRemark(activityDetail.getCostRemark());
        respDTO.setJoinNum(activityDetail.getJoinNum());
        respDTO.setLimitNum(activityDetail.getLimitNum());
        respDTO.setRemark(activityDetail.getRemark());
        if (now.before(activityDetail.getStartTime())) {
            respDTO.setWillOrDone(1);
        } else {
            respDTO.setWillOrDone(2);
        }
        context.setResult(respDTO);
        return context;
    }

    public Context<BaseIdReqDTO, List<JoinAccountRespDTO>> getJoinAccount(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, List<JoinAccountRespDTO>> context = new Context<>();
        List<JoinAccountRespDTO> list = new ArrayList<>();
        //查询活动
        ActivityDetail activityDetail = activityDetailMapper.findById(reqDTO.getId());
        if (activityDetail.getJoinNum() > 0){
            //查询记录
            List<ActivityRecord> recordList = activityRecordMapper.findByActivityId(reqDTO.getId());
            for (ActivityRecord record : recordList){
                Account account = accountMapper.selectByOpenId(record.getOpenId());
                JoinAccountRespDTO respDTO = new JoinAccountRespDTO();
                respDTO.setNickName(account.getNickName());
                respDTO.setAvatarUrl(account.getAvatarUrl());
                respDTO.setRole(record.getType());
                list.add(respDTO);
            }
        }
        context.setResult(list);
        return context;
    }

    public Context<BaseIdReqDTO, Void> deleteActivity(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, Void> context = new Context<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Date now = new Date();
        ActivityDetail activityDetail = activityDetailMapper.findById(reqDTO.getId());
        if (!activityDetail.getCreateId().equals(accountLoginRespDTO.getOpenId())){
            throw new GoActivityException(GoActivityCodeEnum.NO_JURISDICTION);
        }
        if (activityDetail.getStartTime().before(now)){
            throw new GoActivityException(GoActivityCodeEnum.ACTIVITY_IS_BEGIN);
        }
        activityDetail.setValidFlag(0);
        activityDetail.setUpdateId(accountLoginRespDTO.getOpenId());
        activityDetail.setUpdateTime(now);
        activityDetailMapper.deleteById(activityDetail);
        return context;
    }
}
