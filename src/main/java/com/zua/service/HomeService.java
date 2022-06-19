package com.zua.service;

import com.zua.core.common.resp.RestResp;
import com.zua.dto.resp.HomeBookRespDto;
import com.zua.dto.resp.HomeFriendLinkRespDto;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/19 15:48
 */
public interface HomeService {
    /**
     * 查询书籍信息
     * @return
     */
    RestResp<List<HomeBookRespDto>> homeBooks();

    /**
     * 友联查询
     * @return
     */
    RestResp<List<HomeFriendLinkRespDto>> friendLinkList();

}
