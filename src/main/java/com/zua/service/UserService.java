package com.zua.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.core.common.resp.RestResp;
import com.zua.dao.entity.UserInfo;
import com.zua.dto.req.UserInfoUptReqDto;
import com.zua.dto.req.UserLoginReqDto;
import com.zua.dto.req.UserRegisterReqDto;
import com.zua.dto.resp.UserInfoRespDto;
import com.zua.dto.resp.UserLoginRespDto;
import com.zua.dto.resp.UserRegisterRespDto;

/**
 * 会员模块 服务类
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
public interface UserService extends IService<UserInfo> {
    /**
     * 用户登录
     * @param userDto
     * @return
     */
    RestResp<UserLoginRespDto> login(UserLoginReqDto userDto);

    /**
     * 用户注册
     * @param registerReqDto
     * @return
     */
    RestResp<UserRegisterRespDto> register(UserRegisterReqDto registerReqDto);

    /**
     * 获取用户信息
     * @return
     */
    RestResp<UserInfoRespDto> getUserInfo();

    /**
     * 修改用户信息
     * @param dto
     * @return
     */
    RestResp<Void> updateUserInfo(UserInfoUptReqDto dto);
}
