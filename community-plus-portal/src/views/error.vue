<template>
    <div class="error-box">
      <img class="error-img" src="@/assets/images/404.png" />
      <div class="error-msg">{{errorMsg}}</div>
      <div class="error-button-box">
        <div class="refresh" @click="refresh()"><i class="iconfont" style="font-size: 17px;">&#xe614;</i>刷新页面</div>
        <div class="turnIndex" @click="turnIndex()"><i class="iconfont" style="font-size: 17px;">&#xe62b;</i>返回首页</div>
      </div>
    </div>
</template>

<script>
    export default {
      name: "error",
      data() {
        return {
          errorMsg: '哎呀...您访问的页面不存在'
        }
      },
      methods: {
        refresh() {
          window.location.reload();
        },
        turnIndex() {
          this.$router.push('/');
        }
      }
    }
</script>

<style scoped>
  .error-box {
    margin: 150px auto;
    width: 316px;
    user-select: none;
  }
  .error-img {
    width: 316px;
    height: 128px;
  }
  .error-msg {
    margin: 22px;
    text-align: center;
    color: #888888;
    font-size: 17px;
    font-weight: 600;
  }
  .error-button-box {
    margin: 20px 60px;
    height: 28px;
  }
  .error-button-box .refresh {
    float: left;
    color: rgba(0,0,0,0.6);
    cursor: pointer;
    font-size: 17px;
  }
  .error-button-box .refresh:hover {
    text-decoration: underline;
  }
  .error-button-box .turnIndex{
    float: right;
    color: rgba(0,0,0,0.6);
    cursor: pointer;
    font-size: 17px;
  }
  .error-button-box .turnIndex:hover {
    text-decoration: underline;
  }
</style>
