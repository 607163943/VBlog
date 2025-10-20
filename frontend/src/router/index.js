import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import ArticleList from '@/components/ArticleList'
import CateMana from '@/components/CateMana'
import DataCharts from '@/components/DataCharts'
import PostArticle from '@/components/PostArticle'
import UserMana from '@/components/UserMana'
import BlogDetail from '@/components/BlogDetail'

Vue.use(Router)

export const routes = [
  {
    name: '文章管理',
    iconCls: 'fa fa-file-text-o',
    children: [
      {
        path: '/articleList',
        name: '文章列表',
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/postArticle',
        name: '发表文章',
        meta: {
          keepAlive: false
        }
      },
      {
        path: '/blogDetail',
        name: '博客详情',
        hidden: true,
        meta: {
          keepAlive: false
        }
      },
      {
        path: '/editBlog',
        name: '编辑博客',
        hidden: true,
        meta: {
          keepAlive: false
        }
      }
    ]
  },
  {
    path: '/user',
    iconCls: 'fa fa-user-o',
    name: '用户管理',
    children: []
  },
  {
    path: '/cateMana',
    iconCls: 'fa fa-reorder',
    name: '栏目管理',
    children: []
  },
  {
    path: '/charts',
    iconCls: 'fa fa-bar-chart',
    name: '数据统计',
    children: []
  }
]

export default new Router({
  routes: [
    {
      path: '/',
      name: '登录',
      component: Login
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
          component: ArticleList
        },
        {
          path: '/postArticle',
          name: '发表文章',
          component: PostArticle
        },
        {
          path: '/blogDetail',
          name: '博客详情',
          component: BlogDetail
        },
        {
          path: '/editBlog',
          name: '编辑博客',
          component: PostArticle
        },
        {
          path: '/user',
          name: '用户管理',
          component: UserMana
        },
        {
          path: '/cateMana',
          name: '栏目管理',
          component: CateMana
        },
        {
          path: '/charts',
          name: '数据统计',
          component: DataCharts
        }
      ]
    }
  ]
})
