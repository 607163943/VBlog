<template>
  <div class="layout-header">
      <div class="home_title">V部落博客管理平台</div>
      <div class="home_userinfoContainer">
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link home_userinfo">
            {{ userInfo.currentUserName}}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="sysMsg">系统消息</el-dropdown-item>
            <el-dropdown-item command="MyArticle">我的文章</el-dropdown-item>
            <el-dropdown-item command="MyHome">个人主页</el-dropdown-item>
            <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
export default {
  name: 'HomeCom',
  data () {
    return {
      currentUserName: ''
    }
  },
  computed: {
    ...mapState('user', ['userInfo'])
  },
  async created () {
    this.currentUserName = this.userInfo.currentUserName
  },
  methods: {
    async handleCommand (command) {
      if (command === 'logout') {
        await this.$confirm('注销登录吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })

        this.setToken('')
        this.currentUserName = '游客'
        this.$router.replace({ path: '/login' })
      }
    },
    ...mapMutations('user', ['setToken'])
  }
}
</script>

<style lang="less">
.layout-header {
  width: 100%;
  height: 100%;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.home_title {
  color: #fff;
  font-size: 22px;
  display: inline;
}

.home_userinfo {
  color: #fff;
  cursor: pointer;
}

.home_userinfoContainer {
  margin-right: 20px;
}
</style>
