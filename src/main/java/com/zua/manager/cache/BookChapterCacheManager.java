package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.constant.CacheConsts;
import com.zua.core.constant.DatabaseConsts;
import com.zua.dao.entity.BookChapter;
import com.zua.dao.mapper.BookChapterMapper;
import com.zua.dto.resp.BookChapterRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 小说章节 缓存管理类
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
@Component
@RequiredArgsConstructor
public class BookChapterCacheManager {

    private final BookChapterMapper bookChapterMapper;

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
        value = CacheConsts.BOOK_CHAPTER_CACHE_NAME)
    public BookChapterRespDto getLastChapterInfo(Long chapterId) {
        BookChapter lastChapter = bookChapterMapper.selectById(chapterId);
        BookChapterRespDto bookChapterRespDto = BookChapterRespDto.builder().build();
        BeanUtils.copyProperties(lastChapter,bookChapterRespDto);
        bookChapterRespDto.setChapterWordCount(lastChapter.getWordCount());
        bookChapterRespDto.setChapterUpdateTime(lastChapter.getUpdateTime());
        return bookChapterRespDto;
    }

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.BOOK_CHAPTER_CACHE_NAME)
    public List<BookChapterRespDto> getChapterByBookId(Long bookId) {

        LambdaQueryWrapper<BookChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookChapter::getBookId,bookId)
                .orderByAsc(BookChapter::getChapterNum);

        return bookChapterMapper.selectList(queryWrapper).stream().map(v ->{
            BookChapterRespDto bookChapterRespDto = BookChapterRespDto.builder().build();
            BeanUtils.copyProperties(v,bookChapterRespDto);
            return bookChapterRespDto;
        }).collect(Collectors.toList());
    }
}
