package com.zua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.common.constant.ErrorCodeEnum;
import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.DatabaseConsts;
import com.zua.dao.entity.BookChapter;
import com.zua.dao.entity.BookComment;
import com.zua.dao.entity.UserInfo;
import com.zua.dao.mapper.BookChapterMapper;
import com.zua.dao.mapper.BookCommentMapper;
import com.zua.dao.mapper.BookInfoMapper;
import com.zua.dto.req.BookAddVisitReqDto;
import com.zua.dto.req.UserCommentReqDto;
import com.zua.dto.resp.*;
import com.zua.manager.dao.UserDaoManager;
import com.zua.manager.cache.*;
import com.zua.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Arrow
 * @date 2022/6/18 17:51
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static final int REC_BOOK_COUNT = 4;

    private final BookRankCacheManager bookCacheRankManager;

    private final BookInfoCacheManager bookInfoCacheManager;

    private final BookChapterCacheManager bookChapterCacheManager;

    private final BookContentCacheManager bookContentCacheManager;

    private final BookCategoryCacheManager bookCategoryCacheManager;

    private final BookChapterMapper bookChapterMapper;

    private final BookCommentMapper bookCommentMapper;

    private final BookInfoMapper bookInfoMapper;

    private final UserDaoManager userDaoManager;

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

    @Override
    public RestResp<BookChapterAboutRespDto> lastChapterAbout(Long bookId) {
        //小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfoById(bookId.toString());
        //最新章节信息
        Long lastChapterId = bookInfo.getLastChapterId();
        BookChapterRespDto bookChapterInfo = bookChapterCacheManager.getLastChapterInfo(lastChapterId);
        //查询章节内容
        String content = bookContentCacheManager.getLastChapterContent(lastChapterId);
        //查询章节数
        LambdaQueryWrapper<BookChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookChapter::getBookId, bookId);
        Long chapterCount = bookChapterMapper.selectCount(queryWrapper);
        //封装数据
        return RestResp.ok(BookChapterAboutRespDto.builder()
                .chapterInfo(bookChapterInfo)
                .contentSummary(content.substring(0, 30))
                .chapterTotal(chapterCount)
                .build());
    }

    @Override
    public RestResp<List<BookInfoRespDto>> recList(Long bookId) throws NoSuchAlgorithmException {
        //获取当前分类ID
        Long categoryId = bookInfoCacheManager.getBookInfoById(bookId.toString()).getCategoryId();
        //查询当前分类的所有bookID
        List<Long> lastUpdateIds = bookInfoCacheManager.getBookCategories(categoryId);
        //随机选出四个返回给前端显示
        ArrayList<BookInfoRespDto> respDtoList = new ArrayList<>();
        ArrayList<Integer> recIdIndexList = new ArrayList<>();
        int count = 0;
        SecureRandom rand = SecureRandom.getInstanceStrong();
        while (count < REC_BOOK_COUNT) {
            int recIdIndex = rand.nextInt(lastUpdateIds.size());
            //随机数不同的话封装，相同的话继续生成随即数
            if (!recIdIndexList.contains(recIdIndex)) {
                recIdIndexList.add(recIdIndex);
                bookId = lastUpdateIds.get(recIdIndex);
                BookInfoRespDto bookInfoRespDto = bookInfoCacheManager.getBookInfoById(bookId.toString());
                respDtoList.add(bookInfoRespDto);
                count++;
            }
        }
        return RestResp.ok(respDtoList);
    }

    @Override
    public RestResp<BookCommentRespDto> listNewestComments(Long bookId) {
        //查询评论总数
        LambdaQueryWrapper<BookComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookComment::getBookId, bookId);
        Long commentTotal = bookCommentMapper.selectCount(queryWrapper);
        BookCommentRespDto bookCommentRespDto = BookCommentRespDto.builder().build();
        bookCommentRespDto.setCommentTotal(commentTotal);
        //如果没有数据
        if (commentTotal == 0) {
            bookCommentRespDto.setComments(Collections.emptyList());
            return RestResp.ok(bookCommentRespDto);
        }
        //查询前五条评论
        queryWrapper.orderByDesc(BookComment::getCreateTime);
        List<BookComment> bookComments = bookCommentMapper.selectList(queryWrapper);
        //查询用户信息
        List<Long> userIds = bookComments.stream().map(BookComment::getUserId).collect(Collectors.toList());
        List<UserInfo> userInfos = userDaoManager.getUserInfo(userIds);
        Map<Long, UserInfo> userInfoMap = userInfos.stream()
                .collect(Collectors.toMap(UserInfo::getId, Function.identity()));
        //封装信息
        List<BookCommentRespDto.CommentInfo> commentInfos = bookComments.stream().map(v ->
                BookCommentRespDto.CommentInfo.builder()
                        .id(v.getId())
                        .commentContent(v.getCommentContent())
                        .commentTime(v.getCreateTime())
                        .commentUser(userInfoMap.get(v.getUserId()).getUsername())
                        .commentUserId(v.getUserId())
                        .commentUserPhoto(userInfoMap.get(v.getUserId()).getUserPhoto())
                        .build()).collect(Collectors.toList());

        bookCommentRespDto.setComments(commentInfos);

        return RestResp.ok(bookCommentRespDto);
    }

    @Override
    public RestResp<BookContentAboutRespDto> getBookContentAbout(Long chapterId) {
        //查询章节信息
        BookChapterRespDto chapterInfo = bookChapterCacheManager.getLastChapterInfo(chapterId);
        //查询小说信息
        BookInfoRespDto bookInfo = bookInfoCacheManager.getBookInfoById(chapterInfo.getBookId().toString());
        //查询章节内容
        String chapterContent = bookContentCacheManager.getLastChapterContent(chapterId);

        //封装数据
        return RestResp.ok(BookContentAboutRespDto.builder()
                .bookContent(chapterContent)
                .bookInfo(bookInfo)
                .chapterInfo(chapterInfo)
                .build()
        );
    }

    @Override
    public RestResp<Long> getPreChapterId(Long chapterId) {
        //获取当前章节信息
        BookChapterRespDto chapterInfo = bookChapterCacheManager.getLastChapterInfo(chapterId);
        //寻找上一章ChapterId
        LambdaQueryWrapper<BookChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookChapter::getBookId, chapterInfo.getBookId())
                .lt(BookChapter::getChapterNum, chapterInfo.getChapterNum())
                .orderByDesc(BookChapter::getChapterNum)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        return RestResp.ok(
                Optional.ofNullable(bookChapterMapper.selectOne(queryWrapper))
                        .map(BookChapter::getId)
                        .orElse(null));
    }

    @Override
    public RestResp<Long> getNextChapterId(Long chapterId) {
        //获取当前章节信息
        BookChapterRespDto chapterInfo = bookChapterCacheManager.getLastChapterInfo(chapterId);
        //寻找下一章ChapterId
        LambdaQueryWrapper<BookChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookChapter::getBookId, chapterInfo.getBookId())
                .gt(BookChapter::getChapterNum, chapterInfo.getChapterNum())
                .orderByAsc(BookChapter::getChapterNum)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        return RestResp.ok(
                Optional.ofNullable(bookChapterMapper.selectOne(queryWrapper))
                        .map(BookChapter::getId)
                        .orElse(null));
    }

    @Override
    public RestResp<List<BookChapterRespDto>> listChapters(Long bookId) {
        return RestResp.ok(bookChapterCacheManager.getChapterByBookId(bookId));
    }

    @Override
    public RestResp<Void> addVisitCount(BookAddVisitReqDto bookId) {
        bookInfoMapper.addVisitCount(bookId.getBookId());
        return RestResp.ok();
    }

    @Override
    public RestResp<List<BookCategoryRespDto>> listCategory(Integer workDirection) {
        return RestResp.ok(bookCategoryCacheManager.listCategory(workDirection));
    }

    @Override
    public RestResp<Void> saveComment(UserCommentReqDto dto) {
        // 查找当亲用户是否评论过
        LambdaQueryWrapper<BookComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookComment::getBookId,dto.getBookId())
                .eq(BookComment::getUserId,dto.getUserId());
        if(bookCommentMapper.selectCount(queryWrapper) > 0){
            return RestResp.fail(ErrorCodeEnum.USER_COMMENTED);
        }
        //新增评论信息
        BookComment bookComment = new BookComment();
        bookComment.setBookId(dto.getBookId());
        bookComment.setUserId(dto.getUserId());
        bookComment.setCommentContent(dto.getCommentContent());
        bookComment.setCreateTime(LocalDateTime.now());
        bookComment.setUpdateTime(LocalDateTime.now());
        bookCommentMapper.insert(bookComment);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> deleteComment(Long userId, Long id) {
        LambdaQueryWrapper<BookComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookComment::getUserId,userId)
                .eq(BookComment::getId,id);
        bookCommentMapper.delete(queryWrapper);
        return RestResp.ok();
    }

    @Override
    public RestResp<Void> updateComment(Long userId, Long id, String content) {
        LambdaQueryWrapper<BookComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookComment::getUserId,userId)
                .eq(BookComment::getId,id);
        BookComment bookComment = new BookComment();
        bookComment.setUpdateTime(LocalDateTime.now());
        bookComment.setCommentContent(content);
        bookCommentMapper.update(bookComment,queryWrapper);
        return RestResp.ok();
    }
}
