package com.zua.dto.resp;

import lombok.Builder;
import lombok.Data;

/**
 * 用户信息 响应DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/22
 */
@Data
@Builder
public class UserInfoRespDto {

    /**
     * 昵称
     * */
    private String nickName;

    /**
     * 用户头像
     * */
    private String userPhoto;

    /**
     * 用户性别
     * */
    private Integer userSex;

    /**
     * 用户状态
     * */
    private Integer status;

}
