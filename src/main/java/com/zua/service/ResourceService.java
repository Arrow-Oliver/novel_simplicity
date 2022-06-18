package com.zua.service;

import com.zua.core.common.resp.RestResp;
import com.zua.dto.resp.ImgVerifyCodeRespDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Arrow
 * @date 2022/6/17 15:53
 */
public interface ResourceService {
    /**
     * 生成验证码
     * @return
     */
    RestResp<ImgVerifyCodeRespDto> imgVerifyCode() throws IOException;

    /**
     * 上传图片
     * @param file 图片
     * @return
     */
    RestResp<String> uploadImg(MultipartFile file);
}
