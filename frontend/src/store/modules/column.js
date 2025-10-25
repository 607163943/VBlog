import { columnSearchService } from '@/api/column'
import { Message } from 'element-ui'

export default {
  namespaced: true,
  state: {
    // 专栏搜索参数
    searchParams: {
      // 专栏名
      columnName: ''
    },
    searchPage: {
      // 页数
      page: 1,
      // 页码
      pageSize: 10
    },
    // 专栏总条数
    total: 0,
    // 专栏表格加载状态
    tableLoading: false,
    // 专栏表格数据
    columnTableData: []
  },
  mutations: {
    setColumnTableData (state, data) {
      state.columnTableData = data
    },
    setTotal (state, data) {
      state.total = data
    },
    setSearchParams (state, data) {
      state.searchParams = data
    },
    setSearchPageSize (state, data) {
      state.searchPage.pageSize = data
    },
    setSearchPage (state, data) {
      state.searchPage.page = data
    },
    setTableLoading (state, data) {
      state.tableLoading = data
    }
  },
  actions: {
    // 专栏分页查询
    async pageQueryColumn (context) {
      const params = {
        ...context.state.searchParams,
        ...context.state.searchPage
      }
      try {
        const res = await columnSearchService(params)
        context.commit('setColumnTableData', res.data.data.results)
        context.commit('setTotal', res.data.data.total)
      } catch (error) {
        Message.error('专栏查询失败')
      }
    },
    // 搜索专栏
    async searchColumn (context) {
      context.commit('setTableLoading', true)
      try {
        await context.dispatch('pageQueryColumn')
      } finally {
        context.commit('setTableLoading', false)
      }
    }
  },
  getters: {}
}
