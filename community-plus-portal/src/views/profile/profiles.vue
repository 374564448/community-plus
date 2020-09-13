<template>
  <div class="profiles-box">
    <el-row :gutter="30">
      <el-col :span="6">
        <!-- 类别 -->
        <div class="profiles-category-list-box">
          <div :class="['profiles-category-list',{'active': notificationCategoryActive===index}]"
               :key="index" v-for="(category,index) in notificationCategory"
               @click="showCategoryPage(index)">
            <i class="iconfont" style="font-size: 16px;" v-html="category.iconfont"></i>&nbsp;&nbsp;{{category.name}}
          </div>
        </div>
      </el-col>
      <el-col :span="18">
        <!--通知列表-->
        <div class="profiles-list-box">
          <router-view/>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "profiles",
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
          {iconfont: '&#xe611;',name: '个人资料'},
          {iconfont: '&#xe648;',name: '我的文章'},
          {iconfont: '&#xe60f;',name: '我的收藏'}
        ],
        //被选中的通知类别
        notificationCategoryActive: 0
      }
    },
    mounted() {
      /**
       * 选中左边分类
       */
      if (this.$route.params.categoryIndex) {
        this.notificationCategoryActive = this.$route.params.categoryIndex;

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
        if (index === 1) {
          location.href = "/profiles/articles"
        }
      }
    }
  }
</script>

<style scoped>
  @import '../../assets/css/profiles.css';
</style>
