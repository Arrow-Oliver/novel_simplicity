package com.zua.controller.front;

import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dao.entity.HomeBook;
import com.zua.dto.resp.HomeBookRespDto;
import com.zua.dto.resp.HomeFriendLinkRespDto;
import com.zua.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Arrow
 * @date 2022/6/19 15:48
 */
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_HOME_URL_PREFIX)
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("books")
    public RestResp<List<HomeBookRespDto>> homeBooks(){
        return homeService.homeBooks();
    }

    @GetMapping("friend_Link/list")
    public RestResp<List<HomeFriendLinkRespDto>> friendLinkList(){
        return homeService.friendLinkList();
    }
}
