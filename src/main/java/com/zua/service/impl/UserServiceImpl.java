package com.zua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.core.common.constant.ErrorCodeEnum;
import com.zua.core.common.exception.BusinessException;
import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.SystemConfigConsts;
import com.zua.core.util.JwtUtils;
import com.zua.dao.entity.UserInfo;
import com.zua.dao.mapper.UserInfoMapper;
import com.zua.dto.req.UserLoginReqDto;
import com.zua.dto.req.UserRegisterReqDto;
import com.zua.dto.resp.UserLoginRespDto;
import com.zua.dto.resp.UserRegisterRespDto;
import com.zua.manager.redis.VerifyCodeManager;
import com.zua.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Arrow
 * @date 2022/6/15 17:11
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserService{

    private final JwtUtils jwtUtils;

    private final VerifyCodeManager verifyCodeManager;

    @Override
    public RestResp<UserLoginRespDto> login(UserLoginReqDto userDto) {
        //判断用户是否存在
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername,userDto.getUsername());
        UserInfo userInfo = getOne(queryWrapper);
        if(Objects.isNull(userInfo)){
            throw new BusinessException(ErrorCodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        //判断密码是否正确
       if(!Objects.equals(userInfo.getPassword(),
               DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes(StandardCharsets.UTF_8)))){
           throw new BusinessException(ErrorCodeEnum.USER_PASSWORD_ERROR);
       }
        //返回respDTO
        return RestResp.ok(UserLoginRespDto.builder()
                .token(jwtUtils.generateToken(userInfo.getId(), SystemConfigConsts.NOVEL_FRONT_KEY))
                .uid(userInfo.getId())
                .nickName(userInfo.getNickName())
                .build());

    }

    @Override
    public RestResp<UserRegisterRespDto> register(UserRegisterReqDto registerReqDto) {
        //校验验证码
        @NotBlank @Length(min = 32, max = 32) String sessionId = registerReqDto.getSessionId();

        if (!verifyCodeManager.verifyCodeOk(sessionId,registerReqDto.getVelCode())) {
            throw new BusinessException(ErrorCodeEnum.USER_VERIFY_CODE_ERROR);
        }
        //用户名是否存在
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername,registerReqDto.getUsername());
        if(count(queryWrapper) > 0){
            throw new BusinessException(ErrorCodeEnum.USER_NAME_EXIST);
        }
        //存入数据库
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(registerReqDto.getUsername());
        userInfo.setPassword(DigestUtils.md5DigestAsHex(registerReqDto.getPassword().getBytes(StandardCharsets.UTF_8)));
        userInfo.setNickName(registerReqDto.getUsername());
        userInfo.setSalt("0");
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        save(userInfo);
        // 删除验证码
        verifyCodeManager.delVerifyCode(sessionId);
        // 生成JWT 并返回

        Long id = userInfo.getId();
        return RestResp.ok(UserRegisterRespDto.builder()
                .uid(id)
                .token(jwtUtils.generateToken(id,SystemConfigConsts.NOVEL_FRONT_KEY))
                .build());
    }
}
