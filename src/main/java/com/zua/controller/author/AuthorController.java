package com.zua.controller.author;

import com.zua.core.auth.UserHolder;
import com.zua.core.common.req.PageReqDto;
import com.zua.core.common.resp.PageRespDto;
import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.req.AuthorRegisterReqDto;
import com.zua.dto.req.BookAddReqDto;
import com.zua.dto.req.ChapterAddReqDto;
import com.zua.dto.resp.BookChapterRespDto;
import com.zua.dto.resp.BookInfoRespDto;
import com.zua.service.AuthorService;
import com.zua.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Arrow
 * @date 2022/6/21 16:29
 */
@RestController
@RequestMapping(ApiRouterConsts.API_AUTHOR_URL_PREFIX)
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private final BookService bookService;

    /**
     * 查询作家状态接口
     */
    @GetMapping("status")
    public RestResp<Integer> getStatus() {
        return authorService.getStatus(UserHolder.getUserId());
    }

    /**
     * 作家注册接口
     */
    @PostMapping("register")
    public RestResp<Void> register(@Valid @RequestBody AuthorRegisterReqDto dto) {
        dto.setUserId(UserHolder.getUserId());
        return authorService.register(dto);
    }

    /**
     * 小说发布接口
     */
    @PostMapping("book")
    public RestResp<Void> publishBook(@Valid @RequestBody BookAddReqDto dto) {
        return bookService.saveBook(dto);
    }

    /**
     * 小说发布列表查询接口
     */
    @GetMapping("books")
    public RestResp<PageRespDto<BookInfoRespDto>> listBooks(PageReqDto dto) {
        return bookService.listAuthorBooks(dto);
    }

    /**
     * 小说章节发布接口
     */
    @PostMapping("book/chapter/{bookId}")
    public RestResp<Void> publishBookChapter(@PathVariable("bookId") Long bookId, @Valid @RequestBody ChapterAddReqDto dto) {
        dto.setBookId(bookId);
        return bookService.saveBookChapter(dto);
    }

    /**
     * 小说章节发布列表查询接口
     */
    @GetMapping("book/chapters/{bookId}")
    public RestResp<PageRespDto<BookChapterRespDto>> listBookChapters(@PathVariable("bookId") Long bookId, PageReqDto dto) {
        return bookService.listBookChapters(bookId, dto);
    }

}