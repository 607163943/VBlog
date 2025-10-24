import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login/Login'
import Layout from '@/views/layout'
import ArticleList from '@/views/article/ArticleList'
import Column from '@/views/column'
import DataCharts from '@/views/chart/DataCharts'
import PostArticle from '@/views/article/PostArticle'
import UserMana from '@/views/user/UserMana'
import BlogDetail from '@/views/article/BlogDetail'
import Home from '@/views/home'
import store from '@/store'

Vue.use(Router)
const router = new Router({
  routes: [
    {
      path: '/login',
      component: Login,
      meta: { keepAlive: false, nav: { show: false } }
    },
    {
      path: '/',
      component: Layout,
      redirect: '/home',
      children: [
        {
          path: '/home',
          component: Home,
          meta: { keepAlive: false, name: '首页', nav: { show: true, iconCls: 'fa fa-home' } }
        },
        {
          path: '/articleList',
          component: ArticleList,
          meta: { keepAlive: true, name: '文章列表', nav: { group: '文章管理', show: true, iconCls: 'fa fa-file-text-o' } }
        },
        {
          path: '/postArticle',
          component: PostArticle,
          meta: { keepAlive: false, name: '发表文章', nav: { group: '文章管理', show: true, groupIcon: 'fa fa-file-text-o' } }
        },
        {
          path: '/blogDetail',
          component: BlogDetail,
          meta: { keepAlive: false, name: '博客详情', nav: { group: '文章管理', show: false, groupIcon: 'fa fa-file-text-o' } }
        },
        {
          path: '/editBlog',
          component: PostArticle,
          meta: { keepAlive: false, name: '编辑博客', nav: { group: '文章管理', show: false, groupIcon: 'fa fa-file-text-o' } }
        },
        {
          path: '/user',
          component: UserMana,
          meta: { keepAlive: false, name: '用户管理', nav: { show: true, icon: 'fa fa-user-o' } }
        },
        {
          path: '/column',
          component: Column,
          meta: { keepAlive: false, name: '栏目管理', nav: { show: true, icon: 'fa fa-reorder' } }
        },
        {
          path: '/charts',
          component: DataCharts,
          meta: { keepAlive: false, name: '数据统计', nav: { show: true, icon: 'fa fa-bar-chart' } }
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  store.commit('layout/createBreadcrumbNavArr', to)
  next()
})

export default router
