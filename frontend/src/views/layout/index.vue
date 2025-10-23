<template>
  <el-container class="home_container">
    <!-- 头部栏 -->
    <el-header>
      <Header></Header>
    </el-header>
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <SideNav></SideNav>
      </el-aside>
      <!-- 内容栏 -->
      <el-main>
        <!-- 面包屑 -->
        <Breadcrumb></Breadcrumb>
        <!-- 二级路由出口 -->
        <keep-alive>
          <router-view v-if="this.$route.meta.keepAlive"></router-view>
        </keep-alive>
        <router-view v-if="!this.$route.meta.keepAlive"></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import { userGetCurrentUserName } from '@/api/user'
import Header from '@/views/layout/Header'
import SideNav from '@/views/layout/SideNav'
import Breadcrumb from '@/views/layout/Breadcrumb'
import { mapMutations } from 'vuex'
export default {
  name: 'HomeCom',
  components: {
    Header,
    SideNav,
    Breadcrumb
  },
  async mounted () {
    const userInfo = {}
    try {
      const res = await userGetCurrentUserName()
      userInfo.currentUserName = res.data.data
    } catch (error) {
      userInfo.currentUserName = '游客'
    }

    this.setUserInfo(userInfo)
  },
  methods: {
    ...mapMutations('user', ['setUserInfo'])
  }
}
</script>
<style lang="less" scoped>
.home_container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;

  ::v-deep(.el-header) {
    background-color: #20a0ff;
    color: #333;
  }

  ::v-deep(.el-aside) {
    background-color: #ececec;
  }
}

::v-deep(.el-container) {
  overflow: hidden;
}
</style>
