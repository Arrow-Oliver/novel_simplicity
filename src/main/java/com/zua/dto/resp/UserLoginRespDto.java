package com.zua.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 用户登录 响应DTO
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@Data
@Builder
public class UserLoginRespDto {

    private Long uid;

    private String nickName;

    private String token;
}
