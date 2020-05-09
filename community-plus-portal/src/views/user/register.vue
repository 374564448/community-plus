<template>
  <div class="register-box">
    <div style="padding-bottom: 20px;">
      <el-divider>
        注<img src="../../assets/images/logo.png" style="width: 28px; height: 28px">册
      </el-divider>
    </div>
    <el-form ref="registerForm" :model="registerDTO" status-icon :rules="registerRules"  label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号" label-width="51px" prop="accountId">
        <el-input  type="text"  placeholder="请输入您的邮箱"
                   oninput="this.value=this.value.replace(/^\s+|\s+$/g,'')"
                   v-model="registerDTO.accountId"
                   maxlength="30"
        />
      </el-form-item>
      <el-form-item label="密码" label-width="51px" prop="password">
        <el-input type="password"
                  placeholder="请输入密码"
                  oninput="this.value=this.value.replace(/^\s+|\s+$/g,'')"
                  v-model="registerDTO.password" maxlength="16" autocomplete="off"/>
      </el-form-item>
    </el-form>
    <div class="checkCode">
      验证码&nbsp;&nbsp;<el-input  type="text"  placeholder="6位验证码"
                         oninput="this.value=this.value.replace(/[^\d]/g,'')"
                         v-model="registerDTO.checkCode"
                         maxlength="6"
                         size="medium"
                    style="width:120px"
              />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <el-button type="primary" round :loading="isSendCheckCode" @click="sendCheckCode">{{sendCodeMsg}}</el-button>
    </div>
    <el-divider content-position="left"/>
    <div class="register-button">
      <el-button type="primary" round>注 册</el-button>
    </div>
  </div>
</template>

<script>
  import "@/assets/css/register.css";
  import request from "@/utils/request";
  import {SMS_CHECK_CODE_URL} from "@/utils/api";
    export default {
        name: "register",
        data() {
          //表单验证规则: 邮箱验证
          let accountIdRules = (rule, value, callback) => {
            if (!(/^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/.test(this.registerDTO.accountId))) {
              callback(new Error('请输入正确格式的邮箱'));
            } else {
              callback();
            }
          };
          return {
            //注册的数据
            registerDTO: {
              accountId: '',
              password: '',
              checkCode: ''
            },
          //确认密码和验证码
            checkPassword: '',

            //发送验证码
            sendCodeMsg: '发送',
            isSendCheckCode: false,
            sendCodeTimer: null,

            //表单校验规则
            registerRules: {
              accountId: [
                { validator: accountIdRules },
                {required: true, message: '账号不可为空'},
              ],
              password: [
                {required: true, message: '密码不可为空'},
                {min: 8, max: 16, message: '密码长度为8 ~ 16个字符'}
              ],
            },
          }
        },
      methods: {
        //发送验证码倒计时
       isSendCode() {
         const TIME_COUNT = 60;
         if (!this.sendCodeTimer) {
           this.sendCodeMsg = TIME_COUNT;
           this.isSendCheckCode = true;
           this.sendCodeTimer = setInterval(() => {
             if (this.sendCodeMsg > 0 && this.sendCodeMsg <= TIME_COUNT) {
               this.sendCodeMsg--;
             } else {
               this.isSendCheckCode = false;
               clearInterval(this.sendCodeTimer);
               this.sendCodeTimer = null;
               this.sendCodeMsg = '发送'
             }
           }, 1000)
         }
       },
        //发送验证码
        sendCheckCode() {
          if (!(/^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/.test(this.registerDTO.accountId))) {
            this.$message({
              message: '请输入正确格式的邮箱!',
              type: 'warning'
            });
            return;
          }
          this.isSendCode();
          request.get(SMS_CHECK_CODE_URL,{
            params: {
              accountId: this.registerDTO.accountId
            }
          });
          this.$message({
            message: '验证码发送成功,请注意查收',
            type: 'success'
          });
        }
       }
    }
</script>

<style scoped>

</style>
