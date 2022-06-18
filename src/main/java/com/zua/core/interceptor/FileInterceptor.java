package com.zua.core.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Arrow
 * @date 2022/6/18 17:23
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FileInterceptor implements HandlerInterceptor {

    @Value("${novel.file.upload.path}")
    private String fileUploadPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的 URI
        String requestUri = request.getRequestURI();
        log.debug("请求拦截：{}",requestUri);
        // 缓存10天
        response.setDateHeader("expires", System.currentTimeMillis() + 60 * 60 * 24 * 10 * 1000);
        try (OutputStream out = response.getOutputStream(); InputStream input = new FileInputStream(fileUploadPath + requestUri)) {
            byte[] b = new byte[4096];
            for (int n; (n = input.read(b)) != -1; ) {
                out.write(b, 0, n);
            }
        }
        return false;
    }
}
