package com.jackie.goactivitymybatis.controller;

import com.jackie.goactivitymybatis.domain.request.LoginReqDTO;
import com.jackie.goactivitymybatis.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-23
 */
@RestController
@RequestMapping("account")
public class AccountController extends BaseController {
    @Autowired
    private AccountService accountService;

    @PostMapping("login")
    @ResponseBody
    public String login(LoginReqDTO reqDTO){
        return toJSON(accountService.login(reqDTO));
    }
}
