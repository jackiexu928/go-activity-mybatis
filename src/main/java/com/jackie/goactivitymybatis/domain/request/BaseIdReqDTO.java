package com.jackie.goactivitymybatis.domain.request;

import com.jackie.goactivitymybatis.common.enums.GoActivityCodeEnum;
import com.jackie.goactivitymybatis.exception.GoActivityException;
import domain.request.BaseReqDTO;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-14
 */
@Data
public class BaseIdReqDTO extends BaseReqDTO {
    private Long id;

    @Override
    public void validation() {
        if (null == id){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
    }
}
