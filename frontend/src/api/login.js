import request from '@/utils/request'

export const login = ({ username, password }) => {
  return request.post('/login', {
    username,
    password
  })
}
