package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.constant.CacheConsts;
import com.zua.core.constant.DatabaseConsts;
import com.zua.dao.entity.AuthorInfo;
import com.zua.dao.mapper.AuthorInfoMapper;
import com.zua.dto.AuthorInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Arrow
 * @date 2022/6/21 16:35
 */
@Component
@RequiredArgsConstructor
public class AuthorInfoCacheManager {

    private final AuthorInfoMapper authorInfoMapper;

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.AUTHOR_INFO_CACHE_NAME,
            unless = "#result == null")
    public AuthorInfoDto getAuthorInfo(Long userId) {
        //查询当前用户是否是作者
        LambdaQueryWrapper<AuthorInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AuthorInfo::getUserId,userId)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        AuthorInfo authorInfo = authorInfoMapper.selectOne(queryWrapper);
        if(Objects.isNull(authorInfo)){
            return null;
        }
        return AuthorInfoDto.builder()
                .id(authorInfo.getId())
                .penName(authorInfo.getPenName())
                .status(authorInfo.getStatus())
                .build();
    }
}
