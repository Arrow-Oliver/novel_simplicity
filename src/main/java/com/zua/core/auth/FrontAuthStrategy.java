package com.zua.core.auth;

import com.zua.core.common.exception.BusinessException;
import com.zua.core.util.JwtUtils;
import com.zua.manager.cache.UserInfoCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Arrow
 * @date 2022/6/17 17:18
 */
@Component
@RequiredArgsConstructor
public class FrontAuthStrategy implements AuthStrategy {

    private final JwtUtils jwtUtils;

    private final UserInfoCacheManager userInfoCacheManager;


    @Override
    public void auth(String token, String requestUri) throws BusinessException {
        authSSO(jwtUtils,userInfoCacheManager,token);
    }
}
