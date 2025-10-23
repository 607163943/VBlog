<template>
  <el-container class="home_container">
    <el-header>
      <Header></Header>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <SideNav></SideNav>
      </el-aside>
      <el-main>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{
            $router.currentRoute.name
          }}</el-breadcrumb-item>
        </el-breadcrumb>
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
import { mapMutations } from 'vuex'
export default {
  name: 'HomeCom',
  components: {
    Header,
    SideNav
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
<style>
.home_container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}

.el-container {
  overflow: hidden;
}

.el-header {
  background-color: #20a0ff;
  color: #333;
}

.el-aside {
  background-color: #ececec;
}

.el-main {
  background-color: #fff;
  color: #000;
  text-align: center;
}
</style>
