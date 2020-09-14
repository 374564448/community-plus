<template>
  <div class="profile-box">
    <!-- 标题 -->
    <div class="profile-title">
      <i class="iconfont" style="font-size: 20px;">&#xe611;&nbsp;</i>个人资料
    </div>


  </div>
</template>

<script>
  import request from "@/utils/request";
  import {getDateDiff} from "@/utils/common";


  export default {
    name: "profile",
    mounted() {

    },
    data() {
      return {
        userId: this.$store.getters.getUser.id,

        profilePageInfo: []
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
  .profile-box {
    padding: 20px;
  }

  .profile-title {
    font-size: 20px;
    height: 40px;
    color: #606266;
    border-bottom: 1px solid #DCDFE6;
  }
</style>
