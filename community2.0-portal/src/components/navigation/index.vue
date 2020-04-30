<template>

  <div style="width: 100%">
    <div class="nav-top"></div>
    <div class="nav-bottom">

      <!-- logo -->
      <div class="logo">
        <div class="logo-img">
          <img src="../../assets/images/logo.png" style="width: 35px; height: 35px">
        </div>
        <div class="logo-font">
          CodingUp
        </div>
      </div>


      <!-- 搜索框 -->
      <div class="search">
        <el-input size="small" placeholder="搜索..."/>
      </div>

      <!--写作-->
<!--      <div class="publish">-->
<!--        <i class="iconfont" style="font-size: 30px;">&#xe708;</i>-->
<!--      </div>-->


      <div class="login" @click="dialogVisible=true">
          <i class="iconfont" style="font-size: 20px;">&#xe6af;</i>登录
      </div>
    </div>

    <el-dialog
      :visible.sync="dialogVisible"
      :close-on-press-escape="false"
      :center="true"
      width="25%"
      >
      <h3 class="login-title">欢迎登录</h3>
      <el-divider content-position="left">
        <span style="font-size: 10px;font-style: italic;color: #888888">第三方登录</span>
      </el-divider>
      <div class="login-select">
          <div class="GitHub" v-on:click="login('GitHub')">
            <i class="iconfont" style="font-size: 40px;cursor: pointer;">&#xe603;</i>
          </div>
          <div class="WeChat"  v-on:click="login('WeChat')">
            <i class="iconfont" style="font-size: 40px;color: rgb(82, 194, 35);cursor: pointer;">&#xe699;</i>
          </div>
      </div>
      <div class="login-Agreement">登录即表示同意<a href="#">用户协议</a>、<a href="#">隐私政策</a></div>
    </el-dialog>

  </div>
</template>

<script>

  import "@/assets/css/navigation.css"
  import "@/assets/fonts/iconfont/iconfont.css"

    export default {
        name: "index",
        data() {
          return {
            dialogVisible: false,
          };
        },

      //TODO: 首次进入页面通过token加载用户信息,token从localStorage获取
      created() {
          this.getUserInfo();
      },
      methods: {
        login(mode) {
          //GitHub登录
          if (mode === 'GitHub') {
            //跳转到github授权页面
              window.location.href=
                "/login/githubAuthorize?client_id=89aba6082b823e4ea631&redirect_uri=http://localhost:8080/login_github&scope=user";
          }
          //微信登录
          if (mode === 'WeChat') {
          }
        },
        //获取用户信息
        getUserInfo() {

        }

      }
    }
</script>

<style scoped>

</style>
