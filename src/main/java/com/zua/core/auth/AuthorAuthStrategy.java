package com.zua.core.auth;

import com.zua.core.common.constant.ErrorCodeEnum;
import com.zua.core.common.exception.BusinessException;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.core.util.JwtUtils;
import com.zua.dto.AuthorInfoDto;
import com.zua.manager.cache.AuthorInfoCacheManager;
import com.zua.manager.cache.UserInfoCacheManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Arrow
 * @date 2022/6/21 17:32
 */
@Component
@RequiredArgsConstructor
public class AuthorAuthStrategy implements AuthStrategy{

    private final JwtUtils jwtUtils;

    private final UserInfoCacheManager userInfoCacheManager;

    private final AuthorInfoCacheManager authorInfoCacheManager;

    /**
     * 不需要进行作家权限认证的 URI
     * */
    private static final List<String> EXCLUDE_URI = Arrays.asList(
            ApiRouterConsts.API_AUTHOR_URL_PREFIX + "/register",
            ApiRouterConsts.API_AUTHOR_URL_PREFIX +"/status"
    );

    @Override
    public void auth(String token, String requestUri) throws BusinessException {
        //单点登录认证
        Long userId = authSSO(jwtUtils, userInfoCacheManager, token);

        //排除不需要拦截请求
        if(EXCLUDE_URI.contains(requestUri)){
            return;
        }
        //认证作者
        AuthorInfoDto authorInfo = authorInfoCacheManager.getAuthorInfo(userId);
        if(Objects.isNull(authorInfo)){
            // 作家账号不存在，无权访问作家专区
            throw new BusinessException(ErrorCodeEnum.USER_UN_AUTH);
        }
        //设置作者id
        UserHolder.setAuthorId(authorInfo.getId());
    }
}
