package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.constant.CacheConsts;
import com.zua.core.constant.DatabaseConsts;
import com.zua.dao.entity.BookChapter;
import com.zua.dao.entity.BookInfo;
import com.zua.dao.mapper.BookChapterMapper;
import com.zua.dao.mapper.BookInfoMapper;
import com.zua.dto.resp.BookInfoRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author Arrow
 * @date 2022/6/19 17:18
 */
@Component
@RequiredArgsConstructor
public class BookInfoCacheManager {

    private final BookInfoMapper bookInfoMapper;

    private final BookChapterMapper bookChapterMapper;

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
    value = CacheConsts.BOOK_INFO_CACHE_NAME)
    public BookInfoRespDto getBookInfoById(String bookId) {
        //查询书籍基本信息
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        //查询书籍章节信息
        LambdaQueryWrapper<BookChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(BookChapter::getBookId,bookId)
                .orderByAsc(BookChapter::getChapterNum)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        BookChapter firstChapter = bookChapterMapper.selectOne(queryWrapper);
        BookInfoRespDto bookInfoRespDto = new BookInfoRespDto();
        BeanUtils.copyProperties(bookInfo,bookInfoRespDto);
        bookInfoRespDto.setFirstChapterId(firstChapter.getId());
        //封装信息
        return bookInfoRespDto;
    }
}
