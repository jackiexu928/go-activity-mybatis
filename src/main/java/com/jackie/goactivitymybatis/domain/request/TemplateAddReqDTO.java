package com.jackie.goactivitymybatis.domain.request;

import com.jackie.goactivitymybatis.common.enums.GoActivityCodeEnum;
import com.jackie.goactivitymybatis.exception.GoActivityException;
import domain.request.BaseReqDTO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-12
 */
@Data
public class TemplateAddReqDTO extends BaseReqDTO {
    private Integer type;
    private String name;
    private String theme;
    private Integer limitNum;
    private Integer cost;
    private String remark;

    @Override
    public void validation() {
        if (type == null){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
    }
}
