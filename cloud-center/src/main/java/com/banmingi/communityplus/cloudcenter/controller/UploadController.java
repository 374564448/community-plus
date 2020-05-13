package com.banmingi.communityplus.cloudcenter.controller;

import com.banmingi.communityplus.cloudcenter.dto.FileInfoDTO;
import com.banmingi.communityplus.cloudcenter.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @auther 半命i 2020/5/13
 * @description 文件上传.
 */
@RestController
@RequestMapping("upload")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadController {

    private final UploadService uploadService;

    /**
     * 上传图片.
     * @param image
     * @return
     */
    @PostMapping("/image")
    public ResponseEntity<FileInfoDTO> uploadImage(MultipartFile image) {
        String url = this.uploadService.uploadImage(image);
        if(StringUtils.isBlank(url)) {
            //图片上传失败 400
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(FileInfoDTO.builder().path(url).build());
    }

}
