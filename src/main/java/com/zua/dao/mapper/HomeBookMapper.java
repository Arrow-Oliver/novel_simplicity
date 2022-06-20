package com.zua.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zua.dao.entity.HomeBook;
import com.zua.dto.resp.HomeBookRespDto;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/19 16:01
 */
public interface HomeBookMapper extends BaseMapper<HomeBook> {
    /**
     * 升序查找书籍
     * @return
     */
    List<HomeBookRespDto> findHomeBooks();

}
