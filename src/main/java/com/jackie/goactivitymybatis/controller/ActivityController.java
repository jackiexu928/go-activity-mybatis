package com.jackie.goactivitymybatis.controller;

import com.jackie.goactivitymybatis.domain.query.ActivityListQuery;
import com.jackie.goactivitymybatis.domain.request.ActivityAddReqDTO;
import com.jackie.goactivitymybatis.domain.request.BaseIdReqDTO;
import com.jackie.goactivitymybatis.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-25
 */
@RestController
@RequestMapping("activity")
public class ActivityController extends BaseController {
    @Autowired
    private ActivityService activityService;

    @PostMapping("addActivity")
    @ResponseBody
    public String addActivity(ActivityAddReqDTO reqDTO){
        return toJSON(activityService.addActivity(reqDTO));
    }

    @GetMapping("getActivityRecordList")
    @ResponseBody
    public String getActivityRecordList(ActivityListQuery query){
        return toJSON(activityService.getRecordList(query));
    }

    @GetMapping("getDetail")
    @ResponseBody
    public String getActivityDetail(BaseIdReqDTO reqDTO){
        return toJSON(activityService.getActivityDetail(reqDTO));
    }

    @GetMapping("getJoinAccount")
    @ResponseBody
    public String getJoinAccount(BaseIdReqDTO reqDTO){
        return toJSON(activityService.getJoinAccount(reqDTO));
    }

    @PostMapping("deleteActivity")
    @ResponseBody
    public String deleteActivity(BaseIdReqDTO reqDTO){
        return toJSON(activityService.deleteActivity(reqDTO));
    }
}
