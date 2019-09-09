package cn.promptness.kpi.support.exception;

import com.google.gson.Gson;
import cn.promptness.kpi.domain.vo.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常解析器
 *
 * @author linhuid
 * @date 2019/7/1 14:15
 * @since v1.0.0
 */
public class BusinessExceptionResolver implements HandlerExceptionResolver, Ordered {

    private static transient Logger logger = LoggerFactory.getLogger(BusinessExceptionResolver.class);

    /**
     * The default name of the exception attribute: "message".
     */
    private static final String DEFAULT_EXCEPTION_ATTRIBUTE = "message";
    private static final String DEFAULT_EXCEPTION_PAGE = "error";

    @SuppressWarnings("unchecked")
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ModelAndView mv = new ModelAndView();
        if (isJson(handler)) {
            HttpResult errorResult = HttpResult.getErrorHttpResult().setContent(ex.getMessage());
            if (ex instanceof BusinessException) {
                errorResult.setCode(((BusinessException) ex).getCode());
            }
            try {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.getWriter().print(new Gson().toJson(errorResult));
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            return mv;
        } else {
            mv.addObject(DEFAULT_EXCEPTION_ATTRIBUTE, ex.getMessage());
            mv.setViewName(DEFAULT_EXCEPTION_PAGE);
            return mv;
        }
    }

    private boolean isJson(Object handler) {
        // handler处理器  org.springframework.web.method.HandlerMethod
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
            RestController restController = handlerMethod.getBeanType().getAnnotation(RestController.class);
            return responseBody != null || restController != null;
        }
        //org.springframework.web.servlet.resource.ResourceHttpRequestHandler 静态资源处理器
        return false;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
