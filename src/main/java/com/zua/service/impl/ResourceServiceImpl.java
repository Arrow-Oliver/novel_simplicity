package com.zua.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.zua.core.common.resp.RestResp;
import com.zua.dto.resp.ImgVerifyCodeRespDto;
import com.zua.manager.redis.VerifyCodeManager;
import com.zua.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Arrow
 * @date 2022/6/17 15:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final VerifyCodeManager verifyCodeManager;

    @Override
    public RestResp<ImgVerifyCodeRespDto> imgVerifyCode() throws IOException {
        //生成会话ID
        String uuid = IdWorker.get32UUID();

        //生成图片
        String img = verifyCodeManager.generateCode(uuid);
        //封装对象
        return RestResp.ok(ImgVerifyCodeRespDto.builder()
                .sessionId(uuid)
                .img(img)
                .build());
    }
}
