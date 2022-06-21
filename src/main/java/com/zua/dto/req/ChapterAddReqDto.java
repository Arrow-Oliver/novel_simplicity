package com.zua.dto.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 章节发布 请求DTO
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
@Data
public class ChapterAddReqDto {

    /**
     * 小说ID
     */
    private Long bookId;

    /**
     * 章节名
     */
    @NotBlank
    private String chapterName;

    /**
     * 章节内容
     */
    @NotBlank
    @Length(min = 50)
    private String chapterContent;

    /**
     * 是否收费;1-收费 0-免费
     */
    @NotNull
    private Integer isVip;

}
