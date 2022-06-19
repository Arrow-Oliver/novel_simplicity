package com.zua.service.impl;

import com.zua.core.common.resp.RestResp;
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

    @Override
    public RestResp<List<NewsInfoRespDto>> latestList() {
        return RestResp.ok(newsInfoCacheManager.latestList());

    }
}
