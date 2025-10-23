export default {
  namespaced: true,
  state () {
    return {
      // 登录token
      token: '',
      // 用户信息
      userInfo: {}
    }
  },
  mutations: {
    // 设置用户信息
    setUserInfo (state, userInfo) {
      state.userInfo = userInfo
    },
    // 设置token
    setToken (state, token) {
      state.token = token
    }
  },
  actions: {},
  getters: {}
}
