<template>
  <div class="register-box">
    <div style="padding-bottom: 20px;">
      <el-divider>
        注<img src="../../assets/images/logo.png" style="width: 28px; height: 28px">册
      </el-divider>
    </div>
    <el-form ref="registerForm" :model="registerDTO" status-icon :rules="registerRules"  label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号" label-width="51px" prop="accountId">
        <el-input  type="text"  placeholder="请输入11位号码"
                   oninput="this.value=this.value.replace(/[^\d]/g,'')"
                   v-model="registerDTO.accountId"
                   maxlength="11"
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
              />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<el-button type="primary" round>发送</el-button>
    </div>
    <el-divider content-position="left"/>
  </div>
</template>

<script>
  import "@/assets/css/register.css";
    export default {
        name: "register",
        data() {
          //表单验证规则: 号码验证
          let accountIdRules = (rule, value, callback) => {
            if (!(/^1[3456789]\d{9}$/.test(this.registerDTO.accountId))) {
              callback(new Error('请输入正确的号码'));
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
        }
    }
</script>

<style scoped>

</style>
