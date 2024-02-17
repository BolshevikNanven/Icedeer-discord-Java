package org.discord.common.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.discord.common.annotation.RequireAuthentication;
import org.discord.common.exception.AuthenticationException;
import org.discord.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpAuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //跨越请求
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Headers", "*");
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireAuthentication requiresAuthentication = handlerMethod.getMethodAnnotation(RequireAuthentication.class);

        //跨越请求,放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        //未使用注解,放行
        if (requiresAuthentication == null) {
            return true;
        }

        String token = request.getHeader("Authentication");

        if (token == null) {
            throw new AuthenticationException("认证失败");
        }
        try {
            JWTUtil.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            throw new AuthenticationException("认证信息过期");
        } catch (Exception e) {
            throw new AuthenticationException("认证失败");
        }

    }
}
