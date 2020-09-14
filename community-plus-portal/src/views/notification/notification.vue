<template>
  <div class="notification-box">
    <!-- 标题 -->
    <div class="notification-title">
      <i class="iconfont" style="font-size: 20px;">&#xe7e2;&nbsp;</i>我的通知
    </div>
    <!-- 通知列表-->
    <!-- 没有数据时显示-->
    <div v-if="!notificationPageInfo || !notificationPageInfo.total || notificationPageInfo.total===0" style="text-align: center;padding:40px 0;user-select: none">
      <i class="iconfont" style="font-size: 120px;color: rgba(0,181,173,0.7);">&#xe612;</i>
    </div>
    <div v-if="notificationPageInfo && notificationPageInfo.total" class="notification-list-box" :key="index" v-for="(notification,index) in notificationPageInfo.list">
      <div class="notification-content">
        <!--通知人-->
        <span><router-link style="color: #3399ea;text-decoration: underline" to="#">
          {{notification.notifierName}}</router-link>
        </span>
        <!--typeDesc-->
        <span>{{notification.typeDesc}}</span>
        <!--外部标题-->
        <span v-if="notification.outerTitle">
          <span class="outerTitle-style" @click="turnToOutTitleTargetPage(notification)">{{notification.outerTitle}}</span>
        </span>
      </div>
      <!--内容-->
      <div v-if="notification.content" class="content-style">
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

    <!-- 分页 -->
    <div v-show="notificationPageInfo && notificationPageInfo.total" class="pagination-box">
      <el-pagination
        background
        layout="prev, pager, next,jumper"
        :current-page.sync="notificationPageInfo.pageNum"
        :page-size="notificationPageInfo.pageSize"
        :total="notificationPageInfo.total"
        @current-change="pageNumNotificationsList(notificationPageInfo.pageNum)"
        @prev-click="pageNumNotificationsList(notificationPageInfo.pageNum - 1)"
        @next-click="pageNumNotificationsList(notificationPageInfo.pageNum + 1)"
      >
      </el-pagination>
    </div>

  </div>
</template>

<script>
  import request from "@/utils/request";
  import {NOTIFICATIONS_LIST_URL,COMMENT_GET_ARTICLE_ID_URL,NOTIFICATIONS_READ_URL} from "@/utils/api";
  import {getDateDiff} from "@/utils/common";


  export default {
    name: "notification",
    mounted() {
      this.pageNumNotificationsList(1);
    },
    data() {
      return {
        userId: this.$store.getters.getUser.id,

        notificationPageInfo: []
      }
    },
    methods: {
      /**
       * 把时间戳转换成 *小时前  *天前....
       */
      getTheDateDiff(time) {
        return getDateDiff(time);
      },

      /**
       * 分页请求通知列表
       * @param pageNum
       */
      pageNumNotificationsList(pageNum) {
        if (this.userId) {
          request.get(NOTIFICATIONS_LIST_URL + this.userId,
            {params: {
              pageNum: pageNum
              }
            }).then(({data}) => {
              this.notificationPageInfo = data;
          }).catch(err => {
            console.log(err);
            this.$notify.error({
              message: '获取通知列表失败'
            });
          })
        }
      },

      /**
       * 标记某条通知已读
       */
      readNotification(notificationId) {
        request.put(NOTIFICATIONS_READ_URL + notificationId)
      },

      /**
       *跳转到外部标题原处
       * <div><a href="/articles/13#comment2">111</a></div>
       */
      turnToOutTitleTargetPage(dto) {
        let type = dto.type;
        let outerId = dto.outerId;

        //标记该通知已读
        this.readNotification(dto.id);

        //如果是针对文章的评论,直接跳转到该文章
        if (type === "COMMENT_ARTICLE") {
          location.href = "/articles/" + outerId;
        }
        //如果是针对评论的回复,先跳转到文章出处,在定位到评论处
        if (type === "REPLY_COMMENT") {
          //查询该评论所对应的文章
          request.get(COMMENT_GET_ARTICLE_ID_URL + outerId)
            .then(({data}) => {
              location.href = "/articles/"+ data + "#comment" + outerId;
            }).catch(err => {
              console.error(err)
          })
        }
      }
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
    cursor: pointer;
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
  .pagination-box {
    margin-top: 20px;
    border-radius: 5px;
    background-color: #fff;
    padding: 5px 0;
  }
</style>
