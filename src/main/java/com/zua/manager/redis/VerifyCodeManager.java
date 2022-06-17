package com.zua.manager.redis;

import com.zua.core.common.util.ImgVerifyCodeUtils;
import com.zua.core.constant.CacheConsts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Arrow
 * @date 2022/6/17 15:58
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class VerifyCodeManager {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     * @param sessionId
     * @return
     */
    public String generateCode(String sessionId) throws IOException {
        //生成验证码
        String code = ImgVerifyCodeUtils.getRandomVerifyCode(4);
            //生成图片
            String img = ImgVerifyCodeUtils.genVerifyCodeImg(code);
            //存入redis
            stringRedisTemplate.opsForValue().set(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessionId,
                    code,5,TimeUnit.MINUTES);
            return img;
    }

    /**
     * 校验验证码
     * @param sessionId
     * @return
     */
    public boolean verifyCodeOk(String sessionId,String code){
       return Objects.equals(code,
               stringRedisTemplate.opsForValue().get(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessionId));
    }

    /**
     * 删除验证码
     * @param sessionId
     */
    public void delVerifyCode(String sessionId){
        stringRedisTemplate.delete(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessionId);
    }
}
