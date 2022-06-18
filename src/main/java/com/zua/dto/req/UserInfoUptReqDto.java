package com.zua.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 用户信息更新 请求DTO
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@Data
public class UserInfoUptReqDto {

    private Long userId;

    @Length(min = 2,max = 10)
    private String nickName;

    private String userPhoto;

    @Min(value = 0)
    @Max(value = 1)
    private Integer userSex;

}
