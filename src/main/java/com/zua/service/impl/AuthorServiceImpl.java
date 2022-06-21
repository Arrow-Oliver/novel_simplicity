package com.zua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zua.core.common.resp.RestResp;
import com.zua.dao.entity.AuthorInfo;
import com.zua.dao.mapper.AuthorInfoMapper;
import com.zua.dto.AuthorInfoDto;
import com.zua.dto.req.AuthorRegisterReqDto;
import com.zua.manager.cache.AuthorInfoCacheManager;
import com.zua.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Arrow
 * @date 2022/6/21 16:30
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorInfoCacheManager authorInfoCacheManager;

    private final AuthorInfoMapper authorInfoMapper;

    @Override
    public RestResp<Integer> getStatus(Long userId) {
        AuthorInfoDto authorInfo = authorInfoCacheManager.getAuthorInfo(userId);
        return Objects.isNull(authorInfo) ? RestResp.ok(null) : RestResp.ok(authorInfo.getStatus());
    }

    @Override
    public RestResp<Void> register(AuthorRegisterReqDto dto) {
        //判断当前作家是否存在
        AuthorInfoDto authorInfo = authorInfoCacheManager.getAuthorInfo(dto.getUserId());
        if(!Objects.isNull(authorInfo)){
            return RestResp.ok();
        }
        //注册信息
        AuthorInfo authorRegister = new AuthorInfo();
        BeanUtils.copyProperties(dto,authorRegister);
        authorRegister.setInviteCode("0");
        authorRegister.setCreateTime(LocalDateTime.now());
        authorRegister.setUpdateTime(LocalDateTime.now());
        authorInfoMapper.insert(authorRegister);
        return RestResp.ok();
    }
}
