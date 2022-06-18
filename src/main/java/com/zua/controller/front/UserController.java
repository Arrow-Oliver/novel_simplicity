package com.zua.controller.front;

import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.req.UserInfoUptReqDto;
import com.zua.dto.req.UserLoginReqDto;
import com.zua.dto.req.UserRegisterReqDto;
import com.zua.dto.resp.UserInfoRespDto;
import com.zua.dto.resp.UserLoginRespDto;
import com.zua.dto.resp.UserRegisterRespDto;
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

    /**
     * 登录接口
     * @param userDto
     * @return
     */
    @PostMapping("login")
    public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto userDto){
        return userService.login(userDto);
    }

    /**
     * 注册接口
     * @param registerReqDto
     * @return
     */
    @PostMapping("register")
    public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto registerReqDto){
        return userService.register(registerReqDto);
    }

    /**
     * 获取用户信息接口
     * @return
     */
    @GetMapping
    public RestResp<UserInfoRespDto> getUserInfo(){
        return userService.getUserInfo();
    }

    @PutMapping
    public RestResp<Void> updateUserInfo(@Valid @RequestBody UserInfoUptReqDto dto){
        return userService.updateUserInfo(dto);
    }

}
