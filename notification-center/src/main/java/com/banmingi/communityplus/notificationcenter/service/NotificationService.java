package com.banmingi.communityplus.notificationcenter.service;

import com.banmingi.communityplus.commons.enums.NotificationTypeEnum;
import com.banmingi.communityplus.notificationcenter.dto.notification.NotificationCreateDTO;
import com.banmingi.communityplus.notificationcenter.dto.notification.NotificationDTO;
import com.banmingi.communityplus.notificationcenter.entity.Notification;
import com.banmingi.communityplus.notificationcenter.mapper.NotificationMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 半命i 2020/8/2
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationService {

    private final NotificationMapper notificationMapper;

    /**
     * 创建通知
     * @param notificationCreateDTO 从RocketMQ获取的通知创建实体类
     */
    public void createNotification(NotificationCreateDTO notificationCreateDTO) {
        Notification notification = new Notification();
        BeanUtils.copyProperties(notificationCreateDTO,notification);
        notification.setStatus(false);

        this.notificationMapper.insert(notification);

    }

    /**
     * 获取通知列表
     * @param userId 用户id
     * @param pageNum 第几页
     * @param pageSize 页面大小
     * @return 通知列表
     */
    public PageInfo<NotificationDTO> list(Integer userId,Integer pageNum,Integer pageSize) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver_id",userId);
        queryWrapper.orderByDesc("create_time");

        //分页
        //切入下面不分页的SQL,自动拼接分页的SQL
        PageHelper.startPage(pageNum,pageSize);

        //根据条件查询结果
        List<Notification> notificationList = this.notificationMapper.selectList(queryWrapper);
        /*
          因为PageHelper针对的分页是切入数据库的物理分页,这里只能对Notification进行分页,但想要的返回结果是NotificationDTO,
          所以现在这里获取到PageInfo<Notification>的分页属性,然后再在下面从新赋值给PageInfo<NotificationDTO>
         */
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notificationList);
        List<NotificationDTO> notificationDTOList = notificationList.stream().map(notification -> {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeDesc(EnumUtils.getEnum(NotificationTypeEnum.class, notification.getType()).getDesc());
            return notificationDTO;
        }).collect(Collectors.toList());

        //PageInfo<Notification> --> PageInfo<NotificationDTO>
        PageInfo<NotificationDTO> notificationDTOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(notificationPageInfo,notificationDTOPageInfo);
        notificationDTOPageInfo.setList(notificationDTOList);

        return notificationDTOPageInfo;
    }


    /**
     * 获取未读通知数
     * @param userId 用户id
     * @return 未读通知数
     */
    public Integer unReadCount(Integer userId) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver_id",userId);
        queryWrapper.eq("status",false);
        return this.notificationMapper.selectCount(queryWrapper);
    }


}
