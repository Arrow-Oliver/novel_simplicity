package com.zua.service;

import com.zua.core.common.resp.RestResp;
import com.zua.dto.resp.NewsInfoRespDto;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/19 15:30
 */
public interface NewsService {
    /**
     * 最新新闻
     * @return
     */
    RestResp<List<NewsInfoRespDto>> latestList();

}
