package com.zua.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zua.dao.entity.BookInfo;

/**
 * @author Arrow
 * @date 2022/6/18 18:03
 */
public interface BookInfoMapper extends BaseMapper<BookInfo> {
    /**
     * 浏览量增加
     * @param bookId
     */
    void addVisitCount(Long bookId);

}
