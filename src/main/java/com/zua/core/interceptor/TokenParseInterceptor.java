package com.zua.core.interceptor;

import com.zua.core.auth.UserHolder;
import com.zua.core.constant.SystemConfigConsts;
import com.zua.core.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Arrow
 * @date 2022/6/21 16:45
 */
@Component
@RequiredArgsConstructor
public class TokenParseInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //解析token
        String token = request.getHeader(SystemConfigConsts.HTTP_AUTH_HEADER_NAME);
        if(StringUtils.hasText(token)){
            //设置当前用户信息
            Long userId = jwtUtils.parseToken(token, SystemConfigConsts.NOVEL_FRONT_KEY);
            UserHolder.setUserId(userId);
        }
        //放行
        return HandlerInterceptor.super.preHandle(request,response,handler);

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //清除线程用户信息
        UserHolder.clear();
        HandlerInterceptor.super.postHandle(request,response,handler,modelAndView);
    }
}
