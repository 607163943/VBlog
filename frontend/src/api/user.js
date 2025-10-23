import request from '@/utils/request'

// 获取当前用户名
export const userGetCurrentUserName = () => {
  return request.get('/currentUserName')
}

// 搜索用户
export const userSearchService = (searchParams) => {
  return request.get('/admin/user',
    { params: searchParams })
}

// 根据id删除用户
export const userDeleteByIdService = (id) => {
  return request.delete(`/admin/user/${id}`)
}

// 修改用户账号状态
export const userUpdateStatusService = (data) => {
  return request.put('/admin/user/enabled', data)
}
