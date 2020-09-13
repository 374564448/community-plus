package com.banmingi.communityplus.notificationcenter.controller;

import com.banmingi.communityplus.auth.annotations.CheckLogin;
import com.banmingi.communityplus.notificationcenter.dto.notification.NotificationDTO;
import com.banmingi.communityplus.notificationcenter.service.NotificationService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 半命i 2020/8/2
 * @description
 */
@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 获取通知列表
     * @param userId 用户id
     * @param pageNum 第几页
     * @param pageSize 页面大小
     * @return 通知列表
     */
    @GetMapping("/list/{userId}")
    @CheckLogin
    public ResponseEntity<PageInfo<NotificationDTO>> list(
            @PathVariable Integer userId,
            @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(required = false,defaultValue = "10") Integer pageSize) {
        PageInfo<NotificationDTO> notificationList = this.notificationService.list(userId,pageNum,pageSize);
        return ResponseEntity.ok(notificationList);
    }

    /**
     * 获取未读通知数
     * @param userId 用户id
     * @return 未读通知数
     */
    @GetMapping("/unReadCount/{userId}")
    @CheckLogin
    public ResponseEntity<Integer> unReadCount(@PathVariable Integer userId) {
        Integer unReadCount = this.notificationService.unReadCount(userId);
        return ResponseEntity.ok(unReadCount);
    }

    /**
     * 标记通知已读
     * @param id id
     * @return void
     */
    @PutMapping("/read/{id}")
    @CheckLogin
    public ResponseEntity<Void> read(@PathVariable Integer id) {
        this.notificationService.read(id);
        return ResponseEntity.ok().build();
    }
}
