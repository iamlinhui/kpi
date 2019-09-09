package cn.promptness.kpi.support.interceptor;

import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.support.common.Constants;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.support.common.utils.BindUtils;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Lynn
 * @date : 2019-05-03 23:54
 */
public class UserBondingInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof DefaultServletHttpRequestHandler) {
            return true;
        }

        LoginUserRsp loginUserRsp = (LoginUserRsp) request.getSession().getAttribute(Constants.USER_SESSION_KEY);
        AssertUtil.notNull(loginUserRsp, BizExceptionEnum.USER_NOT_LOGIN);

        BindUtils.bind(loginUserRsp);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        BindUtils.remove();
    }

}
