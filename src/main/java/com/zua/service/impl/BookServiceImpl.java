package com.zua.service.impl;

import com.zua.core.common.resp.RestResp;
import com.zua.dto.resp.BookInfoRespDto;
import com.zua.dto.resp.BookRankRespDto;
import com.zua.manager.cache.BookInfoCacheManager;
import com.zua.manager.cache.BookRankCacheManager;
import com.zua.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/18 17:51
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRankCacheManager bookCacheRankManager;

    private final BookInfoCacheManager bookInfoCacheManager;

    @Override
    public RestResp<List<BookRankRespDto>> visitRankBooks() {
        return RestResp.ok(bookCacheRankManager.visitRankBooks());
    }

    @Override
    public RestResp<List<BookRankRespDto>> newestRankBooks() {
        return RestResp.ok(bookCacheRankManager.newestRankBooks());
    }

    @Override
    public RestResp<List<BookRankRespDto>> listUpdateRankBooks() {
        return RestResp.ok(bookCacheRankManager.listUpdateRankBooks());
    }

    @Override
    public RestResp<BookInfoRespDto> getBookInfoById(String bookId) {
        return RestResp.ok(bookInfoCacheManager.getBookInfoById(bookId));
    }
}
