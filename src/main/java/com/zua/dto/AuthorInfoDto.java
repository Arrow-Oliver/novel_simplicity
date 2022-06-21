package com.zua.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 作家信息 DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/18
 */
@Data
@Builder
public class AuthorInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String penName;

    private Integer status;

}