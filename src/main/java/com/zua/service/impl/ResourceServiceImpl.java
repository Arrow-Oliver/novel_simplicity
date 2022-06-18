package com.zua.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.zua.core.common.constant.ErrorCodeEnum;
import com.zua.core.common.exception.BusinessException;
import com.zua.core.common.resp.RestResp;
import com.zua.core.constant.SystemConfigConsts;
import com.zua.dto.resp.ImgVerifyCodeRespDto;
import com.zua.manager.redis.VerifyCodeManager;
import com.zua.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.spi.DateFormatSymbolsProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.Filter;

/**
 * @author Arrow
 * @date 2022/6/17 15:53
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final VerifyCodeManager verifyCodeManager;

    @Value("${novel.file.upload.path}")
    private String fileUploadPath;

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
    @SneakyThrows
    @Override
    public RestResp<String> uploadImg(MultipartFile file) {
        //修改当前图片信息
        LocalDateTime now = LocalDateTime.now();
        String savePath =
                SystemConfigConsts.IMAGE_UPLOAD_DIRECTORY
                + now.format(DateTimeFormatter.ofPattern("yyyy")) + File.separator
                + now.format(DateTimeFormatter.ofPattern("MM")) + File.separator
                + now.format(DateTimeFormatter.ofPattern("dd"));
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String saveFileName = IdWorker.get32UUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        File saveFile = new File(fileUploadPath + savePath, saveFileName);
        if (!saveFile.getParentFile().exists()) {
            boolean isSuccess = saveFile.getParentFile().mkdirs();
            if (!isSuccess) {
                throw new BusinessException(ErrorCodeEnum.USER_UPLOAD_FILE_ERROR);
            }
        }
        //转存图片路径
        file.transferTo(saveFile);
        if (Objects.isNull(ImageIO.read(saveFile))) {
            // 上传的文件不是图片
            Files.delete(saveFile.toPath());
            throw new BusinessException(ErrorCodeEnum.USER_UPLOAD_FILE_TYPE_NOT_MATCH);
        }
        //返回地址信息
        return RestResp.ok(savePath + File.separator + saveFileName);


    }
}
