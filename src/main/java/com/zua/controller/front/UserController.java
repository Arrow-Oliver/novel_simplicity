package com.zua.controller.front;

import com.zua.core.auth.UserHolder;
import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.req.UserCommentReqDto;
import com.zua.dto.req.UserInfoUptReqDto;
import com.zua.dto.req.UserLoginReqDto;
import com.zua.dto.req.UserRegisterReqDto;
import com.zua.dto.resp.UserInfoRespDto;
import com.zua.dto.resp.UserLoginRespDto;
import com.zua.dto.resp.UserRegisterRespDto;
import com.zua.service.BookService;
import com.zua.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Arrow
 * @date 2022/6/15 17:14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRouterConsts.API_FRONT_USER_URL_PREFIX)
public class UserController {

    private final UserService userService;

    private final BookService bookService;

    /**
     * 登录接口
     */
    @PostMapping("login")
    public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto userDto){
        return userService.login(userDto);
    }

    /**
     * 注册接口
     */
    @PostMapping("register")
    public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto registerReqDto){
        return userService.register(registerReqDto);
    }

    /**
     * 获取用户信息接口
     */
    @GetMapping
    public RestResp<UserInfoRespDto> getUserInfo(){
        return userService.getUserInfo();
    }

    /**
     * 修改用户信息
     */
    @PutMapping
    public RestResp<Void> updateUserInfo(@Valid @RequestBody UserInfoUptReqDto dto){
        return userService.updateUserInfo(dto);
    }

    /**
     * 添加评论
     */
    @PostMapping("comment")
    public RestResp<Void> comment(@Valid @RequestBody UserCommentReqDto dto) {
        dto.setUserId(UserHolder.getUserId());
        return bookService.saveComment(dto);
    }

    /**
     * 删除评论
     */
    @DeleteMapping("comment/{id}")
    public RestResp<Void> deleteComment(@PathVariable Long id) {
        return bookService.deleteComment(UserHolder.getUserId(), id);
    }

    /**
     * 修改评论
     */
    @PutMapping("comment/{id}")
    public RestResp<Void> updateComment(@PathVariable Long id, String content) {
        return bookService.updateComment(UserHolder.getUserId(), id, content);
    }
}
