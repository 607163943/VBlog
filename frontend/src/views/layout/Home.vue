<template>
  <el-container class="home_container">
    <el-header>
      <div class="home_title">V部落博客管理平台</div>
      <div class="home_userinfoContainer">
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link home_userinfo">
            {{currentUserName}}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="sysMsg">系统消息</el-dropdown-item>
            <el-dropdown-item command="MyArticle">我的文章</el-dropdown-item>
            <el-dropdown-item command="MyHome">个人主页</el-dropdown-item>
            <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
          default-active="0"
          class="el-menu-vertical-demo" style="background-color: #ECECEC" router>
          <div v-for="(item,index) in navItems" :key="index">
            <el-submenu :index="index+''" v-if="Array.isArray(item.value)">
              <template slot="title">
                <i :class="item.iconCls"></i>
                <span>{{item.name}}</span>
              </template>
              <div  v-for="(child,index) in item.value" :key="index">
                <el-menu-item  :index="child.path" >
                {{child.name}}
              </el-menu-item>
              </div>
            </el-submenu>
            <div v-else>
              <el-menu-item :index="item.value.path" >
                <i :class="item.iconCls"></i>
                <span slot="title">{{item.value.name}}</span>
              </el-menu-item>
            </div>
          </div>
        </el-menu>
      </el-aside>
      <el-container>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item >{{$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <keep-alive>
            <router-view v-if="this.$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!this.$route.meta.keepAlive"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>
<script>
import { getRequest } from '../../utils/api'
export default {
  name: 'HomeCom',
  data () {
    return {
      currentUserName: '',
      navItems: []
    }
  },
  async created () {
    this.createdNavItems()
  },
  async mounted () {
    try {
      const res = await getRequest('/currentUserName')
      this.currentUserName = res.data
    } catch (error) {
      this.currentUserName = '游客'
    }
  },
  methods: {
    // 创建导航栏结构
    createdNavItems () {
      for (let i = 0; i < this.$router.options.routes.length; i++) {
        const route = this.$router.options.routes[i]
        if (route.children && route.children.length > 0) {
          for (let j = 0; j < route.children.length; j++) {
            const child = route.children[j]
            this.pushItem(child)
          }
        } else {
          this.pushItem(route)
        }
      }
    },
    // 添加导航项
    pushItem (route) {
      if (!route.meta.nav.show) return
      if (route.meta.nav.group) {
        const obj = this.navItems.find(item => item.name === route.meta.nav.group)
        if (obj) {
          obj.value.push(route)
        } else {
          const obj = {}
          obj.value = [route]
          obj.name = route.meta.nav.group
          obj.iconCls = route.meta.nav.groupIcon
          this.navItems.push(obj)
        }
      } else {
        const obj = {}
        obj.value = route
        obj.iconCls = route.meta.nav.icon
        this.navItems.push(obj)
      }
    },
    handleCommand (command) {
      const _this = this
      if (command === 'logout') {
        this.$confirm('注销登录吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          getRequest('/logout')
          _this.currentUserName = '游客'
          _this.$router.replace({ path: '/' })
        }, function () {
          // 取消
        })
      }
    }
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

  .el-header {
    background-color: #20a0ff;
    color: #333;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .el-aside {
    background-color: #ECECEC;
  }

  .el-main {
    background-color: #fff;
    color: #000;
    text-align: center;
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
    display: inline;
    margin-right: 20px;
  }
</style>
