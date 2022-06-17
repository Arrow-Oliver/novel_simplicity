package com.zua.controller.front;

import com.zua.core.common.resp.RestResp;
import com.zua.core.common.util.ImgVerifyCodeUtils;
import com.zua.core.constant.ApiRouterConsts;
import com.zua.dto.resp.ImgVerifyCodeRespDto;
import com.zua.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("img_verify_code")
    public RestResp<ImgVerifyCodeRespDto> imgVerifyCode() throws IOException {
        return resourceService.imgVerifyCode();
    }
}
