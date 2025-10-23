<template>
  <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item v-if="BreadcrumbNavArr.length>0" to="/home">首页</el-breadcrumb-item>
    <el-breadcrumb-item v-else >首页</el-breadcrumb-item>
    <transition-group name="list">
      <el-breadcrumb-item v-for="navName in BreadcrumbNavArr" :key="navName">{{
        navName
      }}</el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'BreadcrumbCom',
  computed: {
    ...mapState('layout', ['BreadcrumbNavArr'])
  }
}
</script>

<style lang="less" scoped>
// 过渡样式动画设置(.list-move保证元素不会闪现)
.list-move, /* 对移动中的元素应用的过渡 */
.list-enter-active,
.list-leave-active {
  transition: all 0.5s ease;
}

// 离开动画样式
.list-leave-to {
  opacity: 0;
  transform: translateX(6px);
}

// 进入动画样式
.list-enter {
  opacity: 1;
  transform: translateX(60px);
}

/* 确保将离开的元素从布局流中删除
  以便能够正确地计算移动的动画。 */
.list-leave-active {
  position: absolute;
}
</style>
