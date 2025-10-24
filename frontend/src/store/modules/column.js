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
    // 专栏表格数据
    columnTableData: []
  },
  mutations: {
    setColumnTableData (state, data) {
      state.columnTableData = data
    },
    setSearchParams (state, data) {
      state.searchParams = data
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
        context.commit('setColumnTableData', res.data.data)
      } catch (error) {
        Message.error('专栏查询失败')
      }
    }
  },
  getters: {}
}
