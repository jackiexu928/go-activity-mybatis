package com.jackie.goactivitymybatis.common.enums;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-09
 */
public enum GoActivityCodeEnum {
    PARAM_ISNULL("PARAM_ISNULL","参数为空"),
    USER_NOT_LOGIN("USER_NOT_LOGIN", "用户未登录"),
    NO_RECORD("NO_RECORD", "无此记录"),
    ILLGAL_ARGUMENT("ILLGAL_ARGUMENT","非法参数"),
    ACTIVITY_IS_BEGIN("ACTIVITY_IS_BEGIN","活动已开始，不可删除"),
    NO_JURISDICTION("NO_JURISDICTION","无此权限");

    private String errMsg;

    private String errCode;

    private GoActivityCodeEnum(String errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private GoActivityCodeEnum(String errCode){
        this.errCode = errCode;
    }

    public static GoActivityCodeEnum getTbcpErrorCodeEnum(String code) {
        for(GoActivityCodeEnum x: GoActivityCodeEnum.values()) {
            if(x.getErrCode().equals(code)) {
                return x;
            }
        }
        return null;
    }

    public String getErrCode(){
        return this.errCode;
    }

    public String getErrMsg(){
        return this.errMsg;
    }
}
