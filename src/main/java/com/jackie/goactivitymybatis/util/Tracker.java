package com.jackie.goactivitymybatis.util;

import com.jackie.goactivitymybatis.domain.response.AccountLoginRespDTO;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-04-29
 */
public class Tracker {
    String openId;
    AccountLoginRespDTO accountLoginRespDTO;

    public Tracker() {
    }

    public Tracker(String openId, AccountLoginRespDTO accountLoginRespDTO) {
        this.openId=openId;
        this.accountLoginRespDTO=accountLoginRespDTO;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public AccountLoginRespDTO getAccountLoginRespDTO() {
        return accountLoginRespDTO;
    }

    public void setAccountLoginRespDTO(AccountLoginRespDTO accountLoginRespDTO) {
        this.accountLoginRespDTO = accountLoginRespDTO;
    }
}
