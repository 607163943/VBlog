import request from '@/utils/request'

// 专栏搜索
export const columnSearchService = (params) => {
  return request.get('/admin/column', { params })
}

// 添加专栏
export const columnAddService = (data) => {
  return request.post('/admin/column', data)
}

// 根据id获取专栏
export const columnGetByIdService = (id) => {
  return request.get(`/admin/column/${id}`)
}

// 修改专栏
export const columnUpdateService = (data) => {
  return request.put('/admin/column', data)
}

// 根据id批量删除专栏
export const columnDeleteByIdsService = (ids) => {
  return request.delete('/admin/column', { params: { ids: ids } })
}
