package com.zua.manager.cache;

import com.zua.core.constant.CacheConsts;
import com.zua.dao.entity.UserInfo;
import com.zua.dao.mapper.UserInfoMapper;
import com.zua.dto.UserInfoDto;
import com.zua.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author Arrow
 * @date 2022/6/17 17:14
 */
@Component
@RequiredArgsConstructor
public class UserInfoCacheManager {

    private final UserInfoMapper userInfoMapper;

    /**
     * 查询数据并且存入redis中
     * @param userId
     * @return
     */
    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
    value = CacheConsts.USER_INFO_CACHE_NAME)
    public UserInfoDto getUserInfo(Long userId) {
        //查询数据
        UserInfo userInfo = userInfoMapper.selectById(userId);
        //返回数据
        return  UserInfoDto.builder()
                .id(userInfo.getId())
                .status(userInfo.getStatus())
                .build();
    }
}
