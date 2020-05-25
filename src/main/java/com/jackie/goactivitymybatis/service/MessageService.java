package com.jackie.goactivitymybatis.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jackie.goactivitymybatis.common.enums.GoActivityCodeEnum;
import com.jackie.goactivitymybatis.domain.request.BaseIdReqDTO;
import com.jackie.goactivitymybatis.domain.request.MessageAddReqDTO;
import com.jackie.goactivitymybatis.domain.request.MessageUpdateReqDTO;
import com.jackie.goactivitymybatis.domain.response.AccountLoginRespDTO;
import com.jackie.goactivitymybatis.domain.response.MessageRespDTO;
import com.jackie.goactivitymybatis.entity.Account;
import com.jackie.goactivitymybatis.entity.Message;
import com.jackie.goactivitymybatis.exception.GoActivityException;
import com.jackie.goactivitymybatis.mapper.AccountMapper;
import com.jackie.goactivitymybatis.mapper.MessageMapper;
import com.jackie.goactivitymybatis.process.AbstractService;
import com.jackie.goactivitymybatis.process.Context;
import com.jackie.goactivitymybatis.util.ListUtil;
import com.jackie.goactivitymybatis.util.TrackHolder;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.management.Query;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-25
 */
@Service
public class MessageService extends AbstractService {
    private static int TASK_LENGTH = 20;
    private static ThreadFactory namedThreadFactory =
            new ThreadFactoryBuilder().setNameFormat("ActivityService-pool-%d").build();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            TASK_LENGTH,
            TASK_LENGTH * 5,
            60 * 60 * 24,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(2000),
            namedThreadFactory
    );
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private AccountMapper accountMapper;

    public Context<BaseIdReqDTO, List<MessageRespDTO>> getMessageList(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, List<MessageRespDTO>> context = new Context<>();
        List<MessageRespDTO> respList = new ArrayList<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        List<Message> selfList = messageMapper.findByActivityId(reqDTO.getId());
        if (ListUtil.isNotEmpty(selfList)){
            Map<Long, FutureTask<MessageRespDTO>> res = new HashMap<>();
            for (Message one : selfList){
                //不是自己的非公开留言则不显示
                if (!one.getOpenId().equals(accountLoginRespDTO.getOpenId()) && one.getOpen() == 0){
                    return null;
                }
                final Message message = one;
                FutureTask<MessageRespDTO> task = new FutureTask<>(new Callable<MessageRespDTO>() {
                    @Override
                    public MessageRespDTO call() throws Exception {
                        MessageRespDTO respDTO = new MessageRespDTO();
                        respDTO.setId(message.getId());
                        respDTO.setActivityId(message.getActivityId());
                        respDTO.setOpen(message.getOpen());
                        respDTO.setContent(message.getContent());
                        if (message.getOpenId().equals(accountLoginRespDTO.getOpenId())) {
                            respDTO.setSelf(1);
                        } else {
                            respDTO.setSelf(0);
                        }
                        Account account = accountMapper.selectByOpenId(message.getOpenId());
                        respDTO.setNickName(account.getNickName());
                        respDTO.setAvatarUrl(account.getAvatarUrl());
                        respDTO.setCreateTime(simpleDateFormat.format(message.getCreateTime()));
                        return respDTO;
                    }
                });
                res.put(one.getId(), task);
                executor.execute(task);
            }
            for (Message one : selfList){
                //不是自己的非公开留言则不显示
                if (!one.getOpenId().equals(accountLoginRespDTO.getOpenId()) && one.getOpen() == 0){
                    return null;
                }
                respList.add(this.getDataFromFutureTaskResponse(res, one.getId()));
            }
        }
        context.setResult(respList);
        return context;
    }
    private MessageRespDTO getDataFromFutureTaskResponse(Map<Long, FutureTask<MessageRespDTO>> res, Long id){
        if (CollectionUtils.isEmpty(res)){
            return null;
        }
        FutureTask<MessageRespDTO> response = res.get(id);
        try {
            if (response == null){
                return null;
            } else {
                return response.get();
            }
        } catch (Exception e){

        }
        return null;
    }

    public Context<BaseIdReqDTO, List<MessageRespDTO>> getMyMessageList(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, List<MessageRespDTO>> context = new Context<>();
        List<MessageRespDTO> respList = new ArrayList<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Message messageQuery = new Message();
        messageQuery.setActivityId(reqDTO.getId());
        messageQuery.setOpenId(accountLoginRespDTO.getOpenId());
        List<Message> selfList = messageMapper.findBySelective(messageQuery);
        if (ListUtil.isNotEmpty(selfList)){
            for (Message message : selfList){
                MessageRespDTO respDTO = new MessageRespDTO();
                respDTO.setId(message.getId());
                respDTO.setActivityId(message.getActivityId());
                respDTO.setOpen(message.getOpen());
                respDTO.setContent(message.getContent());
                if (message.getOpenId().equals(accountLoginRespDTO.getOpenId())) {
                    respDTO.setSelf(1);
                } else {
                    respDTO.setSelf(0);
                }
                respList.add(respDTO);
            }
        }
        context.setResult(respList);
        return context;
    }

    public Context<MessageAddReqDTO, Void> addMessage(MessageAddReqDTO reqDTO){
        Context<MessageAddReqDTO, Void> context = new Context<>();
        Date now = new Date();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Message message = new Message();
        message.setActivityId(reqDTO.getActivityId());
        message.setOpenId(accountLoginRespDTO.getOpenId());
        message.setOpen(reqDTO.getOpen());
        message.setContent(reqDTO.getContent());
        message.setValidFlag(1);
        message.setCreateId(accountLoginRespDTO.getOpenId());
        message.setUpdateId(accountLoginRespDTO.getOpenId());
        message.setCreateTime(now);
        message.setUpdateTime(now);
        messageMapper.insert(message);
        return context;
    }

    public Context<BaseIdReqDTO, Void> deleteMessage(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, Void> context = new Context<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Message message = messageMapper.findById(reqDTO.getId());
        if (message != null){
            if (!message.getOpenId().equals(accountLoginRespDTO.getOpenId())){
                throw new GoActivityException(GoActivityCodeEnum.NO_JURISDICTION);
            }
            message.setUpdateId(accountLoginRespDTO.getOpenId());
            message.setUpdateTime(new Date());
            messageMapper.deleteById(message);
        }
        return context;
    }

    public Context<MessageUpdateReqDTO, Void> updateMessage(MessageUpdateReqDTO reqDTO){
        Context<MessageUpdateReqDTO, Void> context = new Context<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Message message = messageMapper.findById(reqDTO.getId());
        if (message != null){
            if (!message.getOpenId().equals(accountLoginRespDTO.getOpenId())){
                throw new GoActivityException(GoActivityCodeEnum.NO_JURISDICTION);
            }
            message.setOpen(reqDTO.getOpen());
            message.setContent(reqDTO.getContent());
            message.setUpdateId(accountLoginRespDTO.getOpenId());
            message.setUpdateTime(new Date());
            messageMapper.updateBySelective(message);
        }
        return context;
    }

}
