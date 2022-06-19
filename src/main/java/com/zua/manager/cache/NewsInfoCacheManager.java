package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.CacheConsts;
import com.zua.core.constant.DatabaseConsts;
import com.zua.dao.entity.BookInfo;
import com.zua.dao.entity.NewsInfo;
import com.zua.dao.mapper.BookInfoMapper;
import com.zua.dao.mapper.NewsInfoMapper;
import com.zua.dto.resp.BookRankRespDto;
import com.zua.dto.resp.NewsInfoRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arrow
 * @date 2022/6/18 18:10
 */
@Component
@RequiredArgsConstructor
public class NewsInfoCacheManager {

    private final NewsInfoMapper newsInfoMapper;

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.LATEST_NEWS_CACHE_NAME)
    public List<NewsInfoRespDto> latestList() {
        // 查询最新信息
        LambdaQueryWrapper<NewsInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(NewsInfo::getCreateTime)
                .last(DatabaseConsts.SqlEnum.LIMIT_2.getSql());
        return newsInfoMapper.selectList(queryWrapper).stream().map(v ->{
            NewsInfoRespDto newsInfoRespDto = NewsInfoRespDto.builder().build();
            BeanUtils.copyProperties(v,newsInfoRespDto);
            return newsInfoRespDto;
        }).collect(Collectors.toList());
    }
}
