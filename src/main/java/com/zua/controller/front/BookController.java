package com.zua.controller.front;

import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.resp.*;
import com.zua.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
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

    /**
     * 小说详情查询
     */
    @GetMapping("{id}")
    public RestResp<BookInfoRespDto> getBookInfoById(@PathVariable("id") String bookId){
        return bookService.getBookInfoById(bookId);
    }

    /**
     * 推荐查询
     * @param bookId
     * @return
     * @throws NoSuchAlgorithmException
     */
    @GetMapping("rec_list")
    public RestResp<List<BookInfoRespDto>> recList(Long bookId) throws NoSuchAlgorithmException {
        return bookService.recList(bookId);
    }

    /**
     * 最新章节查询
     * @param bookId
     * @return
     */
    @GetMapping("last_chapter/about")
    public RestResp<BookChapterAboutRespDto> lastChapterAbout(Long bookId){
        return bookService.lastChapterAbout(bookId);
    }

    /**
     * 小说最新评论查询接口
     */
    @GetMapping("comment/newest_list")
    public RestResp<BookCommentRespDto> listNewestComments(Long bookId) {
        return bookService.listNewestComments(bookId);
    }
}
