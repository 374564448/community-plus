<template>

  <div style="width: 100%;">
    <div class="nav-top"></div>
    <div class="nav-bottom">

      <!-- logo -->
      <div class="logo" @click="turnToIndex()">
        <div class="logo-img">
          <img src="../../assets/images/logo.png" style="width: 38px; height: 38px">
        </div>
        <div class="logo-font">
          CodingUp
        </div>
      </div>


      <!-- 搜索框 -->
      <div class="search">
        <el-input size="small" v-model="search" @keyup.enter.native="searchArticle()" placeholder="搜索..."/>
      </div>


      <!-- 登录成功后-->
      <div class="isLogin" v-if="!this.loginAndRegisterButtonShow">
        <!--写作按钮-->
        <div class="publish">
          <router-link to="/articles/publish"><i class="iconfont" style="font-size: 30px;">&#xe708;</i></router-link>
        </div>
        <!--通知按钮-->
        <div class="notice">
          <router-link to="/notifications">
            <i class="iconfont" style="font-size: 28px;">&#xe7e2;</i>
            <div class="count" v-if="this.unReadNotificationCount && this.unReadNotificationCount !== 0">
              {{unReadNotificationCount<100?unReadNotificationCount:"99+"}}
            </div>
          </router-link>

        </div>
        <el-dropdown placement="bottom" style="height: 44px">
            <!-- 头像 -->
            <div class="userInfo">
              <div class="avatar">
                <img :src="this.avatarUrl"
                     style="width: 30px; height: 30px;border-radius: 50%;" :onerror="this.defaultAvatarUrl"  >
              </div>
              <div class="name">{{this.name}}&nbsp;&nbsp;<i class="el-icon-caret-bottom"/></div>
            </div>
          <el-dropdown-menu slot="dropdown" style="margin: 3px;font-size: 20px">
            <el-dropdown-item>
              <div @click="turnProfile()">个人中心</div>
            </el-dropdown-item>
            <el-dropdown-item>
              <div @click="turnProfileArticles()">我的文章</div>
            </el-dropdown-item>
            <el-dropdown-item>
              <div>我的收藏</div>
            </el-dropdown-item>
            <el-dropdown-item>
              <div  @click="logout()">退出登录</div>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </div>
      <div v-if="this.loginAndRegisterButtonShow" class="loginOrRegisterButton">
        <router-link to="/auth/register"><i class="iconfont" style="font-size: 20px;">&#xe65e;</i>注册</router-link>
      </div>
      <div v-if="this.loginAndRegisterButtonShow" class="loginOrRegisterButton" @click="loginDialogVisible=true">
          <i class="iconfont" style="font-size: 20px;">&#xe6af;</i>登录
      </div>

    </div>

    <!-- 登录框 -->
    <el-dialog
      :visible.sync="loginDialogVisible"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
      :center="true"
      width="25%"
      :before-close="loginDialogBeforeClose"
      >
      <!-- login_title -->
      <div slot="title">
        <img src="../../assets/images/logo.png" style="width: 28px; height: 28px">
      </div>
      <!-- 账号密码登录-->
      <div>
        <el-form ref="loginForm" :model="generalLoginDTO" :rules="rules"  label-width="80px" class="login-box">
          <el-form-item label="账号" prop="accountId" label-width="51px">
            <el-input  type="text"  placeholder="请输入账号"
                       oninput="value=value.replace(/^\s+|\s+$/g,'')"
                       v-model="generalLoginDTO.accountId"
                       maxlength="30"
            />
          </el-form-item>
          <el-form-item label="密码" prop="password" label-width="51px">
            <el-input type="password" placeholder="请输入密码"
                      oninput="this.value=this.value.replace(/^\s+|\s+$/g,'')"
                      v-model="generalLoginDTO.password"
                      maxlength="16"
            />
          </el-form-item>
        </el-form>
        <div class="general_login">
          <el-button type="primary" @click="generalLogin">登录</el-button>
          <div class="forgetPwdOrRegister">
            <router-link to="#">忘记密码</router-link>
            <span style="color: rgba(0,0,0,0.4)">or</span>
            <router-link to="/auth/register">注册</router-link>
          </div>
        </div>
      </div>
      <!-- 协议 -->
      <div class="login-Agreement">登录即表示同意<a href="#">用户协议</a>、<a href="#">隐私政策</a></div>

      <!-- 第三方登录 -->
      <el-divider content-position="left">
        <span style="font-size: 10px;font-style: italic;color: #888888">第三方登录</span>
      </el-divider>
      <div class="other_login-select">
          <div class="GitHub" v-on:click="otherLogin('GitHub')">
            <i class="iconfont" style="font-size: 40px;cursor: pointer;">&#xe603;</i>
          </div>
          <div class="WeChat"  v-on:click="otherLogin('WeChat')">
            <i class="iconfont" style="font-size: 40px;color: rgb(82, 194, 35);cursor: pointer;">&#xe699;</i>
          </div>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import request from "@/utils/request";
  import { GET_USER_INFO_URL,GENERAL_LOGIN_URL,NOTIFICATIONS_UN_READ_COUNT_URL} from '@/utils/api';
  import {setToken,getToken, removeToken} from "@/utils/auth";

  export default {
        name: "index",
        data() {
          return {
            loginDialogVisible: false,
            defaultAvatarUrl: 'this.src="'+ require('@/assets/images/defaultAvatar.png') +'"',
            //用户登录
            generalLoginDTO: {
              accountId: '',
              password: ''
            },


            //未读通知数
            unReadNotificationCount: '',

            //搜索条件
            search: '',

            // 表单验证，需要在 el-form-item 元素中增加 prop 属性
            rules: {

              accountId: [
                {required: true, message: '账号不可为空'},
              ],
              password: [
                {required: true, message: '密码不可为空'},
                {min: 8, max: 16, message: '密码长度为8 ~ 16个字符'}
              ]
            },
          };
        },
     computed: {
       loginAndRegisterButtonShow() {
         return !this.$store.getters.getUser.id;
       },
       avatarUrl() {
           return this.$store.getters.getUser.avatarUrl;
       },
       name() {
         return this.$store.getters.getUser.name;
       },
     },

    //首次进入页面通过token加载用户信息,token从localStorage获取
    created() {
        this.getUserInfo();
    },
    methods: {

        searchArticle() {
          window.location.href = "/?search=" + this.search;
        },

        /**
         * 调转到首页
         */
        turnToIndex() {
          window.location.href = "/";
        },


        /**
         * 关闭页面之前重置表单验证规则的显示
         * @param done
         */
        loginDialogBeforeClose(done) {
          this.$refs['loginForm'].resetFields();
          done();
        },

        /**
         * 账号密码登录
         */
        generalLogin() {
          // 为表单绑定验证功能
          this.$refs['loginForm'].validate((valid) => {
            if (valid) {
              //表单验证成功则请求后台
              request({
                method: "POST",
                url: GENERAL_LOGIN_URL,
                data: this.generalLoginDTO
              }).then(({data}) =>{
                //token存入localStore
                setToken(data.token);
                //用户信息存入vuex
                this.$store.dispatch('asyncUpdateUser',data.user);
                //消息提示
                this.$message({
                  message: '登录成功',
                  type: 'success'
                });
                this.loginDialogVisible=false;
                //跳转首页
                if (this.$route.path !== "/") {
                  this.$router.push("/")
                }
              }).catch(error => {
               if (error.response.status === 400) {
                 this.$message.error("用户不存在或密码错误!");
               } else {
                 this.$message.error("服务器异常,请稍后再试！");
                 console.log('Error',error.message);
               }
              });
            } else {
              return false;
            }
          });
        },

        /**
         * 第三方登录
         * @param mode
         */
        otherLogin(mode) {
          //GitHub登录
          if (mode === 'GitHub') {
            //跳转到github授权页面
              window.location.href=
                 "/login/githubAuthorize?client_id=89aba6082b823e4ea631&redirect_uri=http://localhost:8000/login_github&scope=user";
          }
          //微信登录
          if (mode === 'WeChat') {
          }
        },

        /**
         * 获取用户信息
         */
        getUserInfo() {
          //判断token是否过期
          if (getToken() &&getToken().expirationTime - new Date().getTime() > 0) {
            request.get(GET_USER_INFO_URL)
              .then(({data}) => {
                //获取用户未读通知数
                this.getNotificationsUnReadCount(data.id)
                //userInfo存入vuex
                this.$store.dispatch('asyncUpdateUser',data);
            }).catch(error => {
              console.log(error);
              this.$message.error('服务器异常,暂时无法登录!');
            });
          }
        },

        /**
         * 退出登录
         */
        logout() {
          this.$confirm('你确定要退出登录吗?', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            //清空用户状态
            sessionStorage.removeItem("userState");
            //清空token
            removeToken();
            //清除vuex
            this.$store.dispatch('asyncUpdateUser','');
            this.$message({
              type: 'success',
              message: '退出成功'
            });
            //跳转到首页
            if (this.$route.path !== '/') {
              this.$router.push("/");
            }
          }).catch(() => {
          });
        },

        /**
         * 获取未读通知数
         */
        getNotificationsUnReadCount(userId) {
          request.get(NOTIFICATIONS_UN_READ_COUNT_URL + userId)
            .then(({data}) => {
              this.unReadNotificationCount = data;
          }).catch(err => {
            console.error(err)
          })
        },

      /**
       * 跳转到个人中心页面
       */
      turnProfile() {
        this.$router.push({ name: 'profile', params: {categoryIndex: 0}})
      },
      /**
       * 跳转到个人文章列表页面
       */
      turnProfileArticles() {
        this.$router.push({ name: 'profileArticles', params: {categoryIndex: 1}})
      }

      }
    }
</script>

<style scoped>
  @import '../../assets/css/navigation.css';
</style>
