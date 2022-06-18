package com.zua.core.config;

import com.zua.core.constant.ApiRouterConsts;
import com.zua.core.interceptor.AuthInterceptor;
import com.zua.core.interceptor.FileInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Arrow
 * @date 2022/6/17 16:36
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    private final FileInterceptor fileInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //文件拦截器
        registry.addInterceptor(fileInterceptor)
                .addPathPatterns(ApiRouterConsts.API_FRONT_RESOURCE_URL_PREFIX + "/**");


        // 认证拦截器
        registry.addInterceptor(authInterceptor)
                //拦截路径
                .addPathPatterns(ApiRouterConsts.API_FRONT_USER_URL_PREFIX + "/**")
                //不拦截的路径
                .excludePathPatterns(ApiRouterConsts.API_FRONT_USER_URL_PREFIX + "/register",
                        ApiRouterConsts.API_FRONT_USER_URL_PREFIX + "/login");
    }
}
