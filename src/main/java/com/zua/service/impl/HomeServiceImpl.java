package com.zua.service.impl;

import com.zua.core.common.resp.RestResp;
import com.zua.dto.resp.HomeBookRespDto;
import com.zua.dto.resp.HomeFriendLinkRespDto;
import com.zua.manager.cache.FriendLinkCacheManager;
import com.zua.manager.cache.HomeBookCacheManager;
import com.zua.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/19 15:48
 */
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final HomeBookCacheManager homeBookCacheManager;

    private final FriendLinkCacheManager friendLinkCacheManager;

    @Override
    public RestResp<List<HomeBookRespDto>> homeBooks() {
        return RestResp.ok(homeBookCacheManager.homeBooks());
    }

    @Override
    public RestResp<List<HomeFriendLinkRespDto>> friendLinkList() {
        return RestResp.ok(friendLinkCacheManager.friendLinkList());
    }
}
