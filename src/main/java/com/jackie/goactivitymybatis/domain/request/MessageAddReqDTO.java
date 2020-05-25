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
 * @date 2020-05-20
 */
@Data
public class MessageAddReqDTO extends BaseReqDTO {
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 是否公开，0-仅自己可见，1-公开
     * 查询时，会优先展示自己的留言
     */
    private Integer open;
    private String content;

    @Override
    public void validation() {
        if (StringUtils.isEmpty(activityId)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
    }
}
