<template>
  <div class="notifications-box">
    <el-row :gutter="30">
      <el-col :span="6">
        <!-- 类别 -->
        <div class="notifications-category-list-box">
          <div :class="['notifications-category-list',{'active': notificationCategoryActive===index}]"
               :key="index" v-for="(category,index) in notificationCategory"
               @click="showCategoryPage(index)">
            <i class="iconfont" style="font-size: 16px;" v-html="category.iconfont"></i>&nbsp;&nbsp;{{category.name}}
          </div>
        </div>
      </el-col>
      <el-col :span="18">
        <!--通知列表-->
        <div class="notifications-list-box">
          <router-view/>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "notifications",
    //进入此页面之前先判断是否登录
    beforeRouteEnter: (to,from,next) => {
      next(vm => {
        vm.isLogin();
      });
    },
    data() {
      return {
        /**
         * 样式相关
         */
        //通知类别
        notificationCategory: [
          {iconfont: '&#xe6aa;',name: '通知'},
          {iconfont: '&#xe60b;',name: '私信'},
          {iconfont: '&#xe60e;',name: '系统'}
        ],
      }
    },
    computed: {
      profileCategoryActive: {
        get: function () {
          return this.$route.params.categoryIndex?this.$route.params.categoryIndex:0;
        },
        set: function (v) {

        }
      }
    },
    methods: {
      /**
       * 判断是否已经登录
       */
      isLogin() {
        const isLogin = this.$store.getters.getUser.id;
        if (!isLogin) {
          this.$message({
            message: '你必须登录之后才能执行此操作！',
            type: 'warning'
          });
          this.$router.push("/");
        }
      },

      /**
       * 展现相关的子页面
       * @param index
       */
      showCategoryPage(index) {
        this.notificationCategoryActive = index;
        if (index === 0) {
          this.$router.push({ name: 'notification', params: {categoryIndex: index}})
        }
      }
    }
  }
</script>

<style scoped>
  @import '../../assets/css/notifications.css';
</style>
