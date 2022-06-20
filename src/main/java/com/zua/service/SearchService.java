package com.zua.service;

import com.zua.core.common.resp.PageRespDto;
import com.zua.core.common.resp.RestResp;
import com.zua.dto.req.BookSearchReqDto;
import com.zua.dto.resp.BookInfoRespDto;

/**
 * @author Arrow
 * @date 2022/6/20 17:11
 */
public interface SearchService {
    /**
     * 分页查询
     * @param condition
     * @return
     */
    RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto condition);
}
