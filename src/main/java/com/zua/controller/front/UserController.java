package com.zua.controller.front;

import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.req.UserLoginReqDto;
import com.zua.dto.req.UserRegisterReqDto;
import com.zua.dto.resp.UserLoginRespDto;
import com.zua.dto.resp.UserRegisterRespDto;
import com.zua.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("login")
    public RestResp<UserLoginRespDto> login(@Valid @RequestBody UserLoginReqDto userDto){
        return userService.login(userDto);
    }

    @PostMapping("register")
    public RestResp<UserRegisterRespDto> register(@Valid @RequestBody UserRegisterReqDto registerReqDto){
        return userService.register(registerReqDto);
    }


}
