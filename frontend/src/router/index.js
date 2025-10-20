import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login/Login'
import Home from '@/views/layout/Home'
import ArticleList from '@/views/article/ArticleList'
import CateMana from '@/views/chart/CateMana'
import DataCharts from '@/components/DataCharts'
import PostArticle from '@/views/article/PostArticle'
import UserMana from '@/views/user/UserMana'
import BlogDetail from '@/views/article/BlogDetail'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: '登录',
      component: Login,
      meta: { keepAlive: false, nav: { show: false } }
    },
    {
      path: '/home',
      name: '文章管理',
      component: Home,
      redirect: '/charts',
      children: [
        {
          path: '/articleList',
          name: '文章列表',
          component: ArticleList,
          meta: { keepAlive: true, nav: { group: '文章管理', show: true, iconCls: 'fa fa-file-text-o' } }
        },
        {
          path: '/postArticle',
          name: '发表文章',
          component: PostArticle,
          meta: { keepAlive: false, nav: { group: '文章管理', show: true, groupIcon: 'fa fa-file-text-o' } }
        },
        {
          path: '/blogDetail',
          name: '博客详情',
          component: BlogDetail,
          meta: { keepAlive: false, nav: { group: '文章管理', show: false, groupIcon: 'fa fa-file-text-o' } }
        },
        {
          path: '/editBlog',
          name: '编辑博客',
          component: PostArticle,
          meta: { keepAlive: false, nav: { group: '文章管理', show: false, groupIcon: 'fa fa-file-text-o' } }
        },
        {
          path: '/user',
          name: '用户管理',
          component: UserMana,
          meta: { keepAlive: false, nav: { show: true, icon: 'fa fa-user-o' } }
        },
        {
          path: '/cateMana',
          name: '栏目管理',
          component: CateMana,
          meta: { keepAlive: false, nav: { show: true, icon: 'fa fa-reorder' } }
        },
        {
          path: '/charts',
          name: '数据统计',
          component: DataCharts,
          meta: { keepAlive: false, nav: { show: true, icon: 'fa fa-bar-chart' } }
        }
      ]
    }
  ]
})
