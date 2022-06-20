package com.zua.controller.front;

import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dao.entity.BookInfo;
import com.zua.dto.req.BookAddVisitReqDto;
import com.zua.dto.resp.*;
import com.zua.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
     */
    @GetMapping("visit_rank")
    public RestResp<List<BookRankRespDto>> visitRankBooks(){
        return bookService.visitRankBooks();
    }

    /**
     * 新书排行榜
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
     * 推荐查询
     */
    @GetMapping("rec_list")
    public RestResp<List<BookInfoRespDto>> recList(Long bookId) throws NoSuchAlgorithmException {
        return bookService.recList(bookId);
    }

    /**
     * 增加小说点击量接口
     */
    @PostMapping("visit")
    public RestResp<Void> addVisitCount(@RequestBody BookAddVisitReqDto bookAddVisitReqDto) {
        return bookService.addVisitCount(bookAddVisitReqDto);
    }

    /**
     * 小说详情查询
     */
    @GetMapping("{id}")
    public RestResp<BookInfoRespDto> getBookInfoById(@PathVariable("id") String bookId){
        return bookService.getBookInfoById(bookId);
    }


    /**
     * 最新章节查询
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

    /**
     * 小说内容相关信息查询接口
     */
    @GetMapping("content/{chapterId}")
    public RestResp<BookContentAboutRespDto> getBookContentAbout(@PathVariable("chapterId") Long chapterId) {
        return bookService.getBookContentAbout(chapterId);
    }

    /**
     * 获取上一章节ID接口
     */
    @GetMapping("pre_chapter_id/{chapterId}")
    public RestResp<Long> getPreChapterId(@PathVariable("chapterId") Long chapterId) {
        return bookService.getPreChapterId(chapterId);
    }

    /**
     * 获取下一章节ID接口
     */
    @GetMapping("next_chapter_id/{chapterId}")
    public RestResp<Long> getNextChapterId(@PathVariable("chapterId") Long chapterId) {
        return bookService.getNextChapterId(chapterId);
    }

    /**
     * 小说章节列表查询接口
     */
    @GetMapping("chapter/list")
    public RestResp<List<BookChapterRespDto>> listChapters(Long bookId) {
        return bookService.listChapters(bookId);
    }

    /**
     * 小说分类列表查询接口
     */
    @GetMapping("category/list")
    public RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection) {
        return bookService.listCategory(workDirection);
    }
}
