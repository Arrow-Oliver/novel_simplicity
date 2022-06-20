package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zua.core.constant.CacheConsts;
import com.zua.dao.entity.HomeFriendLink;
import com.zua.dao.mapper.HomeFriendLinkMapper;
import com.zua.dto.resp.HomeFriendLinkRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 友情链接 缓存管理类
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
@Component
@RequiredArgsConstructor
public class FriendLinkCacheManager {

    private final HomeFriendLinkMapper homeFriendLinkMapper;

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.HOME_FRIEND_LINK_CACHE_NAME)
    public List<HomeFriendLinkRespDto> friendLinkList() {
        LambdaQueryWrapper<HomeFriendLink> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(HomeFriendLink::getSort);

        return homeFriendLinkMapper.selectList(queryWrapper).stream().map(v -> {
            HomeFriendLinkRespDto homeFriendLinkRespDto = new HomeFriendLinkRespDto();
            BeanUtils.copyProperties(v, homeFriendLinkRespDto);
            return homeFriendLinkRespDto;
        }).collect(Collectors.toList());
    }
}
