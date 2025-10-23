<template>
  <div class="login">
    <el-form
      :rules="rules"
      class="login-container"
      label-position="top"
      v-loading="loading"
    >
      <h3 class="login_title">系统登录</h3>
      <el-form-item prop="account" label="用户名">
        <el-input type="text" v-model="loginForm.username"
          auto-complete="off" placeholder="账号"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input type="password" v-model="loginForm.password"
          auto-complete="off" placeholder="密码"></el-input>
      </el-form-item>
      <el-checkbox class="login_remember" v-model="checked">记住密码</el-checkbox>
      <el-form-item style="width: 100%">
        <el-button type="primary" @click="login"
        style="width: 100%">登录</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { userLoginService } from '@/api/login'
import { mapMutations } from 'vuex'

export default {
  name: 'LoginCom',
  data () {
    return {
      rules: {
        account: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      checked: true,
      loginForm: {
        username: 'sang',
        password: '123'
      },
      loading: false
    }
  },
  methods: {
    async login () {
      this.loading = true
      try {
        const res = await userLoginService(this.loginForm)
        if (res.data.code === 200) {
          this.setToken(res.data.data)
          this.$router.replace({ path: '/' })
        } else {
          // 失败
          this.$message.error('登录失败!')
        }
      } finally {
        this.loading = false
      }
    },
    ...mapMutations('user', ['setToken'])
  }
}
</script>
<style lang="less" scoped>
.login {
  padding-top: 120px;

  .login-container {
    border-radius: 15px;
    margin: 0 auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;

    ::v-deep(.el-form-item__label) {
      padding: 0;
    }
  }

  .login_title {
    margin-bottom: 20px;
    text-align: center;
    color: #505458;
  }

  .login_remember {
    margin-bottom: 35px;
    text-align: left;
  }
}
</style>
