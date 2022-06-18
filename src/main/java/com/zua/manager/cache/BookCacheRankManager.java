package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.constant.CacheConsts;
import com.zua.core.constant.DatabaseConsts;
import com.zua.dao.entity.BookInfo;
import com.zua.dao.entity.UserInfo;
import com.zua.dao.mapper.BookInfoMapper;
import com.zua.dao.mapper.UserInfoMapper;
import com.zua.dto.resp.BookRankRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arrow
 * @date 2022/6/18 18:10
 */
@Component
@RequiredArgsConstructor
public class BookCacheRankManager {

    private final BookInfoMapper bookInfoMapper;

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.BOOK_VISIT_RANK_CACHE_NAME)
    public List<BookRankRespDto> visitRankBooks() {
        //点击量降序并且wordCount大于0
        LambdaQueryWrapper<BookInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(BookInfo::getVisitCount);
        return rankBooks(queryWrapper);

    }

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.BOOK_NEWEST_RANK_CACHE_NAME)
    public List<BookRankRespDto> newestRankBooks() {
        LambdaQueryWrapper<BookInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(BookInfo::getCreateTime);
        return rankBooks(queryWrapper);

    }

    private List<BookRankRespDto> rankBooks(LambdaQueryWrapper<BookInfo> queryWrapper) {
        queryWrapper.gt(BookInfo::getWordCount, 0).last(DatabaseConsts.SqlEnum.LIMIT_30.getSql());
        return bookInfoMapper.selectList(queryWrapper).stream().map(v -> {
            BookRankRespDto respDto = new BookRankRespDto();
            BeanUtils.copyProperties(v, respDto);
            return respDto;
        }).collect(Collectors.toList());
    }

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.BOOK_UPDATE_RANK_CACHE_NAME)
    public List<BookRankRespDto> listUpdateRankBooks() {
        LambdaQueryWrapper<BookInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(BookInfo::getLastChapterUpdateTime);
        return rankBooks(queryWrapper);
    }
}
