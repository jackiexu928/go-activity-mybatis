package com.jackie.goactivitymybatis.controller;

import com.jackie.goactivitymybatis.domain.query.BaseVoidQuery;
import com.jackie.goactivitymybatis.domain.request.BaseIdReqDTO;
import com.jackie.goactivitymybatis.domain.request.TemplateAddReqDTO;
import com.jackie.goactivitymybatis.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-24
 */
@RestController
@RequestMapping("template")
public class TemplateController extends BaseController {
    @Autowired
    private TemplateService templateService;

    @GetMapping("getList")
    @ResponseBody
    public String getList(BaseVoidQuery query){
        return toJSON(templateService.getTemplateList(query));
    }

    @PostMapping("addTemplate")
    @ResponseBody
    public String addTemplate(TemplateAddReqDTO reqDTO){
        return toJSON(templateService.addTemplate(reqDTO));
    }

    @PostMapping("deleteTemplate")
    @ResponseBody
    public String deleteTemplate(BaseIdReqDTO reqDTO){
        return toJSON(templateService.deleteTemplate(reqDTO));
    }

    @GetMapping("getTemplate")
    @ResponseBody
    public String getTemplateById(BaseIdReqDTO reqDTO){
        return toJSON(templateService.getTemplateById(reqDTO));
    }
}
