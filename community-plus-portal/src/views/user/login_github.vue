<template>

</template>


<script>

  import request from '@/utils/request'
  import { GITHUB_LOGIN_URL } from '@/utils/api'
  import { setToken } from '@/utils/auth'

    export default {
        name: "login_github",
        data() {
          return {
            githubLoginDTO: {
              accountId: '',
              name: '',
              avatarUrl: '',
              bio: ''

            }
          }
        },
      //路由钩子
      //进入此页面前先判断
      beforeRouteEnter: (to,from,next) => {
        //console.log("进入giuhub登录页");
        next(vm => {
          vm.getAccessTokenByCode();
        });
      },

      methods :{
          //通过code获取token
          getAccessTokenByCode() {
          const client_id = this.$route.query.client_id;
          const code = this.$route.query.code;
          if (!client_id || !code) {
            this.$router.push("/");
            return;
          }
          console.log(code);
           request({
            method: 'POST',
            url: '/login/githubAccessToken',
            headers: {
               'Accept': 'application/json'
            },
            data: {
              client_id: client_id,
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
            url: "/login/githubUserInfo",
            headers: {
              'Accept': 'application/json',
              'Authorization': `token ${access_token}`
            }
          }).then(({data}) => {
            this.githubLoginDTO.accountId = data.id;
            this.githubLoginDTO.name = data.name;
            this.githubLoginDTO.avatarUrl = data.avatar_url;
            this.githubLoginDTO.bio = data.bio;

            //console.log(this.userInfo);

            //用户信息存入后台并返回token
            this.gitHubLogin(this.githubLoginDTO);

          }).catch(error => {
            console.log(error);
            this.$message.error('网络异常,暂时无法登录');
            this.$router.push("/");
          });
        },

        //登录
        gitHubLogin(data) {
            request({
              method: "POST",
              url: GITHUB_LOGIN_URL,
              data: data
            }).then(({data}) => {
              console.log(data);
              //token存入localStorage
              setToken(data.token);
              //user存入vuex
              this.$store.dispatch('asyncUpdateUser',data.user);
              //消息提示
              this.$message({
                message: '登录成功',
                type: 'success'
              });
              //跳转首页
              this.$router.push("/")
            }).then(err => {
              console.log(err);
              this.$message.error('网络异常,暂时无法登录');
              this.$router.push("/");
            })

        }
      }
    }
</script>

<style scoped>

</style>
