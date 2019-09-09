package cn.promptness.kpi.support.exception;

import lombok.Getter;

/**
 * @author : Lynn
 * @date : 2019-04-18 22:50
 */
@Getter
public enum BizExceptionEnum {


    /**
     *
     */
    USER_NOT_LOGIN           (100005, "用户未登录"),
    SERVER_ERROR             (999999, "服务器异常");

    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
        this.template = "error";
    }

    BizExceptionEnum(int code, String message, String template) {
        this.code = code;
        this.message = message;
        this.template = template;
    }

    private int code;

    private String message;

    private String template;

    public BusinessException pageException() {
        return new BusinessException(this);
    }


}
