package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.constant.CacheConsts;
import com.zua.dao.entity.BookInfo;
import com.zua.dao.entity.HomeBook;
import com.zua.dao.entity.HomeFriendLink;
import com.zua.dao.mapper.BookInfoMapper;
import com.zua.dao.mapper.HomeBookMapper;
import com.zua.dao.mapper.HomeFriendLinkMapper;
import com.zua.dto.resp.HomeBookRespDto;
import com.zua.dto.resp.HomeFriendLinkRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Arrow
 * @date 2022/6/19 16:01
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HomeBookCacheManager {

    private final HomeBookMapper homeBookMapper;

    private final HomeFriendLinkMapper homeFriendLinkMapper;

    private final BookInfoMapper bookInfoMapper;

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.HOME_BOOK_CACHE_NAME)
    public List<HomeBookRespDto> homeBooks() {
        //升序查询bookID
        LambdaQueryWrapper<HomeBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(HomeBook::getSort);
        List<HomeBook> homeBooks = homeBookMapper.selectList(queryWrapper);
        List<Long> bookIds = homeBooks.stream().map(HomeBook::getBookId).collect(Collectors.toList());
        //查询首页书籍
        if (!CollectionUtils.isEmpty(bookIds)) {
            LambdaQueryWrapper<BookInfo> bookInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            bookInfoLambdaQueryWrapper.in(BookInfo::getId, bookIds);
            List<BookInfo> bookInfos = bookInfoMapper.selectList(bookInfoLambdaQueryWrapper);
            //封装数据
            if (!CollectionUtils.isEmpty(bookInfos)){
                Map<Long, BookInfo> bookInfoMap = bookInfos.stream().
                        collect(Collectors.toMap(BookInfo::getId, Function.identity()));
                return homeBooks.stream().map(v ->{
                    HomeBookRespDto bookRespDto = new HomeBookRespDto();
                    bookRespDto.setType(v.getType());
                    bookRespDto.setBookId(v.getBookId());
                    BookInfo bookInfo = bookInfoMap.get(v.getBookId());
                    bookRespDto.setAuthorName(bookInfo.getAuthorName());
                    bookRespDto.setBookDesc(bookInfo.getBookDesc());
                    bookRespDto.setPicUrl(bookInfo.getPicUrl());
                    bookRespDto.setBookName(bookInfo.getBookName());
                    return bookRespDto;
                }).collect(Collectors.toList());

            }
        }
        return Collections.emptyList();


//        return homeBookMapper.findHomeBooks();
    }

    @Cacheable(cacheManager = CacheConsts.REDIS_CACHE_MANAGER,
            value = CacheConsts.HOME_FRIEND_LINK_CACHE_NAME)
    public List<HomeFriendLinkRespDto> friendLinkList() {
        LambdaQueryWrapper<HomeFriendLink> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(HomeFriendLink::getSort);

        return homeFriendLinkMapper.selectList(queryWrapper).stream().map(v -> {
            HomeFriendLinkRespDto homeFriendLinkRespDto = new HomeFriendLinkRespDto();
            BeanUtils.copyProperties(v, homeFriendLinkRespDto);
            return homeFriendLinkRespDto;
        }).collect(Collectors.toList());
    }
}
