<template>
  <div class="notification-box">
    <!-- 标题 -->
    <div class="notification-title">
      <i class="iconfont" style="font-size: 20px;">&#xe7e2;&nbsp;</i>我的通知
    </div>
    <!-- 通知列表-->
    <div class="notification-list-box" :key="index" v-for="(notification,index) in notificationDTO">
      <div class="notification-content">
        <!--通知人-->
        <span><router-link style="color: #3399ea;text-decoration: underline" to="#">
          {{notification.notifierName}}</router-link>
        </span>
        <!--type-->
        <span>{{notification.typeDescription}}</span>
        <!--外部标题-->
        <span><router-link class="outerTitle-style" to="#">{{notification.outerTitle}}</router-link></span>
      </div>
      <!--内容-->
      <div v-show="notification.content" class="content-style">
        {{notification.content}}
      </div>
      <!--通知时间-->
      <div class="notification-time">
        <i class="iconfont" style="font-size: 11px;">&#xe619;&nbsp;</i>
        {{getTheDateDiff(notification.createTime)}}
      </div>
      <!--未读标志-->
      <div v-show="!notification.status" class="unRead-flag">
        <span>未读</span>
      </div>
    </div>

  </div>
</template>

<script>
  import request from "@/utils/request";
  import {getDateDiff} from "@/utils/common";


  export default {
    name: "notification",
    data() {
      return {
        notificationDTO: [
          {
            id: '1',
            notifierId: '13',
            receiverId: '2',
            outerId: '4',
            type: '14',
            typeDescription: '回复了你的评论',
            status: false,
            notifierName: '半命i',
            outerTitle: '我以管理员身份运行VM启动虚拟器出现黑屏，然后重启电脑，再打开VM重新启动虚拟机，报错，说服务已终止运行。 但有时候重启几次电脑就可以正常运行虚拟机，，，，气死了',
            content: '感谢楼主，解决了！！',
            createTime: 1591980542092
          },
          {
            id: '2',
            notifierId: '16',
            receiverId: '2',
            contentId: '5',
            type: '5',
            typeDescription: '关注了你',
            status: true,
            notifierName: 'banmingi',
            outerTitle: '',
            content: '',
            createTime: 1591980542092
          }
        ]
      }
    },
    methods: {
      /**
       * 把时间戳转换成 *小时前  *天前....
       */
      getTheDateDiff(time) {
        return getDateDiff(time);
      },
    }
  }
</script>

<style scoped>
  a {
    text-decoration: none;
  }
  .notification-box {
    padding: 20px;
  }

  .notification-title {
    font-size: 20px;
    height: 40px;
    color: #606266;
    border-bottom: 1px solid #DCDFE6;
  }
  .notification-list-box {
    position: relative;
    margin: 15px 0;
    padding: 10px 20px;
    border-radius: 3px;
    background-color: #f8f8f9;
    box-shadow: 0 0 0 1px #d3e0e9;
  }
  .notification-content {
    padding: 5px 0;
    font-size: 14px;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
  .notification-content .outerTitle-style {
    color: #303133;
    font-weight: 600;
    text-decoration: none;
  }
  .outerTitle-style:hover {
    color: #b60b14;
  }
  .content-style {
    padding: 10px 0;
    color: #303133;
    font-size: 14px;
  }
  .notification-time {
    margin-top: 5px;
    font-size: 11px;
    user-select: none;
  }
  .unRead-flag {
    position: absolute;
    top: -2px;
    right: -2px;
    border: 1px solid #C0C4CC;
    user-select: none;
    border-radius: 0 3px 0 0;
    margin-left: 20px;
    padding: 1px 5px;
    color: #fff6e5;
    background-color: #d72730;
    font-size: 12px;
  }
</style>
