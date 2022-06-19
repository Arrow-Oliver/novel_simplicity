package com.zua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.common.resp.RestResp;
import com.zua.dao.entity.BookChapter;
import com.zua.dao.mapper.BookChapterMapper;
import com.zua.dto.resp.BookChapterAboutRespDto;
import com.zua.dto.resp.BookChapterRespDto;
import com.zua.dto.resp.BookInfoRespDto;
import com.zua.dto.resp.BookRankRespDto;
import com.zua.manager.cache.BookChapterCacheManager;
import com.zua.manager.cache.BookContentCacheManager;
import com.zua.manager.cache.BookInfoCacheManager;
import com.zua.manager.cache.BookRankCacheManager;
import com.zua.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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

    private final BookChapterMapper bookChapterMapper;

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
}
