package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.zua.core.constant.CacheConsts;
import com.zua.core.constant.DatabaseConsts;
import com.zua.dao.entity.BookContent;
import com.zua.dao.mapper.BookContentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 小说内容 缓存管理类
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
@Component
@RequiredArgsConstructor
public class BookContentCacheManager {

    private final BookContentMapper bookContentMapper;

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
        value = CacheConsts.BOOK_CONTENT_CACHE_NAME)
    public String getLastChapterContent(Long lastChapterId) {
        LambdaQueryWrapper<BookContent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookContent::getChapterId,lastChapterId)
                .last(DatabaseConsts.SqlEnum.LIMIT_1.getSql());
        BookContent bookContent = bookContentMapper.selectOne(queryWrapper);
        return bookContent.getContent();

    }
}
