package com.zua.service;

import com.zua.core.common.resp.RestResp;
import com.zua.dto.req.BookAddVisitReqDto;
import com.zua.dto.req.UserCommentReqDto;
import com.zua.dto.resp.*;

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

    /**
     * 小说内容相关信息查询接口
     * @param chapterId
     * @return
     */
    RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId);

    /**
     * 获取上一章Id
     * @param chapterId
     * @return
     */
    RestResp<Long> getPreChapterId(Long chapterId);

    /**
     * 获取下一章Id
     * @param chapterId
     * @return
     */
    RestResp<Long> getNextChapterId(Long chapterId);

    /**
     * 查询章节列表
     * @param bookId
     * @return
     */
    RestResp<List<BookChapterRespDto>> listChapters(Long bookId);

    /**
     * 点击量增加
     * @param bookId
     * @return
     */
    RestResp<Void> addVisitCount(BookAddVisitReqDto bookId);

    /**
     * 分类查询
     * @param workDirection
     * @return
     */
    RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection);

    /**
     * 发表评论
     * @param dto
     * @return
     */
    RestResp<Void> saveComment(UserCommentReqDto dto);

    /**
     * 删除评论
     * @param userId 用户id
     * @param id 评论id
     * @return
     */
    RestResp<Void> deleteComment(Long userId, Long id);

    /**
     * 修改评论
     * @param userId 用户Id
     * @param id 评论id'
     * @param content 评论内容
     * @return
     */
    RestResp<Void> updateComment(Long userId, Long id, String content);

}
