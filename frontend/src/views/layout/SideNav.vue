<template>
  <el-menu
    :default-active="$route.path"
    class="el-menu-vertical-demo"
    style="background-color: #ececec"
    router
  >
    <div v-for="(item, index) in navItems" :key="index">
      <el-submenu :index="index + ''" v-if="Array.isArray(item.value)">
        <template slot="title">
          <i :class="item.iconCls"></i>
          <span>{{ item.name }}</span>
        </template>
        <div v-for="(child, index) in item.value" :key="index">
          <el-menu-item :index="child.path">
            {{ child.meta.name }}
          </el-menu-item>
        </div>
      </el-submenu>
      <div v-else>
        <el-menu-item :index="item.value.path">
          <i :class="item.iconCls"></i>
          <span slot="title">{{ item.value.meta.name }}</span>
        </el-menu-item>
      </div>
    </div>
  </el-menu>
</template>

<script>
export default {
  data () {
    return {
      navItems: []
    }
  },
  created () {
    this.createdNavItems()
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
        const obj = this.navItems.find(
          (item) => item.name === route.meta.nav.group
        )
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
    }
  }
}
</script>

<style>
</style>
