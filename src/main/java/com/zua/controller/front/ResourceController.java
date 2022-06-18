package com.zua.controller.front;

import com.zua.core.common.resp.RestResp;
import com.zua.core.common.util.ImgVerifyCodeUtils;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.resp.ImgVerifyCodeRespDto;
import com.zua.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Arrow
 * @date 2022/6/17 15:48
 */
@RestController
@RequestMapping(ApiRouterConsts.API_FRONT_RESOURCE_URL_PREFIX)
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    /**
     * 生成验证码
     * @return
     * @throws IOException
     */
    @GetMapping("img_verify_code")
    public RestResp<ImgVerifyCodeRespDto> imgVerifyCode() throws IOException {
        return resourceService.imgVerifyCode();
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("image")
    public RestResp<String> uploadImg(@RequestParam("file") MultipartFile file){
        return resourceService.uploadImg(file);
    }
}
