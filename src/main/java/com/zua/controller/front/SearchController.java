package com.zua.controller.front;

import com.zua.core.common.resp.PageRespDto;
import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.req.BookSearchReqDto;
import com.zua.dto.resp.BookInfoRespDto;
import com.zua.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台门户-搜索模块 API 控制器
 *
 * @author xiongxiaoyang
 * @date 2022/5/27
 */
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_SEARCH_URL_PREFIX)
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /**
     * 小说搜索接口
     */
    @GetMapping("books")
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto condition) {
        return searchService.searchBooks(condition);
    }

}
