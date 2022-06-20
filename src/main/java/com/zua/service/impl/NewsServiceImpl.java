package com.zua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.DatabaseConsts;
import com.zua.dao.entity.NewsContent;
import com.zua.dao.entity.NewsInfo;
import com.zua.dao.mapper.NewsContentMapper;
import com.zua.dao.mapper.NewsInfoMapper;
import com.zua.dto.resp.NewsInfoRespDto;
import com.zua.manager.cache.NewsInfoCacheManager;
import com.zua.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/19 15:30
 */
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsInfoCacheManager newsInfoCacheManager;

    private final NewsInfoMapper newsInfoMapper;

    private final NewsContentMapper newsContentMapper;

    @Override
    public RestResp<List<NewsInfoRespDto>> latestList() {
        return RestResp.ok(newsInfoCacheManager.latestList());

    }

    @Override
    public RestResp<NewsInfoRespDto> getNews(Long id) {
        //查询信息
        NewsInfo newsInfo = newsInfoMapper.selectById(id);
        LambdaQueryWrapper<NewsContent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NewsContent::getNewsId, id)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        NewsContent newsContent = newsContentMapper.selectOne(queryWrapper);

        //封装数据
        return RestResp.ok(NewsInfoRespDto.builder()
                .categoryId(newsInfo.getCategoryId())
                .categoryName(newsInfo.getCategoryName())
                .id(id)
                .sourceName(newsInfo.getSourceName())
                .title(newsInfo.getTitle())
                .updateTime(newsInfo.getUpdateTime())
                .content(newsContent.getContent())
                .build());

    }
}
