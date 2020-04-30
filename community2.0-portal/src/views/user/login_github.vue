<template>

</template>


<script>

  import request from '@/utils/request/user/login_github'

    export default {
        name: "login_github",
        data() {
          return {
            userInfo: {
              id: '',
              name: '',
              avatar_url: '',
              bio: ''
            }
          }
        },
      //路由钩子
      //TODO: 进入此页面前先判断
      beforeRouteEnter: ((to,from,next) => {
      console.log("进入giuhub登录页");
      next();}),

      methods :{
          //通过code获取token
          getAccessTokenByCode() {
          const code = this.$route.query.code;
          console.log(code);
           request({
            method: 'POST',
            url: '/githubAccessToken',
             headers: {
               'Accept': 'application/json'
            },
            data: {
              client_id: '89aba6082b823e4ea631',
              client_secret: '5cb9565ed6374790d753c49fe7af2fed0d42b3d2',
              code: code
            }
          }).then(resp =>{
            console.log(resp.data.access_token)
             this.getUserInfoByAccessToken(resp.data.access_token)
           })

        },
        //通过token获取用户信息
        getUserInfoByAccessToken(access_token) {
          request({
            method: "GET",
            url: "/githubUserInfo",
            headers: {
              'Accept': 'application/json',
              'Authorization': `token ${access_token}`
            }
          }).then(({data}) => {
            this.userInfo.id = data.id;
            this.userInfo.name = data.name;
            this.userInfo.avatar_url = data.avatar_url;
            this.userInfo.bio = data.bio;
            console.log(this.userInfo);

            //TODO: 用户信息存入vuex,用户信息存入后台并返回token

            /**
             * 这里调用自己的用户接口 存储数据---跳转首页
             */
            window.location.href = "/";
          });
        }
      },
      mounted() {
        this.getAccessTokenByCode();
      }
    }
</script>

<style scoped>

</style>
