package com.zua.controller.front;

import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.resp.NewsInfoRespDto;
import com.zua.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/19 15:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRouterConsts.API_FRONT_NEWS_URL_PREFIX)
public class NewsController {

    private final NewsService newsService;

    /**
     * 最新新闻查询
     * @return
     */
    @GetMapping("latest_list")
    public RestResp<List<NewsInfoRespDto>> latestList(){
        return newsService.latestList();
    }

    /**
     * 新闻信息查询接口
     */
    @GetMapping("{id}")
    public RestResp<NewsInfoRespDto> getNews(@PathVariable Long id) {
        return newsService.getNews(id);
    }
}
