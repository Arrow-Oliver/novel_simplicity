package com.zua.service;

import com.zua.core.common.resp.RestResp;
import com.zua.dto.resp.BookChapterAboutRespDto;
import com.zua.dto.resp.BookCommentRespDto;
import com.zua.dto.resp.BookInfoRespDto;
import com.zua.dto.resp.BookRankRespDto;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/18 17:49
 */
public interface BookService {

    /**
     * 查询点击排行榜
     * @return
     */
    RestResp<List<BookRankRespDto>> visitRankBooks();

    /**
     * 查询更新排行榜
     * @return
     */
    RestResp<List<BookRankRespDto>> newestRankBooks();

    /**
     * 更新排行榜
     * @return
     */
    RestResp<List<BookRankRespDto>> listUpdateRankBooks();

    /**
     * 书籍详情
     * @param bookId
     * @return
     */
    RestResp<BookInfoRespDto> getBookInfoById(String bookId);

    /**
     * 小说章节详情
     * @param bookId
     * @return
     */
    RestResp<BookChapterAboutRespDto> lastChapterAbout(Long bookId);

    /**
     * 小说推荐列表
     * @param bookId
     * @return
     */
    RestResp<List<BookInfoRespDto>> recList(Long bookId) throws NoSuchAlgorithmException;

    /**
     * 查询最新评论
     * @param bookId
     * @return
     */
    RestResp<BookCommentRespDto> listNewestComments(Long bookId);
}
