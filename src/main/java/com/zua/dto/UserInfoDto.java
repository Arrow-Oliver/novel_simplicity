package com.zua.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息 DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/18
 */
@Data
@Builder
public class UserInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer status;

}
