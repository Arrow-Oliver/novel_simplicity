package com.zua.service;

import com.zua.core.common.resp.RestResp;
import com.zua.dto.req.AuthorRegisterReqDto;

/**
 * @author Arrow
 * @date 2022/6/21 16:30
 */
public interface AuthorService {
    /**
     * 作者状态
     * @param userId
     * @return
     */
    RestResp<Integer> getStatus(Long userId);

    /**
     * 注册作家
     * @param dto
     * @return
     */
    RestResp<Void> register(AuthorRegisterReqDto dto);
}
