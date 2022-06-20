package com.zua.manager.cache;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.constant.CacheConsts;
import com.zua.dao.entity.BookCategory;
import com.zua.dao.entity.HomeFriendLink;
import com.zua.dao.mapper.BookCategoryMapper;
import com.zua.dao.mapper.HomeFriendLinkMapper;
import com.zua.dto.resp.BookCategoryRespDto;
import com.zua.dto.resp.HomeFriendLinkRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 友情链接 缓存管理类
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
@Component
@RequiredArgsConstructor
public class BookCategoryCacheManager {

    private final BookCategoryMapper bookCategoryMapper;

    @Cacheable(cacheManager = CacheConsts.CAFFEINE_CACHE_MANAGER,
            value = CacheConsts.BOOK_CATEGORY_LIST_CACHE_NAME)
    public List<BookCategoryRespDto> listCategory(Integer workDirection) {
        LambdaQueryWrapper<BookCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BookCategory::getWorkDirection,workDirection)
                .orderByDesc(BookCategory::getSort);
        return bookCategoryMapper.selectList(queryWrapper).stream().map(v ->
                BookCategoryRespDto.builder()
                        .id(v.getId())
                        .name(v.getName())
                        .build()).collect(Collectors.toList());
    }
}
