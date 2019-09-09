package cn.promptness.kpi.support.interceptor;

import cn.promptness.kpi.domain.vo.hr.LoginUserRsp;
import cn.promptness.kpi.service.rpc.UserService;
import cn.promptness.kpi.support.common.Constants;
import cn.promptness.kpi.support.common.utils.AssertUtil;
import cn.promptness.kpi.support.exception.BizExceptionEnum;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Lynn
 * @date : 2019-05-03 23:54
 */
public class SessionBondingInterceptor extends HandlerInterceptorAdapter {


    @Resource
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof DefaultServletHttpRequestHandler) {
            return true;
        }

        HttpSession session = request.getSession();

        LoginUserRsp loginUserRsp = (LoginUserRsp) session.getAttribute(Constants.USER_SESSION_KEY);

        if (null != loginUserRsp) {
            return true;
        }

        // 请求中获取token
        String token = getCookieToken(request);
        AssertUtil.isFalse(StringUtils.isEmpty(token), BizExceptionEnum.USER_NOT_LOGIN);

        loginUserRsp = userService.getUserByToken(token);
        AssertUtil.notNull(loginUserRsp, BizExceptionEnum.USER_NOT_LOGIN);

        session.setAttribute(Constants.USER_SESSION_KEY, loginUserRsp);
        return true;
    }

    private String getCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        List<Cookie> cookieList = Lists.newArrayList();
        if (cookies != null && cookies.length > 0) {
            cookieList.addAll(Arrays.asList(cookies));
        }
        return cookieList.stream().filter(x -> Constants.TOKEN_COOKIE_KEY.equals(x.getName())).findFirst().orElse(Constants.DEFAULT_COOKIE).getValue();
    }


}
