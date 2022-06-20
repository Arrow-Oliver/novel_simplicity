package com.zua.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zua.core.common.resp.PageRespDto;
import com.zua.core.common.resp.RestResp;
import com.zua.dao.entity.BookInfo;
import com.zua.dao.mapper.BookInfoMapper;
import com.zua.dto.req.BookSearchReqDto;
import com.zua.dto.resp.BookInfoRespDto;
import com.zua.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arrow
 * @date 2022/6/20 17:11
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DbSearchServiceImpl implements SearchService {

    private final BookInfoMapper bookInfoMapper;

    @Override
    public RestResp<PageRespDto<BookInfoRespDto>> searchBooks(BookSearchReqDto condition) {
        //分页对象
        Page<BookInfoRespDto> page = new Page<>();
        page.setCurrent(condition.getPageNum());
        page.setSize(condition.getPageSize());
        //条件查询数据
        List<BookInfo> bookInfos = bookInfoMapper.searchBooks(page,condition);
        //封装数据
        return RestResp.ok(PageRespDto.of(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                bookInfos.stream().map(v ->{
                    BookInfoRespDto respDto = BookInfoRespDto.builder().build();
                    BeanUtils.copyProperties(v,respDto);
                    return respDto;
                }).collect(Collectors.toList())));


    }
}
