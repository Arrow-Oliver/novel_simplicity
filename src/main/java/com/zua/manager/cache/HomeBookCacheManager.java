package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.constant.CacheConsts;
import com.zua.dao.entity.HomeBook;
import com.zua.dao.entity.HomeFriendLink;
import com.zua.dao.mapper.HomeBookMapper;
import com.zua.dao.mapper.HomeFriendLinkMapper;
import com.zua.dto.resp.HomeBookRespDto;
import com.zua.dto.resp.HomeFriendLinkRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arrow
 * @date 2022/6/19 16:01
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HomeBookCacheManager {

    private final HomeBookMapper homeBookMapper;

    private final HomeFriendLinkMapper homeFriendLinkMapper;

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.HOME_BOOK_CACHE_NAME)
    public List<HomeBookRespDto> homeBooks() {
        return homeBookMapper.findHomeBooks();
    }

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.HOME_FRIEND_LINK_CACHE_NAME)
    public List<HomeFriendLinkRespDto> friendLinkList() {
        LambdaQueryWrapper<HomeFriendLink> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(HomeFriendLink::getSort);

        return homeFriendLinkMapper.selectList(queryWrapper).stream().map(v ->{
            HomeFriendLinkRespDto homeFriendLinkRespDto = new HomeFriendLinkRespDto();
            BeanUtils.copyProperties(v,homeFriendLinkRespDto);
            return homeFriendLinkRespDto;
        }).collect(Collectors.toList());
    }
}
