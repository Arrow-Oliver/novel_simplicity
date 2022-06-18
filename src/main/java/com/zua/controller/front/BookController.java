package com.zua.controller.front;

import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.resp.BookRankRespDto;
import com.zua.dto.resp.UserInfoRespDto;
import com.zua.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/18 17:48
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRouterConsts.API_FRONT_BOOK_URL_PREFIX)
public class BookController {

    private final BookService bookService;

    /**
     * 点击排行榜
     * @return
     */
    @GetMapping("visit_rank")
    public RestResp<List<BookRankRespDto>> visitRankBooks(){
        return bookService.visitRankBooks();
    }

    /**
     * 新书排行榜
     * @return
     */
    @GetMapping("newest_rank")
    public RestResp<List<BookRankRespDto>> newestRankBooks(){
        return bookService.newestRankBooks();
    }

    /**
     * 小说更新榜查询接口
     */
    @GetMapping("update_rank")
    public RestResp<List<BookRankRespDto>> updateRankBooks() {
        return bookService.listUpdateRankBooks();
    }
}
