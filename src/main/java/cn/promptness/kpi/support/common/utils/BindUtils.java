package cn.promptness.kpi.support.common.utils;

import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.support.exception.BizExceptionEnum;

/**
 * @author : Lynn
 * @date : 2019-05-04 00:00
 */
public class BindUtils {

    private static final ThreadLocal<LoginUserRsp> THREAD_LOCAL = new ThreadLocal<>();

    public static void bind(LoginUserRsp loginUserRsp) {
        AssertUtil.notNull(loginUserRsp, BizExceptionEnum.USER_NOT_LOGIN);
        THREAD_LOCAL.set(loginUserRsp);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    public static LoginUserRsp getUser() {
        LoginUserRsp loginUserRsp = THREAD_LOCAL.get();
        AssertUtil.notNull(loginUserRsp, BizExceptionEnum.USER_NOT_LOGIN);
        return loginUserRsp;
    }

}
