// 本地持久化
export const TOKEN_KEY = 'USER'

export const setItem = (key, value) => {
  localStorage.setItem(key, JSON.stringify(value))
}

// 获取本地存储
export const getItem = (key) => {
  return JSON.parse(localStorage.getItem(key))
}

// 删除本地存储
export const removeItem = (key) => {
  localStorage.removeItem(key)
}
