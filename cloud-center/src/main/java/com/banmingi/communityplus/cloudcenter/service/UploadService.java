package com.banmingi.communityplus.cloudcenter.service;

import com.banmingi.communityplus.cloudcenter.provider.OSSProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@Service
@Slf4j
public class UploadService {
    /**
     * 上传图片.
     * @param image
     * @return
     */
    public String uploadImage(MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        try {
            //校验文件内容
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            if(bufferedImage == null) {
                log.error("文件内容不合法：{}",originalFilename);
                return null;
            }

            //上传至服务器
            String url = OSSProvider.upload(image);
            if(url == null) {
                log.error("服务器异常：{}",originalFilename);
            }
            log.info("上传成功");
            //返回url,进行回显
            return url;
        } catch (IOException e) {
            log.error("服务器内部异常：{}",originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
