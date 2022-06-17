package com.zua.core.auth;

import com.zua.core.common.constant.ErrorCodeEnum;
import com.zua.core.common.exception.BusinessException;
import com.zua.core.constant.SystemConfigConsts;
import com.zua.core.util.JwtUtils;
import com.zua.dao.entity.UserInfo;
import com.zua.dto.UserInfoDto;
import com.zua.manager.cache.UserInfoCacheManager;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author Arrow
 * @date 2022/6/17 17:11
 */
public interface AuthStrategy {
    /**
     * 请求用户认证
     *
     * @param token 登录 token
     * @param requestUri 请求的 URI
     * @throws BusinessException 认证失败则抛出业务异常
     */
    void auth(String token, String requestUri)  throws BusinessException;

    /**
     * 前台多系统单点登录统一账号认证（门户系统、作家系统以及后面会扩展的漫画系统和视频系统等）
     *
     * @param jwtUtils             jwt 工具
     * @param userInfoCacheManager 用户缓存管理对象
     * @param token                token 登录 token
     * @return 用户ID
     */
    default Long authSSO(JwtUtils jwtUtils, UserInfoCacheManager userInfoCacheManager, String token){
        //判断token是否过期
        if(!StringUtils.hasText(token)){
            throw new BusinessException(ErrorCodeEnum.USER_LOGIN_EXPIRED);
        }
        //解析token
        Long userId = jwtUtils.parseToken(token, SystemConfigConsts.NOVEL_FRONT_KEY);
        if(Objects.isNull(userId)){
            throw  new BusinessException(ErrorCodeEnum.USER_LOGIN_EXPIRED);
        }
        //查询用户数据
        UserInfoDto userInfo = userInfoCacheManager.getUserInfo(userId);
        if(Objects.isNull(userInfo)){
            throw new BusinessException(ErrorCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        //存入UserHolder
        UserHolder.setUserId(userId);
        //返回用户Id
        return userId;
    }

}
