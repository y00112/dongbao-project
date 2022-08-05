package com.wukong.common.interceptor;

import com.wukong.common.annotations.TokenCheck;
import com.wukong.common.utils.JwtUtil;
import com.wukong.common.utils.TokenException;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created By WuKong on 2022/8/2 20:50
 * 拦截器
 **/
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("拦截器进入");
        //从请求头里获取token
        String token = request.getHeader("token");

        //token.equals("")
        if (token == null) {
            throw new TokenException("token 为空");
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(TokenCheck.class)) {
            TokenCheck annotation = method.getAnnotation(TokenCheck.class);
            //如果为 true
            if (annotation.required()) {
                //校验token
                try {
                    JwtUtil.parseToken(token);
                    return true;
                }catch (Exception e) {
                   throw new TokenException("token 异常");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
