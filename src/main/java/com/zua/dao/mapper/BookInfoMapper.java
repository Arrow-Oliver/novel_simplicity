package com.zua.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zua.dao.entity.BookInfo;
import com.zua.dto.req.BookSearchReqDto;
import com.zua.dto.resp.BookInfoRespDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 条件查询
     * @param page plus分页对象
     * @param condition 条件
     * @return
     */
    List<BookInfo> searchBooks(@Param("page") Page<BookInfoRespDto> page,
                               @Param("condition") BookSearchReqDto condition);
}
